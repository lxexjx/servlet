package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter     //롬복을 사용하면 자동으로 getter setter가 들어감
public class HelloData {
    private String username;
    private int age;
}
