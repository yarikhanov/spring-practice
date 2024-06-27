package com.example.spring_practice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("files")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class File {

    @Id
    @Column("id")
    private Long id;

    @Column("location")
    private String location;

    @Column("status")
    private Status status;
}
