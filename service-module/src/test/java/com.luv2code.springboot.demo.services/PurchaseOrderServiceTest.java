package com.luv2code.springboot.demo.services;

import com.luv2code.springboot.demo.repository.OrderItemRepository;
import com.luv2code.springboot.demo.repository.ProductRepository;
import com.luv2code.springboot.demo.repository.PurchaseOrderRepository;
import com.luv2code.springboot.demo.repository.entities.OrderItem;
import com.luv2code.springboot.demo.repository.entities.Product;
import com.luv2code.springboot.demo.repository.entities.PurchaseOrder;
import com.luv2code.springboot.demo.services.vo.OrderItemVO;
import com.luv2code.springboot.demo.services.vo.PurchaseOrderVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseOrderServiceTest {

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PurchaseOrderService purchaseOrderService;

    private PurchaseOrder purchaseOrderEntity;
    private PurchaseOrderVO purchaseOrderVO;
    private Product productEntity;
    private OrderItem orderItemEntity;
    private OrderItemVO orderItemVO;

    @BeforeEach
    void setUp() {
        // Setting up Product
        productEntity = new Product();
        productEntity.setId(1);
        productEntity.setName("Test Product");
        productEntity.setPrice(100.0);

        // Setting up Order Item
        orderItemEntity = new OrderItem();
        orderItemEntity.setId(1);
        orderItemEntity.setProduct(productEntity);
        orderItemEntity.setQuantity(2);

        orderItemVO = new OrderItemVO();
        orderItemVO.setProductById(Long.getLong(productEntity.getId().toString()));
        orderItemVO.setQuantity(2);

        // Setting up Purchase Order
        purchaseOrderEntity = new PurchaseOrder();
        purchaseOrderEntity.setId(1);
        purchaseOrderEntity.setTotalAmount(200.0);
        purchaseOrderEntity.setOrderItems(Collections.singletonList(orderItemEntity));

        purchaseOrderVO = new PurchaseOrderVO();
        purchaseOrderVO.setId(1L);
        purchaseOrderVO.setOrderItems(Collections.singletonList(orderItemVO));
        purchaseOrderVO.setTotalAmount(200.0);
    }

    @Test
    void testFindAll() {
        when(purchaseOrderRepository.findAll()).thenReturn(Collections.singletonList(purchaseOrderEntity));
        when(modelMapper.map(purchaseOrderEntity, PurchaseOrderVO.class)).thenReturn(purchaseOrderVO);

        List<PurchaseOrderVO> orders = purchaseOrderService.findAll();

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(purchaseOrderVO.getTotalAmount(), orders.get(0).getTotalAmount());
        verify(purchaseOrderRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(purchaseOrderEntity, PurchaseOrderVO.class);
    }

    @Test
    void testFindById() {
        when(purchaseOrderRepository.findById(1)).thenReturn(Optional.of(purchaseOrderEntity));
        when(modelMapper.map(purchaseOrderEntity, PurchaseOrderVO.class)).thenReturn(purchaseOrderVO);

        Optional<PurchaseOrderVO> foundOrder = purchaseOrderService.findById(1);

        assertTrue(foundOrder.isPresent());
        assertEquals(purchaseOrderVO.getTotalAmount(), foundOrder.get().getTotalAmount());
        verify(purchaseOrderRepository, times(1)).findById(1);
        verify(modelMapper, times(1)).map(purchaseOrderEntity, PurchaseOrderVO.class);
    }

    @Test
    void testSave() {
        when(productRepository.findAllById(anyList())).thenReturn(Collections.singletonList(productEntity));
        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenReturn(purchaseOrderEntity);
        when(modelMapper.map(purchaseOrderVO, PurchaseOrder.class)).thenReturn(purchaseOrderEntity);
        when(modelMapper.map(purchaseOrderEntity, PurchaseOrderVO.class)).thenReturn(purchaseOrderVO);
        when(modelMapper.map(orderItemVO, OrderItem.class)).thenReturn(orderItemEntity);

        PurchaseOrderVO savedOrder = purchaseOrderService.save(purchaseOrderVO);

        assertNotNull(savedOrder);
        assertEquals(purchaseOrderVO.getTotalAmount(), savedOrder.getTotalAmount());
        verify(productRepository, times(1)).findAllById(anyList());
        verify(purchaseOrderRepository, times(1)).save(any(PurchaseOrder.class));
        verify(modelMapper, times(1)).map(purchaseOrderVO, PurchaseOrder.class);
        verify(modelMapper, times(1)).map(purchaseOrderEntity, PurchaseOrderVO.class);
    }

    @Test
    void testDeleteById() {
        doNothing().when(purchaseOrderRepository).deleteById(1);

        purchaseOrderService.deleteById(1);

        verify(purchaseOrderRepository, times(1)).deleteById(1);
    }
}