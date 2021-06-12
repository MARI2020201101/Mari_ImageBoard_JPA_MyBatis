package com.mariworld.imageboard.service;

import com.mariworld.imageboard.dto.ImageBoardDTO;
import com.mariworld.imageboard.dto.ImageDTO;
import com.mariworld.imageboard.dto.PageRequestDTO;
import com.mariworld.imageboard.dto.PageResultDTO;
import com.mariworld.imageboard.entity.ImageBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class ImageBoardServiceTest {

    @Autowired
    private ImageBoardService imageBoardService;

    @Test
    public void getListTest(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResultDTO<ImageBoardDTO, Object[]> result= imageBoardService.getList(pageRequestDTO);

        result.getPageList().stream().forEach(System.out::println);
    }
    @Test
    public void readTest(){
        imageBoardService.read(80L);
    }
}
