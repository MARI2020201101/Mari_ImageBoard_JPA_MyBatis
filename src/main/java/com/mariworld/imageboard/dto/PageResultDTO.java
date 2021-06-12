package com.mariworld.imageboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResultDTO<DTO, EN> {
    private int page;
    private int size;
    private boolean prev,next;
    private int total; //총 페이지 수
    private int start,end;
    private List<Integer> pageList;
    private List<DTO> dtoList;

    public PageResultDTO(Page<EN> entity, Function<EN,DTO> fn){
       this.total = entity.getTotalPages();
       this.size = entity.getSize();
       this.page = entity.getNumber()+1;
       makePages();
       this.dtoList = entity.map(fn).stream().collect(Collectors.toList());
    }

    public PageResultDTO(PageRequestDTO pageRequestDTO, Map<Integer, List<DTO>> result){
        this.total = (int)result.keySet().toArray()[0];
        this.size = pageRequestDTO.getSize();
        this.page = pageRequestDTO.getPage() +1;
        makePages();
        this.dtoList=result.get(total);
    }

    private void makePages(){
        int tempEnd = (int)(Math.ceil(page/10.0))*10;
        this.end = tempEnd > total? total:tempEnd;
        this.start = end<10? 1 : end-9;
        this.prev = start>1;
        this.next = end !=total;
        this.pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }

}
