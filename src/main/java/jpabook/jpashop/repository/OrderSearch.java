package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
// 검색 조건 파라미터

    private String memberName; //회원 이름
    private OrderStatus orderStatus; //주문상태(ORDER,CANCEL)
}
