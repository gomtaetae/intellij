package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.entity.AuctionOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionOrderRepository extends JpaRepository<AuctionOrder, Long> {

    @Query("select o from AuctionOrder o " +
            "where o.member.email = :email " +
            "order by o.auctionOrderDate desc"
    )
    List<AuctionOrder> findAuctionOrders(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from AuctionOrder o " +
            "where o.member.email = :email"
    )
    Long countAuctionOrder(@Param("email") String email);
}
