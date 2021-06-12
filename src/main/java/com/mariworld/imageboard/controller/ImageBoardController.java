package com.mariworld.imageboard.controller;

import com.mariworld.imageboard.dto.ImageBoardDTO;
import com.mariworld.imageboard.dto.PageRequestDTO;
import com.mariworld.imageboard.service.ImageBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/image")
public class ImageBoardController {

    private final ImageBoardService imageBoardService;

    @GetMapping("/register")
    public void registerForm(PageRequestDTO pageRequestDTO){
    }

    @PostMapping("/register")
    public String register(ImageBoardDTO imageBoardDTO, RedirectAttributes rttr
            , PageRequestDTO pageRequestDTO){
        log.info("--------------------------img register-----------------");
        log.info(imageBoardDTO.toString());
        Long ibno = imageBoardService.register(imageBoardDTO);
        log.info("\nibno: "+ibno);
        rttr.addFlashAttribute("msg",ibno);

        return "redirect:/image/list";
    }
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("list", imageBoardService.getList(pageRequestDTO));

    }

    @GetMapping("/read")
    public void read(PageRequestDTO pageRequestDTO, Long ibno, Model model){
        model.addAttribute("dto", imageBoardService.read(ibno));
    }
}
