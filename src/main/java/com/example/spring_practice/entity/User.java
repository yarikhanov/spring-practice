package com.example.spring_practice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class User {


    @Id
    @Column("id")
    private Long id;

    @Column("username")
    private String username;

    @Column("password")
    private String password;

    @Column("user_role")
    private UserRole userRole;

    @Column("status")
    private Status status;

    @Transient
    private List<Event> events;

    @ToString.Include(name = "password")
    private String maskPass(){
        return "******";
    }
}
