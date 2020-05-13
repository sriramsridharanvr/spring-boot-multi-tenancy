package org.sriram.mt.tenancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantContext {
	private static Logger logger = LoggerFactory.getLogger(TenantContext.class);
	private static ThreadLocal<String> currentTenant = new ThreadLocal<>();
	
	public static String getCurrentTenant() {
		return currentTenant.get();
	}
	
	public static void setCurrentTenant(String tenant) {
		currentTenant.set(tenant);
		logger.debug("Current tenant set to --> {}", tenant);
	}
	
	public static void clear() {
		currentTenant.remove();
	}
}
