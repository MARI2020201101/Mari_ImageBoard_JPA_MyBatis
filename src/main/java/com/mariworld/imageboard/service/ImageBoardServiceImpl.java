package com.mariworld.imageboard.service;

import com.mariworld.imageboard.dto.ImageBoardDTO;
import com.mariworld.imageboard.dto.PageRequestDTO;
import com.mariworld.imageboard.dto.PageResultDTO;
import com.mariworld.imageboard.entity.Image;
import com.mariworld.imageboard.entity.ImageBoard;
import com.mariworld.imageboard.entity.Member;
import com.mariworld.imageboard.repository.ImageBoardRepository;
import com.mariworld.imageboard.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageBoardServiceImpl implements ImageBoardService{
    private final ImageBoardRepository imageBoardRepository;
    private final ImageRepository imageRepository;

    @Override @Transactional
    public Long register(ImageBoardDTO imageBoardDTO) {
        Map<String, Object> map = DtoToEntity(imageBoardDTO);
        ImageBoard imageBoard = (ImageBoard) map.get("imageBoard");
        List<Image> imageList = (List<Image>) map.get("imageList");

        imageBoardRepository.save(imageBoard);
        imageList.forEach(image -> imageRepository.save(image));

        return imageBoard.getIbno();
    }

    @Override
    public PageResultDTO<ImageBoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        PageRequest pageRequest = pageRequestDTO.makePageRequest(Sort.by("ibno").descending());
        Page<Object[]> result = imageBoardRepository.getImageBoardList(pageRequest);
        Function<Object[], ImageBoardDTO> fn
                = (en-> entitiesToDTO((ImageBoard) en[0], Arrays.asList((Image)en[1]), (Member) en[2]));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public List<ImageBoardDTO> read(Long ibno) {
        List<Object[]> result = imageBoardRepository.getImageBoardWithAll(ibno);
        List<ImageBoardDTO> imageBoardDTOList = result.stream()
                .map(en-> entitiesToDTO((ImageBoard) en[0], Arrays.asList((Image)en[1]), (Member) en[2]))
                .collect(Collectors.toList());
        return imageBoardDTOList;
    }
}
