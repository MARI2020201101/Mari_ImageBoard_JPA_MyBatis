package com.mariworld.imageboard.repository;

import com.mariworld.bootboardjpamybatis.entity.Board;
import com.mariworld.bootboardjpamybatis.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest(){
        IntStream.rangeClosed(1,100).forEach(
                i->{ Member member = Member.builder().email("user"+i+"@mail.com").build();
                    Board board = Board.builder()
                            .content("content...."+i)
                            .title("title..."+i)
                            .member(member)
                            .build();
                    boardRepository.save(board); }
        );
    }

    @Test
    public void listTest1(){
        boardRepository.getBoardWithMember();
    }

    @Test
    public void listTest2(){
        Page<Object[]> result =
                boardRepository.getBoardWithMember(PageRequest.of(0,10));
        Stream<Object[]> arr = result.get();
        arr.forEach(row->
        {Object[] r = (Object[])row;
            System.out.println(Arrays.toString(r));}

        );
    }

    @Test
    public void readTest(){
        Object result = boardRepository.getBoardWithMember(1L);
        Object[] arr = (Object[])result;
        Arrays.stream(arr).forEach(System.out::println);
    }
}
