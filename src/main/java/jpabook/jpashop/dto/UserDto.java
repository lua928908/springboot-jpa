package jpabook.jpashop.dto;

import lombok.Data;

@Data
public class UserDto {
    private String name; // entity 에는 username 인데 name으로 바꾸어봄
    private int age;
}
