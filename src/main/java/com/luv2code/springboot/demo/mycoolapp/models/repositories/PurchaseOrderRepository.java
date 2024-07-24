package com.luv2code.springboot.demo.mycoolapp.models.repositories;

import com.luv2code.springboot.demo.mycoolapp.models.entities.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
}