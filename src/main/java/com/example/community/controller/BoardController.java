package com.example.community.controller;

import com.example.community.dto.BoardForm;
import com.example.community.entity.Board;
import com.example.community.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;


@Slf4j // 롬복 어노테이션
@Controller // 컨트롤러 선언
public class BoardController {
    @Autowired
    private BoardRepository boardRepository; // 스프링이 자동으로 생성해서 주입 DI

    // 글 작성 화면
    @GetMapping("/boards/new")
    public String boardForm(){
        return "boards/new";
    }

    // 글 생성 처리
    @PostMapping("/boards/create")
    public String createBoard(BoardForm form){
        // 1. 클라이언트에서 넘어온 DTO를 엔티티로 저장
        Board board = form.toEntity();
        // 2. JPA 리파지터리를 통해 엔티티를 DB에 저장
        Board saved = boardRepository.save(board);
        log.info("saved = " + saved);

        // 상세 페이지 리다이렉트
        return "redirect:/boards/" + saved.getId();
    }

    // 전체 조회
    @GetMapping("/boards")
    public String boardAll(Model model){
        // 모든 데이터 가져오기
        ArrayList<Board> boardEntityList = (ArrayList<Board>) boardRepository.findAll();
        // 모델에 등록하기
        model.addAttribute("boardList", boardEntityList);
        return "boards/all";
    }

    // 게시글 상세 조회
    @GetMapping("/boards/{id}")
    public String boardSelect(@PathVariable Long id, Model model){
        // URL 경로에 있는 id 값을 활용해 게시글 조회
        Board boardEntity = boardRepository.findById(id).orElse(null);
        model.addAttribute("boardEntity", boardEntity);
        return "boards/detail";
    }

    // 게시글 수정
    @GetMapping("/boards/{id}/edit")
    public String boardEdit(@PathVariable Long id, Model model) {
        // 수정할 게시글 조회
        Board boardEntity = boardRepository.findById(id).orElse(null);
        model.addAttribute("board", boardEntity);
        return "boards/edit";
    }

    // 게시글 수정요청
    @PostMapping("/boards/update")
    public String boardUpdate(BoardForm form) {
        Board boardEntity = form.toEntity();
        Board target = boardRepository.findById(boardEntity.getId()).orElse(null);

        if (target != null ){
            boardRepository.save(boardEntity);
            log.info(String.valueOf(target));
        }
        return "redirect:/boards/" + boardEntity.getId();
    }

    // 게시글 삭제
    @GetMapping("/boards/{id}/delete")
    public String boardDelete(@PathVariable long id, RedirectAttributes flashmsg){
        Board board = boardRepository.findById(id).orElse(null);
        if (board != null){
            boardRepository.delete(board);
            flashmsg.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }
        return "redirect:/boards";
    }
}

