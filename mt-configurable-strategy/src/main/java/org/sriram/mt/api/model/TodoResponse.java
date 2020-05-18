package org.sriram.mt.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoResponse {
	private String todoId;
	private String text;
	private boolean completed;
	private String tenantId;
}
