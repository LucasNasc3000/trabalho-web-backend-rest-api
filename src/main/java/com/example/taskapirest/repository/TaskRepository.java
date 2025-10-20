package com.example.taskapirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taskapirest.model.Task;

@Repository
public interface TaskRepository  extends JpaRepository <Task, Long>{

}

