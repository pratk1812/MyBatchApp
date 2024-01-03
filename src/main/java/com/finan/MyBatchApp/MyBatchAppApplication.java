package com.finan.MyBatchApp;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;




@SpringBootApplication
public class MyBatchAppApplication {


	public static void main(String[] args) {
		SpringApplication.run(MyBatchAppApplication.class, args);
		
	}

	@Bean
	CommandLineRunner runner(JobLauncher jobLauncher, Job myJob) {
		return args -> {
			jobLauncher.run(myJob, new JobParameters());
		};	
	}
}
