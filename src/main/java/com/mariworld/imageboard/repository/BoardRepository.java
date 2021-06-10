package com.mariworld.imageboard.repository;

import com.mariworld.imageboard.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b,m from Board b left join b.member m")
    List<Object[]> getBoardWithMember();

    @Query(value ="select b,m from Board b left join b.member m"
            ,countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithMember(PageRequest pageRequest);

    @Query(value ="select b,m from Board b left join b.member m " +
            "where b.bno =:bno"
            ,countQuery = "select count(b) from Board b")
    Object getBoardWithMember(@Param("bno")Long bno);
}
