package me.whiteship.inflearnthejavatest;

import me.whiteship.inflearnthejavatest.domain.Study;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        // 모든 테스트 메소드에 적용됨 -> 언더스코어가 공백으로 표시됨
class StudyTest {

    // junit5 부터 public 이 안붙어도됨
    @Test
    @DisplayName("스터디 만들기")
    @EnabledOnOs(OS.WINDOWS)    // windows os 에서만 실행 하겠다.
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10})    // os 뿐만아니라 특정 자바 버전에서만 테스트가 진행 되게 설정 할 수도 있다.
    void create_new_study() throws Exception {
        String test_env = System.getenv("TEST_ENV");
        System.out.println("test_env : " + test_env);
        Assumptions.assumeTrue("LOCAL".equalsIgnoreCase(test_env)); // 환경변수값이 LOCAL일때만 아래 테스트를 진행하라.

        Study actual = new Study(10);
        assertThat(actual.getLimit()).isGreaterThan(0);
        
    }

    @Test
    public void create_new_study_again() throws Exception {
        //given
        System.out.println("create1");
    }

    // 모든 테스트들이 실행하기 전에 딱 한번만 실행 됨
    // 이 메소드는 꼭 static을 사용해야하고 return type이 없어야함
    @BeforeAll
    static void beforeAll() {
        System.out.println("before All");
    }

    // 모든 테스트들이 실행한 후에 딱 한번만 실행 됨
    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    // 테스트들이 시작하기 전에 매번 이걸 한번씩 호출됨
    // static이 아니여도됨
    @BeforeEach
    void beforeEach() {
        System.out.println("before Each");
    }

    // 테스트들이 시작하기 후에 매번 이걸 한번씩 호출됨
    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

}