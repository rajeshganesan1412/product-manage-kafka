package com.product.management.consumer;

import com.product.management.model.Orders;
import com.product.management.model.Product;
import com.product.management.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderNotificationConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "${order.message.topic}", groupId = "${order.consumer.group}")
    public void consume(ConsumerRecord<String, Orders> consumerRecord) {

        List<Product> productList = productService.updateProductQuantityAfterOrderPlaced(consumerRecord.value());
        productList.forEach(System.out::println);
    }

}
