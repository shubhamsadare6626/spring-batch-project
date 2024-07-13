package com.java.spring.batch.statement.repository;

import com.java.spring.batch.statement.entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Integer> {}
