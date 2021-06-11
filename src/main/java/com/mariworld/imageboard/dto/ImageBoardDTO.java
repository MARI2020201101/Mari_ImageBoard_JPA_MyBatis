package com.mariworld.imageboard.dto;

import com.mariworld.imageboard.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageBoardDTO {

    private Long ibno;
    private String text;
    private String email;
    private String name;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @Builder.Default
    private List<ImageDTO> imageDTOList = new ArrayList<>();
}
