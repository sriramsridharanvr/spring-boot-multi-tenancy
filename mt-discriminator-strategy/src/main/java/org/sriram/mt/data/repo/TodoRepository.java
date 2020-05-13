package org.sriram.mt.data.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sriram.mt.data.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

	public Todo findByTodoId(String todoId);
	public List<Todo> findByCompleted(boolean status);
	
}
