package com.example.taskapirest.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateTask (
    @Pattern(regexp = ".*\\S.*") @Size(max = 100, message = "O nome da tarefa deve ter no máximo 100 caracteres") String name, 
    @JsonFormat(pattern = "dd/MM/yyyy") LocalDate assignmentDueDate, 
    @Pattern(regexp = ".*\\S.*") @Size(max = 30, message = "O nome do responsável deve ter no máximo 30 caracteres") String responsible) 
    {}
