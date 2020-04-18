package jpabook.jpashoop.domain.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // item을 상속한 book,album,movie 글이 있는데 상속전략을 결정한다 (JOINED, SINGLE_TABLE 중 하나를 써야함)
@DiscriminatorColumn(name = "Dtype") // item을 상속한 객체의 이름을 정하는 컬럼이름
@Getter
@Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
}
