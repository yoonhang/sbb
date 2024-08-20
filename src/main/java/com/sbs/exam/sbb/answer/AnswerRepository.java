package com.sbs.exam.sbb.answer;

import com.sbs.exam.sbb.RepositoryUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer, Long>, RepositoryUtil {
  @Transactional
  @Modifying
  @Query(value = "ALTER TABLE answer AUTO_INCREMENT = 1", nativeQuery = true)
  void truncate(); // 이거 지우면 안됨, truncateTable 하면 자동으로 이 코드가 실행!!
}
