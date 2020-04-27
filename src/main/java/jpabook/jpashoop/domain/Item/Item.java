package jpabook.jpashoop.domain.Item;

import jpabook.jpashoop.domain.Category;
import jpabook.jpashoop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // stock(재고) 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    // stock(재고) 감소
    public void removeStock(int quantity){
        int restStock = this.stockQuantity -= quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}


/*
    stockQuantity 필드를 가지고 있는 item 엔티티에 수량 증가,감소와 같은 비즈니스 로직을
    만드는것이 더욱 응집도 있고 객체지향적 이므로 도메인인 item엔티티에 비즈니스 로직을 추가함

    item이 변해야하는 경우 setter를 통해 값을 추가하거나 변경하는게 아니라 addStock, removeStock같은 비즈니스 로직을
    만들어서 값을 변경하는것이 적절하다.
*/