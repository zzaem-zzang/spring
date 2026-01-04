package com.example.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity // 이 클래스가 JPA 엔티티
@Getter
@NoArgsConstructor
@ToString
public class Board {


    @Id // 대표값
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 생성 IDENTITY->는 자동증가
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime createdAt;



    public Board(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // DB에 insert되기 전에 초기화 -> 자동 실행
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
