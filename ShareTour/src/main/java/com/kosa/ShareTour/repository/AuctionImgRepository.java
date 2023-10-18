package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.entity.AuctionImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionImgRepository extends JpaRepository<AuctionImg, Long> {

    List<AuctionImg> findByAuctionIdOrderByIdAsc(Long auctionId);

    AuctionImg findByAuctionIdAndRepimgYn(Long auctionId, String repimgYn);
}
