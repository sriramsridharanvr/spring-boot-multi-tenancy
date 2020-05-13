package org.sriram.mt.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sriram.mt.data.listeners.TenantEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todos")
@EntityListeners(TenantEntityListener.class)
public class Todo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "todo_id", nullable = false, unique = true)
	private String todoId;
	
	@Column(name = "text", length = 500, nullable = false)
	private String text;
	
	private boolean completed;
	
}
