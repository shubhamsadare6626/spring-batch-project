package com.java.spring.batch.statement.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

@Slf4j
@Configuration
@AllArgsConstructor
public class BatchDecisionConfig {

  private JobRepository jobRepository;
  private JpaTransactionManager transactionManager;

  @Bean
  public Step decisionStep() {
    return new StepBuilder("Step 1", jobRepository)
        .tasklet(
            (contribution, chunkContext) -> {
              log.info("Executed Step 1");
              return RepeatStatus.FINISHED;
            },
            transactionManager)
        .build();
  }

  @Bean
  public Step decisionStep2() {
    return new StepBuilder("Step 2", jobRepository)
        .tasklet(
            (contribution, chunkContext) -> {
              log.info("Executed Step 2");
              return RepeatStatus.FINISHED;
            },
            transactionManager)
        .build();
  }

  @Bean
  public Step decisionStep3() {
    return new StepBuilder("Step 3", jobRepository)
        .tasklet(
            (contribution, chunkContext) -> {
              log.info("Executed Step 3");
              return RepeatStatus.FINISHED;
            },
            transactionManager)
        .build();
  }

  // You can define step flow in FlowBuilder
  @Bean
  public Flow flowBuilder() {
    return new FlowBuilder<Flow>("Flow deciding").start(decisionStep()).next(decisionStep2()).end();
  }

  //	@Bean
  //	public Job flowJob() {
  //		return new JobBuilder("Flow Job", jobRepository)
  //				.start(flowBuilder())
  //				.next(decisionStep3())
  //				.end()
  //				.build();
  //	}

  //	@Bean
  //	public Job runJobOnDecision() {
  //		return new JobBuilder("Run Decision Job", jobRepository)
  //				.start(decisionStep3())
  //				.on("COMPLETED").to(flowBuilder())
  //				.end()
  //				.build();
  //	}
}
