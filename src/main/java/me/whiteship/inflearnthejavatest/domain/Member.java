package me.whiteship.inflearnthejavatest.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String email;

    @OneToMany(mappedBy = "owner")
    private List<Study> studys = new ArrayList<>();
}
