package io.dmitrikonnov.demo;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table (name = "EXAM_TYPE", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID", name = "exam_type_id_unique_constraint"),
        @UniqueConstraint(columnNames = "TYPE_NAME", name = "exam_type_name_unique_constraint")})
@Data
@NoArgsConstructor
public class ExamType {

    @Id
    @SequenceGenerator(name = "examtype_sequence",
            sequenceName = "examtype_sequence",
            allocationSize = 1,
            initialValue = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "examtype_sequence")

    @Column (nullable = false, name = "ID")
    Long id;

    @Column(nullable = false, name = "TYPE_NAME")
    @NotBlank
    String typeName;
    String description;


}
