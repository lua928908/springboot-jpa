package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> oversV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all; // 무한루프에 빠짐, order를 json으로 뿌려야하는데 order안에 member가 있고 member안에 다시 order가 있기 때문이다. @JsonIgnore 해야함
    }
}
