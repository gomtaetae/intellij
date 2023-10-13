package com.kosa.ShareTour.repository;

import com.kosa.ShareTour.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CartRepository extends JpaRepository<Cart, Long>{
}
