package com.kosa.ShareTour.service;

import com.kosa.ShareTour.dto.*;
import com.kosa.ShareTour.entity.*;
import com.kosa.ShareTour.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionOrderService {

    private final AuctionRepository auctionRepository;

    private final MemberRepository memberRepository;

    private final AuctionOrderRepository auctionOrderRepository;

    private final AuctionImgRepository auctionImgRepository;

    public Long auctionOrder(AuctionOrderDto auctionOrderDto, String email){

        Auction auction = auctionRepository.findById(auctionOrderDto.getAuctionId())
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);

        List<AuctionOrderItem> auctionOrderItemList = new ArrayList<>();
        AuctionOrderItem auctionOrderItem = AuctionOrderItem.createAuctionOrderItem(auction, auctionOrderDto.getCount());
        auctionOrderItemList.add(auctionOrderItem);
        AuctionOrder auctionOrder = AuctionOrder.createAuctionOrder(member, auctionOrderItemList);
        auctionOrderRepository.save(auctionOrder);

        return auctionOrder.getId();
    }

    @Transactional(readOnly = true)
    public Page<AuctionOrderHistDto> getAuctionOrderList(String email, Pageable pageable) {

        List<AuctionOrder> auctionOrders = auctionOrderRepository.findAuctionOrders(email, pageable);
        Long totalCount = auctionOrderRepository.countAuctionOrder(email);

        List<AuctionOrderHistDto> auctionOrderHistDtos = new ArrayList<>();

        for (AuctionOrder auctionOrder : auctionOrders) {
            AuctionOrderHistDto auctionOrderHistDto = new AuctionOrderHistDto(auctionOrder);
            List<AuctionOrderItem> auctionOrderItems = auctionOrder.getAuctionOrderItems();
            for (AuctionOrderItem auctionOrderItem : auctionOrderItems) {
                AuctionImg auctionImg = auctionImgRepository.findByAuctionIdAndRepimgYn
                        (auctionOrderItem.getAuction().getId(), "Y");
                AuctionOrderItemDto auctionOrderItemDto =
                        new AuctionOrderItemDto(auctionOrderItem, auctionImg.getImgUrl());
                auctionOrderHistDto.addAuctionOrderItemDto(auctionOrderItemDto);
            }

            auctionOrderHistDtos.add(auctionOrderHistDto);
        }

        return new PageImpl<AuctionOrderHistDto>(auctionOrderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateAuctionOrder(Long auctionOrderId, String email){
        Member curMember = memberRepository.findByEmail(email);
        AuctionOrder auctionOrder = auctionOrderRepository.findById(auctionOrderId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = auctionOrder.getMember();

        if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    public void cancelAuctionOrder(Long auctionOrderId){
        AuctionOrder auctionOrder = auctionOrderRepository.findById(auctionOrderId)
                .orElseThrow(EntityNotFoundException::new);
        auctionOrder.cancelAuctionOrder();
    }

    public Long auctionOrders(List<AuctionOrderDto> auctionOrderDtoList, String email){

        Member member = memberRepository.findByEmail(email);
        List<AuctionOrderItem> auctionOrderItemList = new ArrayList<>();

        for (AuctionOrderDto auctionOrderDto : auctionOrderDtoList) {
            Auction auction = auctionRepository.findById(auctionOrderDto.getAuctionId())
                    .orElseThrow(EntityNotFoundException::new);

            AuctionOrderItem auctionOrderItem = AuctionOrderItem.createAuctionOrderItem(auction, auctionOrderDto.getCount());
            auctionOrderItemList.add(auctionOrderItem);
        }

        AuctionOrder auctionOrder = AuctionOrder.createAuctionOrder(member, auctionOrderItemList);
        auctionOrderRepository.save(auctionOrder);

        return auctionOrder.getId();
    }
}
