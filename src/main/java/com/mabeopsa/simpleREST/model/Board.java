package com.mabeopsa.simpleREST.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter @Setter
@Table(name = "border")
public class Board { // 게시판 클래스
    @Id
    @GeneratedValue // 자동 생성 => 시퀀스
    @Column(name = "border_id")
    private Long id;
    private String title; // 제목
    private String content; // 본문
    @ManyToOne(fetch=FetchType.LAZY, cascade = ALL) // fetch=FetchType.LAZY : 지연 로딩으로 실시간 업로딩 되는 것을 막음
    @JoinColumn(name = "memberId") // 외래키 => 조인할 속성 이름
    private Member member; // 해당 멤버의 학번을 사용할 거임
    @Column(name = "view_count")
    private int viewcnt; // 조회수
    private LocalDateTime finalDate; // 최종 등록된 날짜
    @Enumerated(EnumType.STRING) // DB에 저장할때, enum 각각 요소의 순서(상수)가 아닌, 문자열로 저장
    private BoardKind boardKind; // 게시판 종류
    @OneToMany(mappedBy = "board", cascade = ALL, orphanRemoval = true) // mappedBy : 연관관계 주인이 누구인지 상태 테이블 속성이름으로 명시해줌
    //== 게시글을 삭제하면 달려있는 댓글 모두 삭제 ==//
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<File> files = new ArrayList<>();


    //== 생성 메소드 --//
    public static Board createBoard(BoardKind boardKind){ // 어떤 게시판의 게시글인지 알기 위해 사용
        Board board = new Board();
        board.setBoardKind(boardKind);

        return board;
    }

}

