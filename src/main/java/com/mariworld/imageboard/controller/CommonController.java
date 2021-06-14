package com.mariworld.imageboard.controller;

import com.mariworld.imageboard.security.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CommonController {

    @GetMapping("")
    public String index()throws Exception{
        return "redirect:/board/list";
    }

    @GetMapping("/customLogin")
    public void customLoginForm(String error, String logout, Model model
            ,@AuthenticationPrincipal MemberDTO memberDTO)throws Exception{

        log.info("customLogin.........................");
        if(error!=null){
            model.addAttribute("msg","패스워드와 아이디를 확인해 주십시오");
        }
        if(logout!=null){
            model.addAttribute("msg","로그아웃 되었습니다.");
        }
        if(memberDTO!=null){
            model.addAttribute("memberDTO", memberDTO);
        }
    }
    @GetMapping("/accessError")
    public void accessError()throws Exception{
    }

    @GetMapping("/customLogout")
    public void customLogoutForm()throws Exception{
    }

}
