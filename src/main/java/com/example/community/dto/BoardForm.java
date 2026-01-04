package com.example.community.dto;


import com.example.community.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardForm {
    private Long id;
    private String title;
    private String content;

    // DTO를 엔티티로 변환
    public Board toEntity(){
        return new Board(id, title, content);
    }
}
