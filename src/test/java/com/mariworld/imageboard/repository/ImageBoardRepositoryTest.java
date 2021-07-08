package com.mariworld.imageboard.repository;

import com.mariworld.imageboard.entity.Image;
import com.mariworld.imageboard.entity.ImageBoard;
import com.mariworld.imageboard.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@SpringBootTest
public class ImageBoardRepositoryTest {

    @Autowired
    private ImageBoardRepository imageBoardRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Test @Transactional @Commit
    public void insertTest(){

        LongStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder().email("user"+i+"@mail.com").build();
            ImageBoard imageBoard = ImageBoard.builder()
                    .text("텍스트...."+i).member(member).build();

            imageBoardRepository.save(imageBoard);

            int rannum =(int)(Math.random()*5)+1;
            for(int n=0 ; n<rannum; n++){

                Image image = Image.builder()
                        .uuid(UUID.randomUUID().toString())
                        .imgName("image"+(n+1)+".jpg")
                        .path(null)
                        .imageBoard(imageBoard)
                        .build();
                imageRepository.save(image);
            }
        });
    }

    @Test
    public void getListTest(){
        PageRequest pageRequest = PageRequest.of(0,10).withSort(Sort.by("ibno").descending());
        Page<Object[]> result = imageBoardRepository.getImageBoardList(pageRequest);
        result.stream().map(arr -> Arrays.asList(arr)).forEach(System.out::println);
    }

    @Test
    public void getImageBoardWithAllTest(){

       List<Object[]> result = imageBoardRepository.getImageBoardWithAll(101L);
        result.stream().map(arr -> Arrays.asList(arr)).forEach(System.out::println);
    }
}
