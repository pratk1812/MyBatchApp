package com.finan.MyBatchApp.jobconfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.finan.MyBatchApp.beans.StudentBean;
import com.finan.MyBatchApp.crudrepo.StudentRepository;
import com.finan.MyBatchApp.entity.StudentEntity;

@Configuration
public class MyJobConfig extends DefaultBatchConfiguration{

	@Autowired
	private StudentRepository studentRepository;
	
	private static Logger logger = LogManager.getLogger(MyJobConfig.class);
	
	private String path = "src/main/java/com/finan/MyBatchApp/student.json";
	private FileSystemResource jsonResource = new FileSystemResource(path);
	
	@Bean
	public Job myJob(JobRepository jobRepository, Step step1) {
		return new JobBuilder("myJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(step1)
				.build();
	}

	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step1", jobRepository)
				.<StudentBean, StudentEntity>chunk(10, transactionManager)
				.allowStartIfComplete(true)
				.listener(myListener())
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.build();
	}

	private JobExecutionListener  myListener() {
		return new JobExecutionListener() {

			@Override
			public void beforeJob(JobExecution jobExecution) {
				logger.info("before job");
			}

			@Override
			public void afterJob(JobExecution jobExecution) {
				logger.info("after job :: " + jobExecution.getStatus());
			}
			
		};
	}

	private ItemWriter<StudentEntity> itemWriter() {
		logger.info("is repo1 null :: " + studentRepository!=null);
		return chunk->{
			logger.info("Saving chunk in database");
			studentRepository.saveAll(chunk);
		};
	}

	private ItemProcessor<StudentBean, StudentEntity> itemProcessor() {
		return (bean)->{
			StudentEntity entity = new StudentEntity();
			BeanUtils.copyProperties(bean, entity);
			return entity;
		};
	}

	private ItemReader<StudentBean> itemReader() {
		return new JsonItemReaderBuilder<StudentBean>()
				.saveState(false)
                .jsonObjectReader(new JacksonJsonObjectReader<>(StudentBean.class))
                .resource(jsonResource)
                .build();
	}
}
