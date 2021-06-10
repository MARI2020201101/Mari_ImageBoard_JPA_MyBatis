package com.mariworld.imageboard.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@Builder
@NoArgsConstructor
public class PageRequestDTO {
    @Builder.Default
    private int page=1;
    @Builder.Default
    private int size=10;

    public PageRequestDTO(int page, int size){
        this.page=page -1 ;
        this.size=size;
    }
    public PageRequest makePageRequest(Sort sort){
        return PageRequest.of(page-1,size, sort);
    }

    public static void main(String[] args){
        PageRequestDTO dto = new PageRequestDTO();
        System.out.println("dto.getPage() " + dto.getPage());

}

}


