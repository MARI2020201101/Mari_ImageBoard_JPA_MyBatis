package com.mariworld.imageboard.mapper;

import com.mariworld.imageboard.dto.BoardDTO;
import com.mariworld.imageboard.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int countAll();
    List<BoardDTO> getListTest();
    List<BoardDTO> getListTest2(PageRequestDTO pageRequestDTO);
    List<BoardDTO> getList(PageRequestDTO pageRequestDTO);

}
