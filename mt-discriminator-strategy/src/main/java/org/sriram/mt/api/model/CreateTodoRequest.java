package org.sriram.mt.api.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTodoRequest {
	
	@NotBlank(message = "Please specify a task")
	private String text;
}
