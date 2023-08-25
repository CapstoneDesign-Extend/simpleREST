package com.mabeopsa.simpleREST.service;


import com.mabeopsa.simpleREST.domain.Board;
import com.mabeopsa.simpleREST.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 조회 시 readOnly = true 해당 속성을 주면 최적화됨
//@AllArgsConstructor // 현재 클래스가 가지고 있는 필드를 가지고 생성자를 만들어줌
@RequiredArgsConstructor // 현재 클래스가 가지고 있는 필드 중 private final 필드만을 가지고 생성자를 만들어줌
public class BoardService {

    private final BoardRepository boardRepository;

    public void createBoard(Board board) {
        // 1. 게시글 생성 -> 각각 알맞은 게시판 속성 저장
        boardRepository.save(board);
    }

    public Board updateBoard(Long boardId, Board board) {
        // 2. 게시글 수정 -> 해당 권한을 가진 member만 수정 가능
        Board updatedBoard = boardRepository.findOne(boardId);
        if (updatedBoard != null) {
            // 게시글 수정 로직 구현
            updatedBoard.setTitle(board.getTitle());
            updatedBoard.setContent(board.getContent());
            updatedBoard.setFinalDate(board.getFinalDate());
        }
        return updatedBoard;
    }

    public void deleteBoard(Long boardId) {
        // 3. 게시글 삭제 -> 해당 member id로 판별
        Board board = boardRepository.findOne(boardId);
        if (board != null) {
            boardRepository.delete(board);
        }
    }

    public Board findBoardById(Long boardId) {
        // 게시글 ID로 게시글 조회
        return boardRepository.findOne(boardId);
    }

    @Transactional(readOnly = true)
    public List<Board> findAllBoards() {
        // 모든 게시글 조회
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Board> searchBoardsByTitle(String title) {
        // 4. 게시글 검색 -> 연관된 제목으로 검색
        return boardRepository.findByTitle(title);
    }

}
