package com.mariworld.imageboard.service;

import com.mariworld.imageboard.dto.BoardDTO;
import com.mariworld.imageboard.dto.PageRequestDTO;
import com.mariworld.imageboard.dto.PageResultDTO;
import com.mariworld.imageboard.entity.Board;
import com.mariworld.imageboard.entity.Member;
import com.mariworld.imageboard.mapper.BoardMapper;
import com.mariworld.imageboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;
    @Override
    public List<Object[]> getList()throws Exception {
        return boardRepository.getBoardWithMember();
    }

    @Override
    public List<Board> getListTest()throws Exception {
        return boardRepository.findAll();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) throws Exception{
        PageRequest pageRequest = pageRequestDTO.makePageRequest(Sort.by("bno").descending());

        Page<Object[]> entity = boardRepository.getBoardWithMember(pageRequest);

        Function<Object[],BoardDTO> fn = (en -> entityToDTO(
                (Board) en[0],(Member) en[1]
        ));
        return new PageResultDTO<>(entity,fn);
    }

    @Override @Transactional
    public PageResultDTO<BoardDTO, Object[]> getList_m(PageRequestDTO pageRequestDTO) throws Exception{
        List<BoardDTO> dtoList = boardMapper.getList(pageRequestDTO);
        int count =boardMapper.countAll();

        Map<Integer, List<BoardDTO>> list = new HashMap<>();
        list.put(count, dtoList);

        return new PageResultDTO<>(pageRequestDTO, list);
    }

    @Override
    public Long register(BoardDTO dto) throws Exception{
        Member member = Member.builder().email(dto.getEmail()).build();
        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .build();
        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public BoardDTO read(Long bno) throws Exception{
        Object result = boardRepository.getBoardWithMember(bno);
        Object[] arr = (Object[])result;
        return entityToDTO((Board) arr[0],(Member)arr[1]);
    }

    @Override
    public void modify(BoardDTO dto) throws Exception{
        Board board = boardRepository.findById(dto.getBno()).get();
        if(board!=null){
            board.changeTitle(dto.getTitle());
            board.changeContent(dto.getContent());
            boardRepository.save(board);
        }
    }

    @Override
    public void remove(Long bno)throws Exception {
        boardRepository.deleteById(bno);
    }
}
