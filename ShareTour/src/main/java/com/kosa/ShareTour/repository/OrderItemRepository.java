package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
