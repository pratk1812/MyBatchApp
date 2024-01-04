package com.finan.MyBatchApp.jobconfig;


import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.PlatformTransactionManager;

import com.finan.MyBatchApp.crudrepo.StudentRepository;
import com.finan.MyBatchApp.entity.StudentEntity;

@Configuration
public class MyJobConfig2 extends DefaultBatchConfiguration {

	@Autowired
	private StudentRepository studentRepository;
	
	private static Logger logger = LogManager.getLogger(MyJobConfig.class);
	
	private String path = "src/main/java/com/finan/MyBatchApp/students.txt";
	private FileSystemResource resource = new FileSystemResource(path);
	
	@Bean
	public Job myJob2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("myJob2", jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(myJobListener())
				.start(step(jobRepository,transactionManager))
				.build();
	}

	private Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step1", jobRepository)
				.<StudentEntity, String>chunk(10, transactionManager)
				.allowStartIfComplete(true)
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.listener(itemReadListener())
				.listener(itemWriterListner())
				.build();
	}

	private ItemWriteListener<String> itemWriterListner() {
		return new ItemWriteListener<String>() {

			@Override
			public void beforeWrite(Chunk<? extends String> items) {
				logger.info("writing begin");
			}

			@Override
			public void afterWrite(Chunk<? extends String> items) {
				logger.info("Writing items complete");
			}

			@Override
			public void onWriteError(Exception exception, Chunk<? extends String> items) {
				logger.info(exception);
			}
			
		};
	}

	private ItemReadListener<StudentEntity> itemReadListener() {
		return new ItemReadListener<StudentEntity>() {

			@Override
			public void beforeRead() {
				logger.info("Reading begin");
			}

			@Override
			public void afterRead(StudentEntity item) {
				logger.info("Reading complete ");
			}

			@Override
			public void onReadError(Exception ex) {
				logger.info(ex);
			}
			
		};
	}

	private ItemWriter<String> itemWriter() {
		return new FlatFileItemWriterBuilder<String>()
				.name("itemWriter")
				.resource(resource)
				.lineAggregator(new PassThroughLineAggregator<>())
				.build();
	}

	private ItemProcessor<StudentEntity, String> itemProcessor() {
		return entity -> entity.toString();
	}

	private ItemReader<StudentEntity> itemReader() {
		return new RepositoryItemReaderBuilder<StudentEntity>()
				.name("JobItemReader")
				.repository(studentRepository)
				.pageSize(5)
				.methodName("findAll")
				.sorts(Map.of("id", Direction.ASC))
				.build();
			
	}

	private JobExecutionListener myJobListener() {
		return new JobExecutionListener() {

			@Override
			public void beforeJob(JobExecution jobExecution) {
				logger.info("Starting job :: " + jobExecution.toString());
			}

			@Override
			public void afterJob(JobExecution jobExecution) {
				logger.info("after job :: " + jobExecution.getStatus());
			}
			
		};
	}
}
