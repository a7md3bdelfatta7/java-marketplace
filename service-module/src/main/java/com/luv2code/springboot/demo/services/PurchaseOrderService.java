package com.luv2code.springboot.demo.services;
import com.luv2code.springboot.demo.repository.entities.Customer;
import com.luv2code.springboot.demo.repository.entities.OrderItem;
import com.luv2code.springboot.demo.repository.entities.Product;
import com.luv2code.springboot.demo.repository.entities.PurchaseOrder;
import com.luv2code.springboot.demo.repository.OrderItemRepository;
import com.luv2code.springboot.demo.repository.ProductRepository;
import com.luv2code.springboot.demo.repository.PurchaseOrderRepository;
import com.luv2code.springboot.demo.services.vo.CustomerVO;
import com.luv2code.springboot.demo.services.vo.OrderItemVO;
import com.luv2code.springboot.demo.services.vo.PurchaseOrderVO;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void configureMapper() {
        modelMapper.typeMap(PurchaseOrderVO.class, PurchaseOrder.class).addMappings(mapper -> {
            mapper.map(PurchaseOrderVO::getCustomerId, PurchaseOrder::setCustomerById);
            mapper.skip(PurchaseOrder::setOrderItems);
        });

        modelMapper.typeMap(OrderItemVO.class, OrderItem.class).addMappings(mapper -> {
            mapper.map(OrderItemVO::getProductId, OrderItem::setProductById);
        });

    }


    public List<PurchaseOrderVO> findAll() {
        List<PurchaseOrder> entities = purchaseOrderRepository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, PurchaseOrderVO.class))
                .collect(Collectors.toList());
    }

    public Optional<PurchaseOrder> findById(Integer id) {
        return  purchaseOrderRepository.findById(id);
    }

    public PurchaseOrderVO save(PurchaseOrderVO purchaseOrderVO) {
        //mapping
        List<OrderItemVO> orderItemsVO = purchaseOrderVO.getOrderItems();
        List<OrderItem> orderItems = orderItemsVO.stream()
                .map(itemVO -> modelMapper.map(itemVO, OrderItem.class))
                .collect(Collectors.toList());
        PurchaseOrder purchaseOrder = modelMapper.map(purchaseOrderVO, PurchaseOrder.class);


        //internal logic
        List<Integer> productIds = orderItems.stream().map(item -> item.getProduct().getId()).toList();
        List<Product> products = productRepository.findAllById(productIds);
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        double totalAmount = 0;
        // Link order items to the purchase order
        for (OrderItem item :orderItems) {
            Product product = productMap.get(item.getProduct().getId());
            if (product == null) {
                throw new RuntimeException("Product not found with id " + item.getId());
            }
            item.setProduct(product);
            item.setPurchaseOrder(purchaseOrder);
            totalAmount += product.getPrice() * item.getQuantity();
        }

        purchaseOrder.setOrderItems(orderItems);
        purchaseOrder.setTotalAmount(totalAmount);

        // Save order
        return modelMapper.map(purchaseOrderRepository.save(purchaseOrder), PurchaseOrderVO.class);
    }


    public void deleteById(Integer id) {
        purchaseOrderRepository.deleteById(id);
    }

}
