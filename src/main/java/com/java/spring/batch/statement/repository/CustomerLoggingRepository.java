package com.java.spring.batch.statement.repository;

import com.java.spring.batch.statement.entity.CustomerLogging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLoggingRepository extends JpaRepository<CustomerLogging, Integer> {}
