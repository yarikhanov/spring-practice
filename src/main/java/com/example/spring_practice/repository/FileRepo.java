package com.example.spring_practice.repository;

import com.example.spring_practice.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface FileRepo extends JpaRepository<File, Long> {

    @Query("update File f set f.status = 'DELETED' where f.id = :id")
    @Modifying
    void softDeleteById(@Param("id") Long id);
}
