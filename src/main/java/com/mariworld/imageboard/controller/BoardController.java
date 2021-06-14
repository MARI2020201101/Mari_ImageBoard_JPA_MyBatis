package com.mariworld.imageboard.controller;

import com.mariworld.imageboard.dto.BoardDTO;
import com.mariworld.imageboard.dto.PageRequestDTO;
import com.mariworld.imageboard.security.MemberDTO;
import com.mariworld.imageboard.service.BoardService;
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

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String index(Model model, PageRequestDTO pageRequestDTO
            , @AuthenticationPrincipal MemberDTO memberDTO)throws Exception{

        log.info("---------------------------------------------------------------");
        if(memberDTO!=null){
            log.info(memberDTO.toString());
            List<String> authList
                    = memberDTO.getAuthorities()
                    .stream().map(a->a.toString()).collect(Collectors.toList());
            authList.stream().forEach(a->log.info(a));
        }
        log.info("---------------------------------------------------------------");

        model.addAttribute("list",boardService.getList(pageRequestDTO));
        return "/board/list";
    }
    //@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public String registerForm(PageRequestDTO pageRequestDTO
            , @AuthenticationPrincipal MemberDTO memberDTO, Model model)throws Exception{
        model.addAttribute("memberDTO" ,memberDTO);
        return "/board/register";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public String register(BoardDTO dto, PageRequestDTO pageRequestDTO
            , RedirectAttributes attr)throws Exception{
        log.info(String.valueOf(dto));
        log.info(String.valueOf(pageRequestDTO));
        Long bno = boardService.register(dto);
        log.info("bno : " + bno);
        attr.addFlashAttribute("msg"," 게시글 "+bno +" 번이 등록되었습니다.");
        //attr.addAttribute("page", pageRequestDTO.getPage());
        return "redirect:/board/list";
    }
    @GetMapping("/read")
    public void read(Long bno, PageRequestDTO pageRequestDTO
        , Model model)throws Exception{
        BoardDTO dto = boardService.read(bno);
        model.addAttribute("dto", dto);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public void modify(Long bno, PageRequestDTO pageRequestDTO
            , Model model)throws Exception{
        BoardDTO dto = boardService.read(bno);
        model.addAttribute("dto", dto);
    }

    @PreAuthorize("authentication.principal.username == #dto.email or hasRole('ROLE_ADMIN')")
    @PostMapping("/modify")
    public String modify( PageRequestDTO pageRequestDTO
            , RedirectAttributes rttr, BoardDTO dto
            , @AuthenticationPrincipal MemberDTO memberDTO)throws Exception{

        BoardDTO modDto = boardService.read(dto.getBno());
        log.info("------------------------------------------");
        log.info("memberDTO.getUsername()"+ memberDTO.getUsername());
        log.info("dto.getEmail()"+ dto.getEmail());
        log.info("------------------------------------------");

        if(!(memberDTO.getUsername().equals(modDto.getEmail()))){
            return "redirect:/accessError";
        }
        boardService.modify(dto);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("bno", dto.getBno());
        return "redirect:/board/read";
    }

    @PreAuthorize("authentication.principal.username == #dto.email or hasRole('ROLE_ADMIN')")
    @PostMapping("/remove")
    public String remove(BoardDTO dto, RedirectAttributes rttr)throws Exception{
        boardService.remove(dto.getBno());
        rttr.addFlashAttribute("msg"," 게시글 " + dto.getBno()+" 번이 삭제되었습니다.");
        return "redirect:/board/list";
    }
}
