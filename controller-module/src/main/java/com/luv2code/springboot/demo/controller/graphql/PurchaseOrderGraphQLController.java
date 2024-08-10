package com.luv2code.springboot.demo.controller.graphql;

import com.luv2code.springboot.demo.controller.input.OrderItemInput;
import com.luv2code.springboot.demo.controller.dto.PurchaseOrderDTO;
import com.luv2code.springboot.demo.controller.input.PurchaseOrderInput;
import com.luv2code.springboot.demo.services.PurchaseOrderService;
import com.luv2code.springboot.demo.services.vo.OrderItemVO;
import com.luv2code.springboot.demo.services.vo.PurchaseOrderVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PurchaseOrderGraphQLController {

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

    @QueryMapping
    public List<PurchaseOrderDTO> getAllOrders() {
        List<PurchaseOrderVO> orders = orderService.findAll();
        return orders.stream().map(order -> modelMapper.map(order, PurchaseOrderDTO.class)).collect(Collectors.toList());
    }

    @QueryMapping
    public PurchaseOrderDTO getOrderById(@Argument Integer id) {
        Optional<PurchaseOrderVO> purchaseOrder = orderService.findById(id);
        return purchaseOrder.map(order -> modelMapper.map(order, PurchaseOrderDTO.class)).orElse(null);
    }

    @MutationMapping
    public PurchaseOrderDTO createOrder(@Argument PurchaseOrderInput input) {
        PurchaseOrderVO purchaseOrderVO = modelMapper.map(input, PurchaseOrderVO.class);
        return modelMapper.map(orderService.save(purchaseOrderVO), PurchaseOrderDTO.class);
    }

    @MutationMapping
    public PurchaseOrderDTO updateOrder(@Argument Integer id, @Argument PurchaseOrderInput input) {
        Optional<PurchaseOrderVO> order = orderService.findById(id);
        if (order.isPresent()) {
            PurchaseOrderVO updatedOrder = order.get();
            updatedOrder.setOrderDate(input.getOrderDate());
            updatedOrder.setCustomerById(input.getCustomerId());
            updatedOrder.setOrderItems(input.getOrderItems().stream()
                    .map(orderItem -> modelMapper.map(orderItem, OrderItemVO.class))
                    .collect(Collectors.toList()));
            return modelMapper.map(orderService.save(updatedOrder), PurchaseOrderDTO.class);
        } else {
            return null;
        }
    }

    @MutationMapping
    public Boolean deleteOrder(@Argument Integer id) {
        Optional<PurchaseOrderVO> order = orderService.findById(id);
        if (order.isPresent()) {
            orderService.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}