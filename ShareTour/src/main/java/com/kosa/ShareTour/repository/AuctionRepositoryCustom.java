package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.dto.AuctionSearchDto;
import com.kosa.ShareTour.dto.MainAuctionDto;
import com.kosa.ShareTour.entity.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionRepositoryCustom {

    Page<Auction> getAdminAuctionPage(AuctionSearchDto auctionSearchDto, Pageable pageable);

    //Main 페이지 메소드를 해당 파일에 구현
    Page<MainAuctionDto> getMainAuctionPage(AuctionSearchDto auctionSearchDto, Pageable pageable);
}
