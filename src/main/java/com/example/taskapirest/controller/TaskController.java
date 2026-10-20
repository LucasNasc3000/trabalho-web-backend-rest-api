package com.example.taskapirest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.taskapirest.model.Task;
import com.example.taskapirest.repository.TaskRepository;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping({ "/task" })
public class TaskController {
	private TaskRepository repository;

	TaskController(TaskRepository taskRepository) {
		this.repository = taskRepository;
	}

	@ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Todas as tarefas encontradas com sucesso",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de sucesso",
                    summary = "Exemplo de retorno bem-sucedido",
                    value = "{ \"id\": 4, \"name\": \"Fazer café\", \"assignmentDueDate\": \"10/08/2026\", \"responsible\": \"Lucas\" }"
                )
            )
        ),
	})
	@GetMapping
	public List<?> findAll() {
		return repository.findAll();
	}

	@ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Busca bem sucedida pela tarefa por id",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de sucesso",
                    summary = "Exemplo de tarefa encontrada",
                    value = "{ \"id\": 4, \"name\": \"Comprar mochila nova\", \"assignmentDueDate\": \"04/09/2026\", \"responsible\": \"Paulo\" }"
                )
            )
        ),
	})
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Task> findById(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Todas as tarefas encontradas com sucesso",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de sucesso",
                    summary = "Exemplo de retorno bem-sucedido",
                    value = "{ \"id\": 4, \"name\": \"Fazer café\", \"assignmentDueDate\": \"10/08/2026\", \"responsible\": \"Lucas\" }"
                )
            )
        ),
	})
	@PostMapping
	public ResponseEntity<Task> create(@RequestBody @NonNull Task contato) {
		Task newTask = repository.save(contato);
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(newTask.getId())
		.toUri();

		return ResponseEntity.created(location).body(newTask);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Task task) {
		return repository.findById(id).map(record -> {
			record.setName(task.getName());
			record.setAssignmentDueDate(task.getAssignmentDueDate());
			record.setResponsible(task.getResponsible());
			Task updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable long id) {
		return repository.findById(id).map(record -> {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
