package com.luv2code.springboot.demo.mycoolapp.models.services;
import com.luv2code.springboot.demo.mycoolapp.models.entities.OrderItem;
import com.luv2code.springboot.demo.mycoolapp.models.entities.Product;
import com.luv2code.springboot.demo.mycoolapp.models.entities.PurchaseOrder;
import com.luv2code.springboot.demo.mycoolapp.models.repositories.OrderItemRepository;
import com.luv2code.springboot.demo.mycoolapp.models.repositories.ProductRepository;
import com.luv2code.springboot.demo.mycoolapp.models.repositories.PurchaseOrderRepository;
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
    private ProductRepository productRepository;

    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    public Optional<PurchaseOrder> findById(Integer id) {
        return purchaseOrderRepository.findById(id);
    }

//    public PurchaseOrder save(PurchaseOrder order) {
//        return orderRepository.save(order);
//    }

    public PurchaseOrder save(PurchaseOrder purchaseOrder) {

        List<OrderItem> orderItems = purchaseOrder.getOrderItems();
        List<Integer> productIds = orderItems.stream().map(item -> item.getProduct().getId()).toList();

        List<Product> products = productRepository.findAllById(productIds);

        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));


        double totalAmount = 0;
        // Link order items to the purchase order
        for (OrderItem item :orderItems) {
            Product product = productMap.get(item.getProduct().getId());
            if (product == null) {
                throw new RuntimeException("Product not found with id " + item.getProduct().getId());
            }
            item.setProduct(product);
            item.setPurchaseOrder(purchaseOrder);
            totalAmount += product.getPrice() * item.getQuantity();
        }

        purchaseOrder.setOrderItems(orderItems);
        purchaseOrder.setTotalAmount(totalAmount);

        // Save order
        return purchaseOrderRepository.save(purchaseOrder);

    }


    public void deleteById(Integer id) {
        purchaseOrderRepository.deleteById(id);
    }

}
