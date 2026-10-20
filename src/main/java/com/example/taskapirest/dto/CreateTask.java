package com.example.taskapirest.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateTask (
    @NotBlank(message = "Nome da tarefa não preenchido") @Size(max = 100) @Pattern(regexp = ".*\\S.*") String name, 
    @NotNull (message = "Data de entrega não preenchida") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate assignmentDueDate, 
    @NotBlank(message = "Nome do responsável não preenchido") @Size(max = 30) @Pattern(regexp = ".*\\S.*") String responsible) 
    {}
