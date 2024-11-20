package com.example.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 책임: 비즈니스 로직 처리(트랙잭션 관리), DTO 생성
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void 게시글수정(int id, BoardRequest.UpdateDTO updateDTO) {
        // 1. 게시글 조회, 2. 게시글 수정 권한, 3. 게시글 수정, 4. 수정된 엔터티 응답
        boardRepository.update(id, updateDTO.getTitle(), updateDTO.getContent());
    }

    public BoardResponse.UpdateFormDTO 게시글수정화면보기(int id) {
        Board board = boardRepository.findById(id);
        return new BoardResponse.UpdateFormDTO(board);
    }

    @Transactional
    public void 게시글삭제(int id) {
        boardRepository.delete(id);
    } // commit or rollback

    @Transactional
    public void 게시글쓰기(BoardRequest.SaveDTO saveDTO) {
        boardRepository.save(saveDTO.toEntity());
    } // commit or rollback

    public BoardResponse.DetailDTO 게시글상세보기(int id) {
        Board board = boardRepository.findById(id);
        return new BoardResponse.DetailDTO(board);
    }

    public List<BoardResponse.DTO> 게시글목록보기() {
        return boardRepository.findAll().stream()
                .map(BoardResponse.DTO::new)
                .toList();
    }
}
