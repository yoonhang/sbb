package com.sbs.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
  @RequestMapping("/sbb")
  // @ResponseBody
  // 아래 함수의 리턴값을 그대로 브라우저에 표시
  // 아래 함수의 리턴값을 문자열화 해서 브라우저 응답을 바디에 담는다.
  @ResponseBody
  public String showHome() {
    return "안녕하세요.";
  }

  @GetMapping("/")
  public String root() {
    return "redirect:/question/list";
  }
}

@GetMapping("/plus")
@ResponseBody
public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
  return a + b;
}

@GetMapping("/get")
@ResponseBody
public String showGet() {
  return """
          <h2>test</h2>
          <h1>test</h1>  
          """;
}

