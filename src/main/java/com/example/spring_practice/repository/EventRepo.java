package com.example.spring_practice.repository;

import com.example.spring_practice.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepo extends JpaRepository<Event, Long> {

    @Query("update Event e set e.status = 'DELETED' where e.id = :id")
    @Modifying
    void softDeleteById(@Param("id") Long id);

    @Query("update Event e set e.status = 'DELETED' where e.file.id = :id")
    @Modifying
    void softDeleteByFileId(@Param("id") Long id);
}
