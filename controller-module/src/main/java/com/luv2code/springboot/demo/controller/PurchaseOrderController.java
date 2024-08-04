package com.luv2code.springboot.demo.controller;

import com.luv2code.springboot.demo.models.entities.PurchaseOrder;
import com.luv2code.springboot.demo.models.services.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService orderService;

    @GetMapping
    public List<PurchaseOrder> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getOrderById(@PathVariable Integer id) {
        Optional<PurchaseOrder> order = orderService.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PurchaseOrder createOrder(@RequestBody PurchaseOrder order) {
        return orderService.save(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrder> updateOrder(@PathVariable Integer id, @RequestBody PurchaseOrder orderDetails) {
        Optional<PurchaseOrder> order = orderService.findById(id);
        if (order.isPresent()) {
            PurchaseOrder updatedOrder = order.get();
            updatedOrder.setOrderDate(orderDetails.getOrderDate());
            updatedOrder.setTotalAmount(orderDetails.getTotalAmount());
            updatedOrder.setCustomer(orderDetails.getCustomer());
            updatedOrder.setOrderItems(orderDetails.getOrderItems());
            return ResponseEntity.ok(orderService.save(updatedOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        Optional<PurchaseOrder> order = orderService.findById(id);
        if (order.isPresent()) {
            orderService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
