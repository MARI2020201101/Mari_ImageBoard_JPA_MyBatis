package com.mariworld.imageboard.repository;

import com.mariworld.imageboard.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ImageRepository extends JpaRepository<Image, Long> {

    @Modifying
    @Query("delete from Image i where i.imageBoard.ibno=:ibno ")
    void deleteByIbno(@Param("ibno") Long ibno);
}
