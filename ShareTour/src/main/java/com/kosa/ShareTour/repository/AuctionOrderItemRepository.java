package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionOrderItemRepository  extends JpaRepository<OrderItem, Long> {
}
