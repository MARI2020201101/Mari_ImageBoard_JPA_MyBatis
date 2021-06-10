package com.mariworld.imageboard.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberDTO extends User {

    private String email;
    private String password;
    private String name;
   // private MemberRole memberRole;

    public MemberDTO(String username
            , String password
            , String name
            , Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.name = name;
        //this.memberRole = MemberRole.valueOf(authorities.stream().findFirst().get().toString());
        //DTO는 실제 유저에게 노출되는 클래스이므로 담지않음
    }
}
