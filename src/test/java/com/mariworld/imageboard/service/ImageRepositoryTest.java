package com.mariworld.imageboard.service;

import com.mariworld.imageboard.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

@SpringBootTest
public class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Test @Transactional @Commit
    public void deleteTest(){
        imageRepository.deleteByIbno(1L);
    }
}
