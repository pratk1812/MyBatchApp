package com.finan.MyBatchApp.crudrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finan.MyBatchApp.entity.StudentEntity;


@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer>  {
	
}
