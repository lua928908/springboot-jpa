package jpabook.jpashop.repository;

import jpabook.jpashop.dto.MemberSearchCondition;
import jpabook.jpashop.dto.MemberTeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
    Page<MemberTeamDto> searchPlageSimple(MemberSearchCondition condition, Pageable pageable);
    Page<MemberTeamDto> searchPlageComplex(MemberSearchCondition condition, Pageable pageable);
}
