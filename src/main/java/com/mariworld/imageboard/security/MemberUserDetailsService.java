package com.mariworld.imageboard.security;

import com.mariworld.imageboard.entity.Member;
import com.mariworld.imageboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> result = memberRepository.findById(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("User Email : "+username +" Not Exists");
        }
        Member member = result.get();
        MemberDTO memberDTO = new MemberDTO(username
                , member.getPassword()
                , member.getName()
                , List.of(new SimpleGrantedAuthority("ROLE_"+member.getMemberRole().name())));
        return memberDTO;
    }
}
