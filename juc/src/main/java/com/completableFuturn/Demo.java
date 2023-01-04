package com.completableFuturn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author dubin
 * @create 2023-01-05 10:50
 */
public class Demo {
    public static void main(String[] args) {
        final Student student = new Student(1, "23", "cs");
        student.setId(12).setStudentName("li4").setMajor("english");

    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true) //启动链式编程
class Student {
    private Integer id;
    private String studentName;
    private String major;
}