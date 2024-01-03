package com.finan.MyBatchApp.crudrepo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.finan.MyBatchApp.entity.StudentEntity;


@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Integer>  {
	
}
