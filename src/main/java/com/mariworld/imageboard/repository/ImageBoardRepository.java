package com.mariworld.imageboard.repository;

import com.mariworld.imageboard.entity.ImageBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageBoardRepository extends JpaRepository<ImageBoard, Long> {

    @Query("select ib, i, m from ImageBoard ib " +
            "left join Image i on i.imageBoard = ib " +
            "left join Member m on ib.member = m " )
    Page<Object[]> getImageBoardList(PageRequest pageRequest);

    @Query("select ib, i, m from ImageBoard ib " +
            "left join Image i on i.imageBoard = ib left join Member m on ib.member = m " +
            "where ib.ibno =:ibno group by i" )
    List<Object[]> getImageBoardWithAll(@Param("ibno") Long ibno);
}
