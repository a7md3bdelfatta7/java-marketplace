package com.springboot.demo.controller.rest;


import com.springboot.demo.controller.input.OrderItemInput;
import com.springboot.demo.controller.dto.PurchaseOrderDTO;
import com.springboot.demo.controller.input.PurchaseOrderInput;
import com.springboot.demo.services.PurchaseOrderService;
import com.springboot.demo.services.vo.OrderItemVO;
import com.springboot.demo.services.vo.PurchaseOrderVO;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class PurchaseOrderRESTController {
    @Autowired
    private PurchaseOrderService orderService;
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void configureMapper() {

        modelMapper.typeMap(PurchaseOrderInput.class, PurchaseOrderVO.class).addMappings(mapper -> {
            mapper.map(PurchaseOrderInput::getCustomerId, PurchaseOrderVO::setCustomerById);
        });

        modelMapper.typeMap(OrderItemInput.class, OrderItemVO.class).addMappings(mapper -> {
            mapper.map(OrderItemInput::getProductId, OrderItemVO::setProductById);
            mapper.skip(OrderItemVO::setId);
        });
    }

    @GetMapping
    public List<PurchaseOrderDTO> getAllOrders() {
        List<PurchaseOrderVO> orders = orderService.findAll();
        return orders.stream().map(order -> modelMapper.map(order, PurchaseOrderDTO.class)).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> getOrderById(@PathVariable Integer id) {
        Optional<PurchaseOrderVO> purchaseOrder = orderService.findById(id);
        return purchaseOrder.map(order -> ResponseEntity.ok(modelMapper.map(order, PurchaseOrderDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PurchaseOrderVO createOrder(@RequestBody PurchaseOrderInput order) {
        return orderService.save(modelMapper.map(order, PurchaseOrderVO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> updateOrder(@PathVariable Integer id, @RequestBody PurchaseOrderInput orderDetails) {
        Optional<PurchaseOrderVO> order = orderService.findById(id);
        if (order.isPresent()) {
            PurchaseOrderVO updatedOrder = order.get();
            updatedOrder.setOrderDate(orderDetails.getOrderDate());
            updatedOrder.setTotalAmount(orderDetails.getTotalAmount());
            updatedOrder.setCustomerById(orderDetails.getCustomerId());
            updatedOrder.setOrderItems(orderDetails.getOrderItems().stream().map(orderItem ->
                    modelMapper.map(orderItem, OrderItemVO.class)).toList());
            return ResponseEntity.ok(modelMapper.map(orderService.save(updatedOrder), PurchaseOrderDTO.class));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        Optional<PurchaseOrderVO> order = orderService.findById(id);
        if (order.isPresent()) {
            orderService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
