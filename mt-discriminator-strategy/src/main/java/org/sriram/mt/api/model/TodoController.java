package org.sriram.mt.api.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sriram.mt.data.entity.Todo;
import org.sriram.mt.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("")
	public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody CreateTodoRequest request) {
		Todo todo = modelMapper.map(request, Todo.class);
		todoService.createTodo(todo);
		return ResponseEntity.ok().body(modelMapper.map(todo, TodoResponse.class));
	}
	
	@GetMapping("/{todoId}")
	public ResponseEntity<TodoResponse> getTodo(@PathVariable String todoId) {
		Todo todo = todoService.getTodo(todoId);
		if(todo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(modelMapper.map(todo, TodoResponse.class));
	}
	
	@GetMapping("")
	public ResponseEntity<List<TodoResponse>> getTodos(@RequestParam(name = "status", required=false, defaultValue = "") String status) {
		Type listType = new TypeToken<List<TodoResponse>>() {}.getType();
		
		List<Todo> todos = new ArrayList<>();
		if(StringUtils.isEmpty(status)) {
			todos = todoService.getAllTodos();
		}else if("completed".equalsIgnoreCase(status)) {
			todos = todoService.getTodos(true);
		}else if("pending".equalsIgnoreCase(status)) {
			todos = todoService.getTodos(false);		
		}else {
			todos = todoService.getAllTodos();
		}
		
		List<TodoResponse> response = modelMapper.map(todos, listType);
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping("/{todoId}")
	public ResponseEntity<Object> markTodoAsPending(@PathVariable String todoId) {
		try {
			todoService.changeTodoStatus(todoId, false);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{todoId}")
	public ResponseEntity<Object> markTodoAsCompleted(@PathVariable String todoId) {
		try {
			todoService.changeTodoStatus(todoId, true);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
