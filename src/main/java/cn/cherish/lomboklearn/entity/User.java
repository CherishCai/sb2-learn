package cn.cherish.lomboklearn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cherish
 * @version 1.0
 * @date 2018/6/13 12:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String firstname;
    private String lastname;
    private int age;

}
