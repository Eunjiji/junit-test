package me.whiteship.inflearnthejavatest.service;

import me.whiteship.inflearnthejavatest.domain.Study;
import me.whiteship.inflearnthejavatest.domain.Member;
import me.whiteship.inflearnthejavatest.member.MemberService;
import me.whiteship.inflearnthejavatest.study.StudyRepository;
import me.whiteship.inflearnthejavatest.study.StudyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 이 어노테이션이 있어야 제대로 Mock이 동작한다.
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyService studyService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService(){

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("eunjia190@gmail.com");

        // 1 이라는 id 가 호출이 되야만 위에서 셋팅해 놓은 member 값을 return 받을수 있음
        when(memberService.findById(any())).thenReturn(Optional.of(member));

        assertEquals("eunjia190@gmail.com", memberService.findById(1L).get().getEmail());
        assertEquals("eunjia190@gmail.com", memberService.findById(2L).get().getEmail());

        // memberService 의 validate에 1을 넣어 호출 했을때 IllegalArgumentException을 던진다.
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        // IllegalArgumentException이 나오는지 테스트
        assertThrows(IllegalArgumentException.class, () ->{
           memberService.validate(1L);
        });

        // 2를 호출하면 exception이 발생 안함
        memberService.validate(2L);

    }


    @Test
    public void createNewStudy(){

        // 생성자를 이용한 Mock 주입
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("eunji@gmail.com");

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))    // findById를 첫번째로 호출하면 Optional 값으로 member return.
                .thenThrow(new RuntimeException())  // findById를 두번째로 호출하면 RuntimeException을 터뜨리다.
                .thenReturn(Optional.empty());      // findById를 세번째를 호출하면 빈 Optional이 reurn 된다.

       Optional<Member> byId = memberService.findById(1L);  // 첫번째 호출
       assertEquals("eunji@gmail.com", byId.get().getEmail());

       assertThrows(RuntimeException.class, () ->{
           memberService.findById(2L);                         // 두번째 호출
       });

       assertEquals(Optional.empty(), memberService.findById(3L));  // 세번째 호출
    }


    @Test
    public void pratice(){
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Study study = new Study(10, "test");

        Member member = new Member();
        member.setId(1L);
        member.setEmail("eunjia190@gmail.com");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);


        studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());
    }


}