package jpabook.jpashop;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.entity.Hello;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class JpashoopApplicationTests {
	@Autowired
	EntityManager em;
}
