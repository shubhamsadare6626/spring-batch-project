package com.java.spring.batch.statement.config;

import com.java.spring.batch.statement.batch.CustomerAccountProcessor;
import com.java.spring.batch.statement.batch.PdfItemProcessor;
import com.java.spring.batch.statement.entity.CustomerAccount;
import com.java.spring.batch.statement.entity.CustomerLogging;
import com.java.spring.batch.statement.mapper.CustomerAccountMapper;
import com.java.spring.batch.statement.repository.CustomerAccountRepository;
import com.java.spring.batch.statement.repository.CustomerLoggingRepository;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
@AllArgsConstructor
public class SpringBatchConfig {

  private JobRepository jobRepository;
  private JpaTransactionManager transactionManager;
  private CustomerAccountRepository customerAccountRepository;
  private CustomerLoggingRepository customerLoggingRepository;
  private DataSource dataSource;
  private CustomerAccountMapper customerAccountMapper;

  @Bean
  public PoiItemReader<CustomerAccount> excelFileReader() {
    PoiItemReader<CustomerAccount> itemReader = new PoiItemReader<>();
    itemReader.setResource(new FileSystemResource("src/main/resources/customers.xlsx"));
    itemReader.setName("File Reader");
    itemReader.setLinesToSkip(1);
    itemReader.setRowMapper(excelRowMapper());
    return itemReader;
  }

  private RowMapper<CustomerAccount> excelRowMapper() {
    BeanWrapperRowMapper<CustomerAccount> rowMapper = new BeanWrapperRowMapper<>();
    rowMapper.setTargetType(CustomerAccount.class);
    return rowMapper;
  }

  @Bean
  public CustomerAccountProcessor processor() {
    return new CustomerAccountProcessor();
  }

  @Bean
  public RepositoryItemWriter<CustomerAccount> writer() {
    RepositoryItemWriter<CustomerAccount> writer = new RepositoryItemWriter<>();
    writer.setRepository(customerAccountRepository);
    writer.setMethodName("save");
    return writer;
  }

  @Bean
  public TaskExecutor taskExecutor() {
    SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
    asyncTaskExecutor.setConcurrencyLimit(3);
    return asyncTaskExecutor;
  }

  @Bean
  public Step step() {
    return new StepBuilder("File-Reading-step", jobRepository)
        .<CustomerAccount, CustomerAccount>chunk(20, transactionManager)
        .reader(excelFileReader())
        .processor(processor())
        .writer(writer())
        .taskExecutor(taskExecutor())
        .build();
  }

  //    @Bean
  //    public Job runJob() {
  //        return new JobBuilder("Import-Customers Data", jobRepository)
  //                .flow(step())
  //                .end()
  //                .build();
  //    }

  //    ===============================================
  @Bean
  public JdbcCursorItemReader<CustomerAccount> jdbcCursorDataReader() {
    return new JdbcCursorItemReaderBuilder<CustomerAccount>()
        .name("Jdbc Database Reader")
        .dataSource(dataSource)
        .rowMapper(customerAccountMapper)
        .sql("select * FROM customers_account")
        .build();
  }

  @Bean
  public ItemProcessor<CustomerAccount, CustomerLogging> pdfProcessor() {
    return new PdfItemProcessor();
  }

  @Bean
  public RepositoryItemWriter<CustomerLogging> htmlToPdfWriter() {
    RepositoryItemWriter<CustomerLogging> writer = new RepositoryItemWriter<>();
    writer.setRepository(customerLoggingRepository);
    writer.setMethodName("save");
    return writer;
  }

  @Bean
  public Step htmlToPdfStep() {
    return new StepBuilder("HTMLTOPDF Step", jobRepository)
        .<CustomerAccount, CustomerLogging>chunk(20, transactionManager)
        .reader(jdbcCursorDataReader())
        .processor(pdfProcessor())
        .writer(htmlToPdfWriter())
        .taskExecutor(taskExecutor())
        .build();
  }

  //    @Bean
  //    public Job generateHtmlPdf() {
  //        return new JobBuilder("Generate HTMLTOPDF Job", jobRepository)
  //                .flow(htmlToPdfStep())
  //                .end()
  //                .build();
  //    }
  // ===============================================

}
