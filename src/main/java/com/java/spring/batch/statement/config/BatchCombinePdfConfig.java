package com.java.spring.batch.statement.config;

import com.java.spring.batch.statement.batch.PdfProcessTasklet;
import com.java.spring.batch.statement.repository.CustomerAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
@AllArgsConstructor
public class BatchCombinePdfConfig {

  private JobRepository jobRepository;
  private JpaTransactionManager transactionManager;
  private CustomerAccountRepository customerAccountRepository;

  // Tasklet -Generate All record Pdf by itext
  @Bean
  public PdfProcessTasklet pdfGeneratingtasklet() {
    // to perform a single task within a step .Consist of several steps
    return new PdfProcessTasklet(customerAccountRepository);
  }

  @Bean
  public Step pdfStep() {
    return new StepBuilder("Generate PDF Step", jobRepository)
        .tasklet(pdfGeneratingtasklet(), transactionManager)
        .taskExecutor(asyncTaskExecutor())
        .build();
  }

  @Bean
  public TaskExecutor asyncTaskExecutor() {
    SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
    asyncTaskExecutor.setConcurrencyLimit(3);
    return asyncTaskExecutor;
  }

  //	@Bean
  //	public Job makePdf() {
  //		return new JobBuilder("Export-DBTOPDF Job", jobRepository)
  //				.start(pdfStep())
  //				.build();	// start() or flow() -> next()
  //	}
}
