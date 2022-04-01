package io.dmitrikonnov.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ExamServiceTest {

    @Mock
    private ExamRepo examRepoMock;
    private ExamService underTest;


    @BeforeEach
    void setUp() {

        underTest = new ExamServiceImpl(examRepoMock);
    }

    @AfterEach
    void tearDown()  {

    }

    @Test
    void canGetAllExams() {
        //when
        underTest.getAll();
        //then
        verify(examRepoMock).findAll();
    }

    @Test
    void canAddExam() {
        //given
        String email ="email.test1@gmail.com";
        Exam exam1 = new Exam("exam1","","","A1",null,"","");
        exam1.setEmailPersonInChargeForExam(email);
        //when
        underTest.addExam(exam1);
        //then
        ArgumentCaptor<Exam> examArgumentCaptor = ArgumentCaptor.forClass(Exam.class);
        verify(examRepoMock).save(examArgumentCaptor.capture());
        Exam capturedExam = examArgumentCaptor.getValue();
        assertThat(capturedExam).isEqualTo(exam1);

    }

    @Test

    void canDeleteExamById() {
        //given
        Long id = anyLong();
        //when
        underTest.deleteExamById(id);
        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(examRepoMock).deleteById(longArgumentCaptor.capture());
        Long capturedId = longArgumentCaptor.getValue();
        assertThat(id).isEqualTo(capturedId);

    }

    @Test
    void shouldThrowExceptionOnDeleteIfIdDoesNotExist(){
        //given
        Long notExistingId = 1L;
        //when
        doThrow(NoSuchElementException.class).when(examRepoMock).deleteById(notExistingId);
        //then
        assertThatThrownBy(()-> {underTest.deleteExamById(notExistingId);})
                .isInstanceOf(NoSuchElementException.class);
        verifyNoMoreInteractions(examRepoMock);


    }


}