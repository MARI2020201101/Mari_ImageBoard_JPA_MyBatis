package com.mariworld.imageboard.service;

import com.mariworld.imageboard.dto.ImageBoardDTO;
import com.mariworld.imageboard.dto.ImageDTO;
import com.mariworld.imageboard.dto.PageRequestDTO;
import com.mariworld.imageboard.dto.PageResultDTO;
import com.mariworld.imageboard.entity.Image;
import com.mariworld.imageboard.entity.ImageBoard;
import com.mariworld.imageboard.entity.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ImageBoardService {

    Long register(ImageBoardDTO imageBoardDTO)throws Exception;
    PageResultDTO<ImageBoardDTO,Object[]> getList(PageRequestDTO pageRequestDTO)throws Exception;
    ImageBoardDTO read(Long ibno)throws Exception;
    void remove(Long ibno)throws Exception;

    default Map<String, Object> DtoToEntity(ImageBoardDTO imageBoardDTO){

        Map<String, Object> map = new HashMap<>();

        ImageBoard imageBoard = ImageBoard.builder()
                .text(imageBoardDTO.getText())
                .member(Member.builder().email(imageBoardDTO.getEmail()).build())
                .build();
        map.put("imageBoard",imageBoard);

        List<ImageDTO> imageDTOList = imageBoardDTO.getImageDTOList();

        if(imageDTOList != null && imageDTOList.size()>0){
            List<Image> imageList = new ArrayList<>();
            imageDTOList.stream().forEach(img->{
                Image image = Image.builder()
                        .imgName(img.getImgName())
                        .path(img.getPath())
                        .uuid(img.getUuid())
                        .imageBoard(imageBoard)
                        .build();
                imageList.add(image);
            });
            map.put("imageList",imageList);
        }

        return map;
    }

    default ImageBoardDTO entitiesToDTO(ImageBoard imageBoard, List<Image> imageList, Member member){
        ImageBoardDTO imageBoardDTO = ImageBoardDTO.builder()
                .ibno(imageBoard.getIbno())
                .text(imageBoard.getText())
                .email(member.getEmail())
                .name(member.getName())
                .regDate(imageBoard.getRegDate())
                .modDate(imageBoard.getModDate())
                .imageDTOList(imageList.stream().map(
                        img-> {
                            ImageDTO imageDTO = ImageDTO.builder().path(img.getPath())
                                    .imgName(img.getImgName())
                                    .uuid(img.getUuid()).build();

                            return imageDTO;
                        }).collect(Collectors.toList())).build();

/*        if(imageList!=null && imageList.size()>0){
            List<ImageDTO> imageDTOList = new ArrayList<>();
            imageList.forEach(
                    img->{ ImageDTO imageDTO = ImageDTO.builder()
                    .path(img.getPath())
                    .imgName(img.getImgName())
                    .uuid(img.getUuid()).build();
                imageDTOList.add(imageDTO);
            });

        }*/
        return imageBoardDTO;
    }
}
