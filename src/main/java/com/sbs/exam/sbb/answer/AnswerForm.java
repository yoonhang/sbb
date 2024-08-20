package com.sbs.exam.sbb.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AnswerForm {
  @NotEmpty(message = "내용은 필수항목입니다.")
  private String content;
}
