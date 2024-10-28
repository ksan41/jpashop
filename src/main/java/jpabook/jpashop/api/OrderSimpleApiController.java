package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import jpabook.jpashop.service.OrderService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("api/v2/orders")
    public List<SimpleOrderDto> getOrdersV2() {
        List<Order> findOrders = orderService.findOrders(new OrderSearch());
        List<SimpleOrderDto> orders = findOrders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return orders;
    }

    // fetch join 적용
    @GetMapping("api/v3/orders")
    public List<SimpleOrderDto> getOrdersV3() {
        List<Order> findOrders = orderRepository.findAllWithMemberAndDelivery();
        List<SimpleOrderDto> orders = findOrders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return orders;
    }

    @GetMapping("api/v4/orders")
    public List<OrderSimpleQueryDto> getOrdersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    @NoArgsConstructor
    static class SimpleOrderDto {

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
    }
}
