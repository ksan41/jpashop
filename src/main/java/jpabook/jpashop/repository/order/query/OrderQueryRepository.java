package jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import jpabook.jpashop.domain.OrderItem;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> findDtos = findOrders();

        findDtos.forEach(d -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(d.getOrderId());
            d.setOrderItems(orderItems);
        });
        return findDtos;
    }

    public List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                "from OrderItem oi " +
                "join oi.item i " +
                "where oi.order.id=:orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<OrderQueryDto> findOrders() {
        return em.createQuery("select new jpabook.jpashop.repository.order.query." +
                        "OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        "from Order o " +
                "join o.member m " +
                "join o.delivery d ", OrderQueryDto.class)
                .getResultList();
    }

    public List<OrderQueryDto> findAllByDtos() {
        List<OrderQueryDto> findDtos = findOrders();
        List<Long> orderIds = toOrderIds(findDtos);

        Map<Long, List<OrderItemQueryDto>> map = findOrderItemMap(orderIds);
        findDtos.forEach(o -> o.setOrderItems(map.get(o.getOrderId())));
        return findDtos;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems =
                em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                                "from OrderItem oi " +
                                "join oi.item i " +
                                "where oi.order.id in :orderIds", OrderItemQueryDto.class)
                        .setParameter("orderIds", orderIds)
                        .getResultList();
        return orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));
    }

    private List<Long> toOrderIds(List<OrderQueryDto> findDtos) {
        return findDtos.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
    }

    public List<OrderFlatDto> findAllByDtosFlat() {
        return em.createQuery("select new " +
                        "jpabook.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count) " +
                "from Order o " +
                "join o.member m " +
                "join o.delivery d " +
                "join o.orderItems oi " +
                "join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
