package com.example.spring_practice.reactive_repo;

import com.example.spring_practice.entity.File;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends R2dbcRepository<File, Long> {

}
