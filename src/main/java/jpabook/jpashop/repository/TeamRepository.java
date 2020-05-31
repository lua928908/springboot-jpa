package jpabook.jpashop.repository;

import jpabook.jpashop.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
