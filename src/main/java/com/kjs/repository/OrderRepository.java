package com.kjs.repository;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kjs.Entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

	Page<Orders> findByUserId(Long userId, Pageable pageable);
}
