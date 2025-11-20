package com.example.demo.Employee.model;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
        private int id;
        private String name;
        private double salary;
        private Department department;
        private Gender gender;
        private boolean isPresent;

}


