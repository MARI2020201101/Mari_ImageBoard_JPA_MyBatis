package com.mariworld.imageboard.service;

import com.mariworld.imageboard.dto.BoardDTO;
import com.mariworld.imageboard.dto.PageRequestDTO;
import com.mariworld.imageboard.dto.PageResultDTO;
import com.mariworld.imageboard.entity.Board;
import com.mariworld.imageboard.entity.Member;

import java.util.List;

public interface BoardService {

    List<Object[]> getList()throws Exception;
    List<Board> getListTest()throws Exception;
    //public List<Object[]> getList(PageRequestDTO pageRequestDTO);
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO)throws Exception;
    PageResultDTO<BoardDTO, Object[]> getList_m(PageRequestDTO pageRequestDTO) throws Exception;
    Long register(BoardDTO dto)throws Exception;
    BoardDTO read(Long bno)throws Exception;
    void modify(BoardDTO dto)throws Exception;
    void remove(Long bno)throws Exception;

    default BoardDTO entityToDTO(Board board, Member member){
        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .email(member.getEmail())
                .name(member.getName())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();
        return dto;
    }

    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder().email(dto.getEmail()).build();
        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .build();
        return board;
    }

}
