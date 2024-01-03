package com.finan.MyBatchApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;




@SpringBootApplication
public class MyBatchAppApplication {

	private static Logger logger = LogManager.getLogger(MyBatchAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyBatchAppApplication.class, args);
		logger.info("Test log");
	}

	@Bean
	CommandLineRunner runner(JobLauncher jobLauncher, Job myJob) {
		return args -> {
			logger.info("running job");
			jobLauncher.run(myJob, new JobParameters());
		};	
	}
}
