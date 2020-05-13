package org.sriram.mt.service;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sriram.mt.data.entity.Todo;
import org.sriram.mt.data.repo.TodoRepository;

@Service
public class TodoService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Autowired
	private TodoRepository todoRepository;
	
	public List<Todo> getAllTodos() {
		return todoRepository.findAll();
	}
	
	public List<Todo> getTodos(boolean completed) {
		return todoRepository.findByCompleted(completed);
	}
	
	public void createTodo(Todo todo) {
		todo.setTodoId(UUID.randomUUID().toString());
		todo.setCompleted(false);
		todoRepository.save(todo);
	}
	
	public Todo getTodo(String todoId) {
		return todoRepository.findByTodoId(todoId);
	}
	
	@Transactional
	public void changeTodoStatus(String todoId, boolean completed) {
		Todo todo = getTodo(todoId);
		if(todo == null) throw new IllegalArgumentException("The requested todo does not exist");
		todo.setCompleted(completed);
		todoRepository.save(todo);
	}
	
}
