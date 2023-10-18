package com.kosa.ShareTour.service;

import com.kosa.ShareTour.dto.*;
import com.kosa.ShareTour.entity.Auction;
import com.kosa.ShareTour.entity.AuctionImg;
import com.kosa.ShareTour.repository.AuctionImgRepository;
import com.kosa.ShareTour.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    private final AuctionImgService auctionImgService;

    private final AuctionImgRepository auctionImgRepository;

    public Long saveAuction(AuctionFormDto auctionFormDto, List<MultipartFile> auctionImgFileList) throws Exception {

        //상품 등록
        Auction auction = auctionFormDto.createAuction();
        auctionRepository.save(auction);

        //이미지 등록
        for (int i = 0; i < auctionImgFileList.size(); i++) {
            AuctionImg auctionImg = new AuctionImg();
            auctionImg.setAuction(auction);

            if (i == 0)
                auctionImg.setRepimgYn("Y");
            else
                auctionImg.setRepimgYn("N");

            auctionImgService.saveAuctionImg(auctionImg, auctionImgFileList.get(i));
        }

        return auction.getId();
    }


    @Transactional(readOnly = true)     //상품데이터를 읽어오는 Transaction을 읽기 전용으로 설정(JPA가 더티체킹을 수행하지 않아 성능 향상 가능)
    public AuctionFormDto getAuctionDtl(Long auctionId) {

        //상품의 이미지 조회, 등록순으로 가져오기위해 오름차순 정렬
        List<AuctionImg> auctionImgList = auctionImgRepository.findByAuctionIdOrderByIdAsc(auctionId);

        List<AuctionImgDto> auctionImgDtoList = new ArrayList<>();
        for (AuctionImg auctionImg : auctionImgList) {       //조회한 AuctionImg 엔티티를 AuctionImgDto 객체로 만들어서 리스트에 추가
            AuctionImgDto auctionImgDto = AuctionImgDto.of(auctionImg);
            auctionImgDtoList.add(auctionImgDto);
        }

        Auction auction = auctionRepository.findById(auctionId)         //상품 아이디를 통해 상품 엔티티를 조회
                .orElseThrow(EntityNotFoundException::new);     //존재하지 않을때는 EntityNotFoundException을 발생
        AuctionFormDto auctionFormDto = AuctionFormDto.of(auction);
        auctionFormDto.setAuctionImgDtoList(auctionImgDtoList);
        return auctionFormDto;
    }

    public Long updateAuction(AuctionFormDto auctionFormDto, List<MultipartFile> auctionImgFileList) throws Exception {
        //상품 수정
        Auction auction = auctionRepository.findById(auctionFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        //상품 등록 화면으로부터 전달 받은 상품 아이디를 이용하여 상품 엔티티를 조회

        auction.updateAuction(auctionFormDto);
        //상품 등록 화면으로부터 전달 받은 AuctionFormDto를 통해 상품 엔티티를 업데이트

        List<Long> auctionImgIds = auctionFormDto.getAuctionImgIds();
        //상품 이미지 아이디 리스트를 조회

        //이미지 등록
        for (int i = 0; i < auctionImgFileList.size(); i++) {
            auctionImgService.updateAuctionImg(auctionImgIds.get(i),
                    auctionImgFileList.get(i));        //updatetAuctionImg 메소드에 상품이미지와 상품 이미지 파일정보를 파라미터로 전달
        }

        return auction.getId();
    }

    @Transactional(readOnly = true)
    public Page<Auction> getAdminAuctionPage(AuctionSearchDto auctionSearchDto, Pageable pageable) {
        return auctionRepository.getAdminAuctionPage(auctionSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainAuctionDto> getMainAuctionPage(AuctionSearchDto auctionSearchDto, Pageable pageable) {
        return auctionRepository.getMainAuctionPage(auctionSearchDto, pageable);
    }
}
