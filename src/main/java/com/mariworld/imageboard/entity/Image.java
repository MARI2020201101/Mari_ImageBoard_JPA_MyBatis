package com.mariworld.imageboard.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "imageBoard")
public class Image extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;
    private String uuid;
    private String path;
    private String imgName;

    @ManyToOne(fetch = FetchType.LAZY)
    private ImageBoard imageBoard;
}
