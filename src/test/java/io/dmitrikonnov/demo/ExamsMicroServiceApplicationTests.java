package io.dmitrikonnov.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class ExamsMicroServiceApplicationTests {

    Calculator underTest = new Calculator();

    @Test
    void itShouldPerformAddiation() {
        //given
        int number1 = 30;
        int number2 = 20;
        //when
        var result = underTest.add(number1, number2);
        //then
        int expected = 20+30;
        assertThat(result).isEqualTo(expected);



    }

    class Calculator {
        int add(int x, int y){
          return x + y;
        }
    }
}
