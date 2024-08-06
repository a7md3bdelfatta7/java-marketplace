package com.luv2code.springboot.demo.controller;
import com.luv2code.springboot.demo.controller.dto.OrderItemDTO;
import com.luv2code.springboot.demo.controller.dto.PurchaseOrderDTO;
import com.luv2code.springboot.demo.repository.entities.PurchaseOrder;
import com.luv2code.springboot.demo.services.PurchaseOrderService;
import com.luv2code.springboot.demo.services.vo.OrderItemVO;
import com.luv2code.springboot.demo.services.vo.PurchaseOrderVO;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void configureMapper() {
        modelMapper.typeMap(OrderItemDTO.class, OrderItemVO.class).addMappings(mapper -> {
            mapper.skip(OrderItemVO::setPrice);
            mapper.skip(OrderItemVO::setOrderId);
            mapper.skip(OrderItemVO::setId);
        });
    }

    @GetMapping
    public List<PurchaseOrderDTO> getAllOrders() {
        List<PurchaseOrderVO> orders = orderService.findAll();
        return orders.stream().map(order -> modelMapper.map(order, PurchaseOrderDTO.class)).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getOrderById(@PathVariable Integer id) {
        Optional<PurchaseOrder> purchaseOrder = orderService.findById(id);
        return purchaseOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PurchaseOrderVO createOrder(@RequestBody PurchaseOrderDTO order) {
        return orderService.save(modelMapper.map(order, PurchaseOrderVO.class));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<PurchaseOrderDTO> updateOrder(@PathVariable Integer id, @RequestBody PurchaseOrderDTO orderDetails) {
//        Optional<PurchaseOrderVO> order = orderService.findById(id);
//        if (order.isPresent()) {
//            PurchaseOrderVO updatedOrder = order.get();
//            updatedOrder.setOrderDate(orderDetails.getOrderDate());
//            updatedOrder.setTotalAmount(orderDetails.getTotalAmount());
//            updatedOrder.setCustomerId(orderDetails.getCustomerId());
//            updatedOrder.setOrderItems(orderDetails.getOrderItems().stream().map(orderItem -> modelMapper.map(orderItem, OrderItemVO.class)).collect(Collectors.toList()));
//            return ResponseEntity.ok(modelMapper.map(orderService.save(updatedOrder), PurchaseOrderDTO.class));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

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
