package com.mariworld.imageboard.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "member")
public class ImageBoard extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ibno;

    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
