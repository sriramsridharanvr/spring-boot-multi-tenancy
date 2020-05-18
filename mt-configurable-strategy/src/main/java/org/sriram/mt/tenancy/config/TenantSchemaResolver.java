package org.sriram.mt.tenancy.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.sriram.mt.tenancy.TenantContext;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver{
	
	private static final String DEFAULT_TENANT = "public";
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenant = TenantContext.getCurrentTenant();
		if(tenant == null) return DEFAULT_TENANT;
		
		return tenant;
	}
	
	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}
