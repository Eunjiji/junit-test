package me.whiteship.inflearnthejavatest.study;

import me.whiteship.inflearnthejavatest.domain.Study;
import me.whiteship.inflearnthejavatest.domain.Member;
import me.whiteship.inflearnthejavatest.member.MemberService;

import java.util.Optional;


public class StudyService {

    private final MemberService memberService;
    private final StudyRepository repository;

    public StudyService(MemberService memberService, StudyRepository repository){
        // assert 는 null 이면 exception이 떨어짐
        assert memberService != null;
        assert repository != null;
        this.memberService = memberService;
        this.repository = repository;
    }

    public Study createNewStudy(Long memberId, Study study){
        Optional<Member> member = memberService.findById(memberId);
        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("member don't find")));

        Study newStudy = repository.save(study);

        // memberService 한테 새로운 스터디가 나왔다고 알려주는 메서드
        memberService.notify(newStudy);
        memberService.notify(member.get());
        return newStudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = repository.save(study);
        memberService.notify(study);
        return openedStudy;
    }
}
