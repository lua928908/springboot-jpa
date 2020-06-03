package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class JpashoopApplication {
	public static void main(String[] args) {
		SpringApplication.run(JpashoopApplication.class, args);
	}
}
