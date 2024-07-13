package com.java.spring.batch.statement.batch;

import com.java.spring.batch.statement.entity.CustomerAccount;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;

public class CustomerAccountProcessor implements ItemProcessor<CustomerAccount, CustomerAccount> {

  @Override
  public CustomerAccount process(CustomerAccount customer) throws Exception {

    return StringUtils.hasText(customer.getEmail()) ? customer : null;
  }
}
