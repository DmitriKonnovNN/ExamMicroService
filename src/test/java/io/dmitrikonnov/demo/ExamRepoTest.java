package io.dmitrikonnov.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
@ActiveProfiles("test")
class ExamRepoTest {

    @Autowired
    ExamRepo underTest;

    private final String email ="email.test1@gmail.com";
    private final String notExistingEmail = "notexistingemail.test1@gmail.com";

    @BeforeEach
    void setUp() {
        //given
        Exam exam1 = new Exam("exam1","","","A1",null,"","");
        exam1.setEmailPersonInChargeForExam(email);
        underTest.save(exam1);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }



    @Test
    void itShouldCheckIfEmailPersonInChargeForExamExists(){
        //given: see before each

        //when
        boolean exists = underTest.existsByEmailPersonInChargeForExam(email);
        //then
        assertThat(exists).isTrue();

    }

    @Test
    void itShouldFailWhenEmailPersonInChargeForExamDoesNotExist(){
        //given: see before each

        //when
        boolean exists = underTest.existsByEmailPersonInChargeForExam(notExistingEmail);
        //then
        assertThat(exists).isFalse();

    }
}