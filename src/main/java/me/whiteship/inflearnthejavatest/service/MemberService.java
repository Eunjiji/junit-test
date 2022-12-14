package me.whiteship.inflearnthejavatest.service;

import me.whiteship.inflearnthejavatest.domain.Member;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);
}
