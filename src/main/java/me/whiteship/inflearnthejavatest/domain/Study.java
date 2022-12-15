package me.whiteship.inflearnthejavatest.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import me.whiteship.inflearnthejavatest.study.StudyStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Study {

    @Id @GeneratedValue
    private Long id;

    private StudyStatus status = StudyStatus.DRAFT;

    private int limit;

    private String name;

    private LocalDateTime openedDateTime;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member owner;

    public Study(int limit) {
        if(limit < 0){
            throw new IllegalArgumentException("limit는 0보다 커야한다.");
        }
        this.limit = limit;
    }

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }


    public void setOwner(Member member) {
        this.owner = member;
    }

    public void open() {
        this.status = StudyStatus.OPENDED;
        this.openedDateTime = LocalDateTime.now();
    }
}
