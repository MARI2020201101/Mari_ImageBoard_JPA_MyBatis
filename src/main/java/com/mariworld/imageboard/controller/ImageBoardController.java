package com.mariworld.imageboard.controller;

import com.mariworld.imageboard.dto.ImageBoardDTO;
import com.mariworld.imageboard.dto.PageRequestDTO;
import com.mariworld.imageboard.security.MemberDTO;
import com.mariworld.imageboard.service.ImageBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public void registerForm(PageRequestDTO pageRequestDTO
            ,@AuthenticationPrincipal MemberDTO memberDTO, Model model){
        model.addAttribute("memberDTO" , memberDTO);

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public String register(ImageBoardDTO imageBoardDTO, RedirectAttributes rttr
            , PageRequestDTO pageRequestDTO){
        Long ibno = imageBoardService.register(imageBoardDTO);
        log.info("\nibno: "+ibno);
        rttr.addFlashAttribute("msg"," 이미지게시글 "+ibno+" 번이 등록되었습니다.");

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

    @PreAuthorize("authentication.principal.username == #email or hasRole('ROLE_ADMIN')")
    @PostMapping("/remove")
    public String remove(Long ibno, RedirectAttributes rttr
            , @AuthenticationPrincipal MemberDTO memberDTO, String email){
        log.info("--------------------------------------------------------");
        log.info("remove ibno : " + ibno);
        imageBoardService.remove(ibno);

        rttr.addFlashAttribute("msg"," 이미지게시글 "+ ibno +" 번이 삭제되었습니다.");
        return "redirect:/image/list";
    }
}
