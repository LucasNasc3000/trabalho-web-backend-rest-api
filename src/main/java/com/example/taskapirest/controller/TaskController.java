package com.example.taskapirest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskapirest.dto.CreateTask;
import com.example.taskapirest.dto.UpdateTask;
import com.example.taskapirest.model.Task;
import com.example.taskapirest.repository.TaskRepository;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping({ "/task" })
public class TaskController {
	private TaskRepository repository;

	TaskController(TaskRepository taskRepository) {
		this.repository = taskRepository;
	}

	private EntityModel<Task> toModel(Task task) {
		return EntityModel.of(task, 
			linkTo(methodOn(TaskController.class).findById(task.getId())).withSelfRel(),
			linkTo(methodOn(TaskController.class).findAll()).withRel("tasks")
		);
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
                    value = """
						[
							{ "id": 2, "name": "Fazer café", "assignmentDueDate": "10/08/2026", "responsible": "Lucas" },
					 		{ "id": 3, "name": "Trazer bolacha", "assignmentDueDate": "04/09/2026", "responsible": "Tiago" },
							{ "id": 4, "name": "Comprar suco de uva", "assignmentDueDate": "17/05/2026", "responsible": "Vanessa" }
						]
					"""
                )
            )
        ),
	})
	@GetMapping
	public List<Task> findAll() {
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
                    value = "{ \"id\": 1, \"name\": \"Comprar mochila nova\", \"assignmentDueDate\": \"04/09/2026\", \"responsible\": \"Paulo\" }"
                )
            )
        ),
	})
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Task> findById(@PathVariable long id) {
		return repository.findById(id).map(foundTask -> ResponseEntity.ok().body(foundTask))
				.orElse(ResponseEntity.notFound().build());
	}

	@ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Tarefa criada com sucesso",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de sucesso",
                    summary = "Exemplo de retorno bem-sucedido no método create",
                    value = "{ \"id\": 2, \"name\": \"Fazer café\", \"assignmentDueDate\": \"10/08/2026\", \"responsible\": \"Lucas\" }"
                )
            )
        ),
	})
	@PostMapping
	public ResponseEntity<EntityModel<Task>> create(@Valid @RequestBody CreateTask request) {
		Task newTask = new Task();
		newTask.setName(request.name());
		newTask.setAssignmentDueDate(request.assignmentDueDate());
		newTask.setResponsible(request.responsible());

		EntityModel<Task> model = toModel(repository.save(newTask));
		return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
	}

	@ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Tarefa atualizada com sucesso",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de sucesso",
                    summary = "Exemplo de atualização bem-sucedida no método update",
                    value = "{ \"id\": 2, \"name\": \"Fazer almoço\", \"assignmentDueDate\": \"10/08/2026\", \"responsible\": \"Lucas\" }"
                )
            )
        ),
	})
	@PatchMapping(value = "/{id}")
	public ResponseEntity<EntityModel<Task>> update(@PathVariable("id") long id, @Valid @RequestBody UpdateTask request) {
		return repository.findById(id).map(foundTask -> {
			if (request.name() != null) foundTask.setName(request.name());
			if (request.assignmentDueDate() != null) foundTask.setAssignmentDueDate(request.assignmentDueDate());
			if (request.responsible() != null) foundTask.setResponsible(request.responsible());
			Task updated = repository.save(foundTask);
			return ResponseEntity.ok(toModel(updated));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<Void> delete(@PathVariable long id) {
		return repository.findById(id).map(foundTask -> {
			repository.deleteById(id);
			return ResponseEntity.noContent().<Void>build();
		}).orElse(ResponseEntity.notFound().<Void>build());
	}

}
