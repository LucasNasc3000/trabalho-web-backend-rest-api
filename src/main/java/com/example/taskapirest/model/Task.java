package com.example.taskapirest.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate assignmentDueDate;
	private String responsible;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getAssignmentDueDate() {
		return assignmentDueDate;
	}
	public void setAssignmentDueDate(LocalDate assignmentDueDate) {
		this.assignmentDueDate = assignmentDueDate;
	}
	public String getResponsible() {
		return responsible;
	}
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	@Override
	public String toString() {
		return "Contato [id=" + id + ", name=" + name + ", data de entrega=" + assignmentDueDate + ", responsável=" + responsible + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, name, assignmentDueDate, responsible);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(name, other.name) && Objects.equals(assignmentDueDate, other.assignmentDueDate) && Objects.equals(id, other.id)
				&& Objects.equals(responsible, other.responsible);
	}
}
