package com.java.spring.batch.statement.config;

import com.java.spring.batch.statement.entity.CustomerAccount;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class CSVFileConfig {

  @Bean
  public FlatFileItemReader<CustomerAccount> csvFileReader() {
    FlatFileItemReader<CustomerAccount> itemReader = new FlatFileItemReader<>();
    itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
    itemReader.setName("File Reader");
    itemReader.setLinesToSkip(1);
    itemReader.setLineMapper(lineMapper());
    return itemReader;
  }

  private LineMapper<CustomerAccount> lineMapper() {
    DefaultLineMapper<CustomerAccount> lineMapper = new DefaultLineMapper<>();

    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setDelimiter(","); // default COMMA
    lineTokenizer.setStrict(false);
    lineTokenizer.setNames(
        "id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

    BeanWrapperFieldSetMapper<CustomerAccount> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(CustomerAccount.class);

    lineMapper.setLineTokenizer(lineTokenizer);
    lineMapper.setFieldSetMapper(fieldSetMapper);
    return lineMapper;
  }
}
