package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashoopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashoopApplication.class, args);
	}

	@Bean
	Hibernate5Module hibernate5Module(){
		return new Hibernate5Module(); // 엔티티를 외부에 노출할 때 LAZY 타입으로 반환하면 프록시가 비어있는 상태에서 json을 읽어들이면 에러가난다 이걸 해결하기위해 넣어놓는 건데 사실상 엔티티 자체를 노출하는게 잘못된 방법이기 때문에 실제로 쓸일이 없을것이다.
	}
}
