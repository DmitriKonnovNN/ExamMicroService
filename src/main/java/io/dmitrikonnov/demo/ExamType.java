package io.dmitrikonnov.demo;


import lombok.*;

import javax.persistence.*;

@Entity
@Table (name = "EXAM_TYPE")
@Data
@NoArgsConstructor
public class ExamType {

    @Id
    @SequenceGenerator(name = "examtype_sequence",
            sequenceName = "examtype_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "examtype_sequence")
    Long id;
    String typeName;
    String description;


}
