package com.sbs.exam.sbb;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// @Controller : 스프링부트한테 해당 클래스는 컨트롤러 역할이라고 알려준다.
@Controller
public class TestController {
  private int increaseNo;
  private List<Article> articles;

  public TestController() {
    increaseNo = -1;
    articles = new ArrayList<>() {{
      add(new Article("제목1", "내용1"));
      add(new Article("제목2", "내용2"));
    }};
  }

  @RequestMapping("/test")
  @ResponseBody
  public String showTest() {
    return """
        <h1>안녕하세요.</h1>
        <input type="text" placeholder="입력해주세요."/>
        """;
  }

  @GetMapping("/page1")
  @ResponseBody
  public String showGet() {
    return """
        <form method="POST" action="/page2">
          <input type="number" name="age" placeholder="나이 입력" />
          <input type="submit" value="page2로 POST 방식으로 이동" />
        </form>
        """;
  }

  @GetMapping("/page2")
  @ResponseBody
  public String showPage2Get(@RequestParam(defaultValue = "0") int age) {
    return """
        <h1>입력된 나이 : %d</h1>
        <h1>안녕하세요. GET 방식으로 오신걸 환영합니다.</h1>        
        """.formatted(age);
  }

  @PostMapping("/page2")
  @ResponseBody
  public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
    return """
        <h1>입력된 나이 : %d</h1>
        <h1>안녕하세요. POST 방식으로 오신걸 환영합니다.</h1>        
        """.formatted(age);
  }

  @GetMapping("/plus")
  @ResponseBody
  public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
    return a + b;
  }

  @GetMapping("/plus2")
  @ResponseBody
  public void showPlus2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    int a = Integer.parseInt(req.getParameter("a"));
    int b = Integer.parseInt(req.getParameter("b"));

    resp.getWriter().append(a + b + "");
  }

  @GetMapping("/minus")
  @ResponseBody
  public int showMinus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
    return a - b;
  }

  @GetMapping("/increase")
  @ResponseBody
  public int showIncrease() {
    increaseNo++;
    return increaseNo;
  }

  @GetMapping("/gugudan")
  @ResponseBody
  public String showGugudan(Integer dan, Integer limit) {
    if (dan == null) {
      dan = 9;
    }

    if (limit == null) {
      limit = 9;
    }

    String gugudanFormat = "";

    for (int i = 1; i <= limit; i++) {
      gugudanFormat += "%d * %d = %d<br>".formatted(dan, i, dan * i);
    }

    return gugudanFormat;
  }

  @GetMapping("/gugudan2")
  @ResponseBody
  public String showGugudan2(Integer dan, Integer limit) {
    if (dan == null) {
      dan = 9;
    }

    if (limit == null) {
      limit = 9;
    }

    // final 수식어가 붙으면 해당 변수는 상수처리 된다.
    final Integer finalDan = dan;
    return IntStream.rangeClosed(1, limit)
        .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
        .collect(Collectors.joining("<br>"));
  }

  @GetMapping("/mbti/{name}")
  @ResponseBody
  public String showMbti(@PathVariable String name) {
    return switch (name) {
      case "홍길동" -> {
        char j = 'J';
        yield "INF" + j;
      }
      case "홍길순" -> "ENFP";
      case "임꺽정", "신짱구" -> "ESFJ";
      default -> "모름";
    };
  }

  @GetMapping("/saveSession/{name}/{value}")
  @ResponseBody
  public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
    HttpSession session = req.getSession();

    // req => 쿠키 => JSESSIONID => 세션을 얻을 수 있다.
    session.setAttribute(name, value);

    return "세션변수의 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value);
  }

  @GetMapping("/getSession/{name}")
  @ResponseBody
  public String getSession(@PathVariable String name, HttpSession session) {
    String value = (String) session.getAttribute(name);

    return "세션변수의 %s의 값이 %s입니다.".formatted(name, value);
  }

  @GetMapping("/home/returnBoolean")
  @ResponseBody
  public boolean showReturnBoolean() {
    return false;
  }

  @GetMapping("/home/returnDouble")
  @ResponseBody
  public double showReturnDouble() {
    return Math.random();
  }

  @GetMapping("/home/returnIntArr")
  @ResponseBody
  public int[] showReturnIntArr() {
    int[] arr = new int[]{10, 20, 30};
    return arr;
  }

  @GetMapping("/home/returnStringList")
  @ResponseBody
  public List<String> showReturnStringList() {
    List<String> list = new ArrayList<>() {{
      add("안녕");
      add("반가워");
      add("어서와");
    }};

    /*
    // 위와 같은 코드
    List<String> list2 = new ArrayList<>();
    list2.add("안녕");
    list2.add("반가워");
    list2.add("어서와");
     */

    return list;
  }

  @GetMapping("/home/returnMap")
  @ResponseBody
  public Map<String, Object> showReturnMap() {
    Map<String, Object> map = new LinkedHashMap<>() {{
      put("id", 1);
      put("age", 5);
      put("name", "푸바오");
      put("related", new ArrayList<>() {{
        add(2);
        add(3);
        add(4);
      }});
    }};

    return map;
  }

  @GetMapping("/home/returnAnimal")
  @ResponseBody
  public Animal showReturnAnimal() {
    Animal animal = new Animal(1, 3, "포비", new ArrayList<>() {{
      add(2);
      add(3);
      add(4);
    }});

    System.out.println(animal);

    return animal;
  }

  @GetMapping("/home/returnAnimal2")
  @ResponseBody
  public AnimalV2 showReturnAnimal2() {
    AnimalV2 animal = new AnimalV2(1, 3, "포비", new ArrayList<>() {{
      add(2);
      add(3);
      add(4);
    }});

    animal.setName(animal.getName() + "V3");

    return animal;
  }

  @GetMapping("/home/returnAnimalMapList")
  @ResponseBody
  public List<Map<String, Object>> showReturnAnimalMapList() {
    Map<String, Object> animalMap1 = new LinkedHashMap<>() {{
      put("id", 1);
      put("age", 5);
      put("name", "푸바오");
      put("related", new ArrayList<>() {{
        add(2);
        add(3);
        add(4);
      }});
    }};

    Map<String, Object> animalMap2 = new LinkedHashMap<>() {{
      put("id", 2);
      put("age", 8);
      put("name", "포비");
      put("related", new ArrayList<>() {{
        add(5);
        add(6);
        add(7);
      }});
    }};

    List<Map<String, Object>> list = new ArrayList<>();

    list.add(animalMap1);
    list.add(animalMap2);

    return list;
  }

  @GetMapping("/home/returnAnimalList")
  @ResponseBody
  public List<AnimalV2> showReturnAnimalList() {
    AnimalV2 animal1 = new AnimalV2(1, 3, "포비", new ArrayList<>() {{
      add(2);
      add(3);
      add(4);
    }});

    AnimalV2 animal2 = new AnimalV2(2, 6, "푸바오", new ArrayList<>() {{
      add(5);
      add(6);
      add(7);
    }});

    List<AnimalV2> list = new ArrayList<>();
    list.add(animal1);
    list.add(animal2);

    return list;
  }

  @GetMapping("/addArticle")
  @ResponseBody
  public String addArticle(String title, String body) {
    Article article = new Article(title, body);

    System.out.println(article);

    articles.add(article);

    return "%d번 게시물이 추가되었습니다.".formatted(article.getId());
  }

  @GetMapping("/article/list")
  @ResponseBody
  public List<Article> getArticles() {
    return articles;
  }

  @GetMapping("/article/detail/{id}")
  @ResponseBody
  public Object getArticle(@PathVariable int id) {
    Article article = articles.stream()
        .filter(a -> a.getId() == id) // 게시물 id와 내가 입력한 id가 일치한지 확인
        .findFirst()
        .orElse(null); // 입력한 번호의 게시물이 없으면 null 반환

    if (article == null) {
      return "%d번 게시물이 존재하지 않습니다.".formatted(id);
    }

    return article;
  }

  @GetMapping("/article/modify/{id}")
  @ResponseBody
  public String modifyArticle(@PathVariable int id, String title, String body) {
    Article article = articles.stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElse(null);

    if (article == null) {
      return "%d번 게시물이 존재하지 않습니다.".formatted(id);
    }

    if(title == null) {
      return "제목을 입력해주세요.";
    }

    if(body == null) {
      return "내용을 입력해주세요.";
    }

    article.setTitle(title);
    article.setBody(body);

    return "%d번 게시물을 수정하였습니다.".formatted(article.getId());
  }

  @GetMapping("/article/delete/{id}")
  @ResponseBody
  public String deleteArticle(@PathVariable int id) {
    Article article = articles.stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElse(null);

    if (article == null) {
      return "%d번 게시물이 존재하지 않습니다.".formatted(id);
    }

    articles.remove(article);

    return "%d번 게시물을 삭제하였습니다.".formatted(id);
  }

  @GetMapping("/addPersonOldWay")
  @ResponseBody
  public Person addPersonOldWay(int id, int age, String name) {
    Person p = new Person(id, age, name);

    return p;
  }

  @GetMapping("/addPerson/{id}")
  @ResponseBody
  public Person addPerson(Person p) {
    return p;
  }
}

class Animal {
  private final int id;
  private final int age;
  private String name;
  private final List<Integer> related;

  public int getId() {
    return id;
  }

  public int getAge() {
    return age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Integer> getRelated() {
    return related;
  }

  public Animal(int id, int age, String name, List<Integer> related) {
    this.id = id;
    this.age = age;
    this.name = name;
    this.related = related;
  }

  @Override
  public String toString() {
    return "Animal{" +
        "id=" + id +
        ", age=" + age +
        ", name='" + name + '\'' +
        ", related=" + related +
        '}';
  }
}

@AllArgsConstructor
@Getter
class AnimalV2 {
  private final int id;
  private final int age;

  @Setter
  private String name;
  private final List<Integer> related;
}

@AllArgsConstructor
@Data
class Article {
  private static int lastId;
  private final int id;
  private String title;
  private String body;

  static {
    lastId = 0;
  }

  public Article(String title, String body) {
    this(++lastId, title, body);
  }

}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class Person {
  private int id;
  private int age;
  private String name;

  public Person(int age, String name) {
    this.age = age;
    this.name = name;
  }
}
