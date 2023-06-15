package io.dmitrikonnov.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode (of ={"id"})
public class ExamComment {

    @Id
    @GeneratedValue
    private Integer commentId;
    private String text;

    @OneToOne
    private Exam exam;
}
