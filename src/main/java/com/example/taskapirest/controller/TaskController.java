package com.example.taskapirest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.taskapirest.dto.CreateTask;
import com.example.taskapirest.dto.UpdateTask;
import com.example.taskapirest.model.Task;
import com.example.taskapirest.repository.TaskRepository;

import io.swagger.v3.oas.annotations.Operation;
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

	@Operation(summary = "Lista todas as tarefas")
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

	@Operation(summary = "Buscar tarefa por id")
	@ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Busca bem sucedida pela tarefa por id",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de sucesso",
                    summary = "Tarefa encontrada",
                    value = """
						{ "id": 1, "name": "Comprar mochila nova", "assignmentDueDate": "04/09/2026", "responsible": "Paulo" }
					 """
                )
            )
        ),
		@ApiResponse(responseCode = "404", description = "Tarefa não encontrada", content = @Content())
	})
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Task> findById(@PathVariable long id) {
		return repository.findById(id).map(foundTask -> ResponseEntity.ok().body(foundTask))
				.orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Criar nova tarefa")
	@ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Tarefa criada com sucesso",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de sucesso",
                    summary = "Exemplo de retorno bem-sucedido no método create",
                    value = """
					{ "id": 2, "name": "Fazer café", "assignmentDueDate": "10/08/2026", "responsible": "Lucas" }
					 """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Dados inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Erro de validação",
                    summary = "Campo obrigatório não preenchido",
                    value = """
					{ "id": 2, "name": "Fazer café", "assignmentDueDate": "10/08/2026", "responsible": "Lucas" }
					 """
                )
            )
        ),
	})
	@PostMapping
	public ResponseEntity<Task> create(@Valid @RequestBody CreateTask request) {
		Task setNewTaskData = new Task();
		setNewTaskData.setName(request.name());
		setNewTaskData.setAssignmentDueDate(request.assignmentDueDate());
		setNewTaskData.setResponsible(request.responsible());

		Task newTask = repository.save(setNewTaskData);

		 URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(newTask.getId())
        .toUri();

		return ResponseEntity.created(location).body(newTask);
	}

	@Operation(summary = "Atualizar tarefa (somente os campos enviados)")
	@ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Tarefa atualizada com sucesso",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Exemplo de sucesso",
                    summary = "Exemplo de atualização bem-sucedida no método update",
                    value = """
					{ "id": 2, "name": "Fazer almoço", "assignmentDueDate": "10/08/2026", "responsible": "Lucas" }
					 """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Dados inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Erro de validação",
                    summary = "Nome do responsável acima do limite de caracteres",
                    value = """
					{ "name": "O nome do responsável deve ter no máximo 30 caracteres" }
					 """
                )
            )
        ),
		@ApiResponse(responseCode = "404", description = "Tarefa não encontrada", content = @Content())
	})
	@PatchMapping(value = "/{id}")
	public ResponseEntity<Task> update(@PathVariable("id") long id, @Valid @RequestBody UpdateTask request) {
		return repository.findById(id).map(foundTask -> {
			if (request.name() != null) foundTask.setName(request.name());
			if (request.assignmentDueDate() != null) foundTask.setAssignmentDueDate(request.assignmentDueDate());
			if (request.responsible() != null) foundTask.setResponsible(request.responsible());
			Task updated = repository.save(foundTask);
			return ResponseEntity.ok(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Remove uma tarefa")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Tarefa removida com sucesso", content = @Content),
		@ApiResponse(responseCode = "404", description = "Tarefa não encontrada", content = @Content)
	})
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<Void> delete(@PathVariable long id) {
		return repository.findById(id).map(foundTask -> {
			repository.deleteById(id);
			return ResponseEntity.noContent().<Void>build();
		}).orElse(ResponseEntity.notFound().<Void>build());
	}

}
