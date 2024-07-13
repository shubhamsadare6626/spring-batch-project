package com.java.spring.batch.statement.controller;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

  private final JobLauncher jobLauncher;

  private final Job runJob;

  @PostMapping("/import-customers")
  public ResponseEntity<String> importCsvToDBJob() {
    JobParameters jobParameters =
        new JobParametersBuilder()
            .addDate("startAt", new Date(System.currentTimeMillis()))
            .toJobParameters();
    JobExecution jobExecution = null;
    try {
      jobExecution = jobLauncher.run(runJob, jobParameters);
    } catch (JobExecutionAlreadyRunningException
        | JobRestartException
        | JobInstanceAlreadyCompleteException
        | JobParametersInvalidException e) {
      e.printStackTrace();
    }
    return ResponseEntity.ok(
        "Batch Job: "
            + runJob.getName()
            + " started with JobExecutionId: "
            + (jobExecution == null ? "" : jobExecution.getId()));
  }
}
