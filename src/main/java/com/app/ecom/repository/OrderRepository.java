package com.app.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ecom.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
