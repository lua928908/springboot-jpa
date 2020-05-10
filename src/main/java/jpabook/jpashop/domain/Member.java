package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @JsonIgnore // api로 json 반환할때 무한루프 방지용으로 owner가 아닌쪽에 어노테이션 붙임
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
