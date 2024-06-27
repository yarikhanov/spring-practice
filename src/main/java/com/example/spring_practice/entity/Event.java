package com.example.spring_practice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Event {

    @Id
    @Column("id")
    private Long id;

    @Column( "user_id")
    private Long userId;

    @Column("file_id")
    private Long fileId;

    @Column( "status")
    private Status status;

    @Transient
    private User user;

    @Transient
    private File file;
}
