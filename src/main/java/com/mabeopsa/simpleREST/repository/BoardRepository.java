package com.mabeopsa.simpleREST.repository;

import com.mabeopsa.simpleREST.model.Board;
import com.mabeopsa.simpleREST.model.BoardKind;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 자동으로 스프링 bean으로 사용됨
@RequiredArgsConstructor
public class BoardRepository {
    // 1. 게시글 생성 -> 각각 알맞은 게시판 속성 저장
    // 2. 게시글 수정 -> 해당 권한을 가진 member만 수정 가능
    // 3. 게시글 삭제 -> 해당 member id로 판별
    // 4. 게시글 검색 -> 연관된 제목으로 검색

    @PersistenceContext // EntityManager를 주입받기 위해 사용
    private final EntityManager em;

    public void save(Board board){ // 게시글 저장
        em.persist(board);
    }

    // 해당 id를 가진 게시글 반환
    public Board findOne(Long id){
        return em.find(Board.class, id);
    }
    // 모든 게시글 리스트 반환
    public List<Board> findAll(){
        // JPA는 객체를 대상으로 쿼리문을 작성 => 메소드 인자 중 두 번째 인자가 타입을 나타냄
        List<Board> result = em.createQuery("select b from Board b", Board.class)
                .getResultList();
        return result;
    }
    // 해당 boardKind 의 게시글 리스트 반환
    public List<Board> findByBoardKind(BoardKind boardKind) {
        return em.createQuery("select b from Board b where b.boardKind = :boardKind", Board.class)
                .setParameter("boardKind", boardKind)
                .getResultList();
    }
    // 제목으로 검색해 게시글 리스트 반환
    public List<Board> findByTitle(String title) {
        return em.createQuery("select b from Board b where b.title like :title", Board.class)
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }
    // 제목이나 본문에 해당 키워드를 포함하는 게시글들을 반환
    public List<Board> findByKeyword(String keyword) {
        return em.createQuery("select b from Board b where b.title like :keyword or b.content like :keyword", Board.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }
    public void delete(Board board) {
        em.remove(board);
    }
    public void deleteById(Long id) { // 해당 게시글 id로 삭제함
        Board board = em.find(Board.class, id);
        if (board != null) {
            em.remove(board);
        }
    }


}
