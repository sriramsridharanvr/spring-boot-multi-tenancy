package org.sriram.mt.service;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.hibernate.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.sriram.mt.tenancy.TenantContext;

@Aspect
@Component
public class TodoServiceTenantAspect {

	private static final Logger logger = LoggerFactory.getLogger(TodoServiceTenantAspect.class);

	@Before("execution(* org.sriram.mt.service.TodoService.*(..)) && !execution(* org.sriram.mt.service.TodoService.run(..)) && target(todoService)")
	public void aroundExecution(JoinPoint pjp, TodoService todoService) throws Throwable{
		logger.debug("Setting tenant ID to filter");
		Filter tenantFilter = todoService.getEntityManager().unwrap(Session.class).enableFilter("TENANT_FILTER");
		tenantFilter.setParameter("tenantId", TenantContext.getCurrentTenant());
		tenantFilter.validate();
	}
	
}
