package com.sbs.exam.sbb.answer;

import com.sbs.exam.sbb.qustion.Question;
import com.sbs.exam.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDateTime createDate;
  private LocalDateTime modifyDate;

  @ManyToOne
  private Question question;

  @ManyToOne
  private SiteUser author;

  @ManyToMany
  Set<SiteUser> voter;
}
