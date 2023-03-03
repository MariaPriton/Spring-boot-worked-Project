package com.example.TestCaseCheck;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
//@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="userdetail")
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy= GenerationType.AUTO, generator="id_seq")
    @SequenceGenerator(name="id_seq", sequenceName="userdetail_seq",allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "address")
    private String address;


}
