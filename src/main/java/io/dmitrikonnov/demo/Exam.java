package io.dmitrikonnov.demo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "EXAM", indexes = @Index(columnList = "id", name = "exam_id_idx"))
public class Exam {

    @Id
    @SequenceGenerator(name = "exam_sequence",
            sequenceName = "exam_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "exam_sequence")
    Long id;
    @NotBlank
    String examName;
    String results;
    String examiners;
    String examLevel;
    String examSummary;
    @Email
    String emailPersonInChargeForExam;

    @ManyToMany (fetch =FetchType.LAZY ,cascade = CascadeType.MERGE)
    @JoinTable(name = "exam_type_jointable",
            joinColumns = {@JoinColumn (name = "fk_exam")},
            inverseJoinColumns = {@JoinColumn(name = "fk_exam_type")})
    Set<ExamType> examType;
    String subjectOfExam;
    String status;


    public Exam(String examName, String results, String examiners, String examLevel, Set<ExamType> examType, String subjectOfExam, String status) {
        this.examName = examName;
        this.results = results;
        this.examiners = examiners;
        this.examLevel = examLevel;
        this.examType = examType;
        this.subjectOfExam = subjectOfExam;
        this.status = status;
    }
}
