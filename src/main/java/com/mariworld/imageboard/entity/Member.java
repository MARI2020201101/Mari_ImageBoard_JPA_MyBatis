package com.mariworld.imageboard.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member extends BaseEntity{

    @Id
    private String email;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    public void changeMemberRole(MemberRole memberRole){
        this.memberRole = memberRole;
    }
}
