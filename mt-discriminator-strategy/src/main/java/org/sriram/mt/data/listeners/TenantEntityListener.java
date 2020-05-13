package org.sriram.mt.data.listeners;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.sriram.mt.data.entity.Todo;
import org.sriram.mt.tenancy.TenantContext;

public class TenantEntityListener {

	@PrePersist
	@PreUpdate
	@PreRemove
	private void setTenantId(Object object) {
		if(object instanceof Todo) {
			((Todo) object).setTenantId(TenantContext.getCurrentTenant());
		}
	}
	
}
