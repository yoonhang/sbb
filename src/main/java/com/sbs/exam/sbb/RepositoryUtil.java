package com.sbs.exam.sbb;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RepositoryUtil {
  @Transactional
  @Modifying
  @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
  void disableForeignKeyChecks();

  @Transactional
  @Modifying
  @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
  void enableForeignKeyChecks();

  // default 메서드를 구현하면 인터페이스 내에 구상 메서드를 정의하는게 가능하다.
  default void truncateTable() {
    // disableForeignKeyChecks();
    truncate();
    // enableForeignKeyChecks();
  }

  void truncate();
}
