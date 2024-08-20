package com.sbs.exam.sbb;

import com.sbs.exam.sbb.answer.AnswerRepository;
import com.sbs.exam.sbb.qustion.QuestionRepository;
import com.sbs.exam.sbb.user.UserRepository;
import com.sbs.exam.sbb.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {
  @Autowired
  private UserService userService;

  @Autowired
  private AnswerRepository answerRepository;
  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void beforeEach() {
    clearData();
    createSampleData();
  }

  private void createSampleData() {
    createSampleData(userService);
  }

  public static void createSampleData(UserService userService) {
    userService.create("admin", "admin@test.com", "1234");
    userService.create("user1", "user1@test.com", "1234");
  }

  private void clearData() {
    clearData(userRepository, answerRepository, questionRepository);
  }

  // 삭제 순서 : 답변 -> 질문 -> 회원
  public static void clearData(UserRepository userRepository,
                        AnswerRepository answerRepository,
                        QuestionRepository questionRepository) {
    answerRepository.deleteAll();
    answerRepository.truncateTable();

    questionRepository.deleteAll();
    questionRepository.truncateTable();

    userRepository.deleteAll();
    userRepository.truncateTable();
  }

  @Test
  @DisplayName("회원가입이 가능하다.")
  public void t1() {
    userService.create("user2", "user2@email.com", "1234");
  }

}
