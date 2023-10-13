package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>{
}
