package io.dmitrikonnov.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
class ExamRepoTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    @Autowired
    ExamRepo underTest;

    @Test
    void itShouldCheckIfExistsEmailPersonInChargeForExam(){
        //given
        String email ="email.test1@gmail.com";
        Exam exam1 = new Exam("exam1","","","A1",null,"","");
        exam1.setEmailPersonInChargeForExam(email);
        underTest.save(exam1);
        //when
        boolean exists = underTest.existsByEmailPersonInChargeForExam(email);
        //then
        assertThat(exists).isTrue();

    }
}