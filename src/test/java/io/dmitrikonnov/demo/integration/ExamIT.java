package io.dmitrikonnov.demo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.dmitrikonnov.demo.Exam;
import io.dmitrikonnov.demo.ExamRepo;
import io.dmitrikonnov.demo.ExamTypeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
public class ExamIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ExamRepo examRepo;


    private final String API_V1_EXAM_ENDPOINT = "/api/v1/exams";
    private final Faker faker = new Faker();

    @Test
    @Rollback(value = false)
    @Transactional
    void canAddNewExam() throws Exception {
        //given
        String name = faker.name().firstName() + "." + faker.name().lastName();
        String email = String.format("%s@gmail.com", name.toLowerCase(Locale.ROOT));
        Exam exam1 = new Exam("exam1","","","A1", Collections.emptySet(),"","");
        exam1.setEmailPersonInChargeForExam(email);
        String jsonContent = objectMapper.writeValueAsString(exam1);
        //then
        ResultActions resultActions = mockMvc.perform(post(API_V1_EXAM_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent));
        //then
        resultActions.andExpect(status().isCreated());
        List<Exam> exams = examRepo.findAll();
        assertThat(exams).usingElementComparatorIgnoringFields("id").contains(exam1);

    }

    //TODO: i) replace "usingElement..." by smth appropriate;
    //TODO: iii) write all integration tests
}
