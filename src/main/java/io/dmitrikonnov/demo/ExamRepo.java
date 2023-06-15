package io.dmitrikonnov.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExamRepo extends JpaRepository<Exam,Long> {

    Boolean existsByEmailPersonInChargeForExam(String email);
    List<Exam> findAllByOrderByIdAsc();
    List<Exam> findAllByOrderByLastModified();

    @Modifying
    @Transactional()
    @Query ("update Exam e set e.previewImageLink = :data where e.id = :id")
    void updateImageDataById(@Param("id")long id,
                             @Param("data") String imageData );

}
