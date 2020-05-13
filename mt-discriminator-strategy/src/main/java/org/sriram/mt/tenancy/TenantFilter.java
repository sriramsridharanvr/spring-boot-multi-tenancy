package org.sriram.mt.tenancy;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class TenantFilter implements Filter{

	public static final String TENANT_HEADER = "X-TENANT-ID";
	
	@Override
	  public void init(FilterConfig filterConfig) throws ServletException {
	  }

	  @Override
	  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
	    HttpServletResponse response = (HttpServletResponse) servletResponse;
	    HttpServletRequest request = (HttpServletRequest) servletRequest;
	    if(request.getRequestURI().startsWith("/api")) {
	    	String tenantHeader = request.getHeader(TENANT_HEADER);
	    	if (tenantHeader != null && !tenantHeader.isEmpty()) {
	    		TenantContext.setCurrentTenant(tenantHeader);
	    	} else {
	    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    		response.getWriter().write("{\"error\": \"No Tenant ID Supplied\"}");
	    		response.getWriter().flush();
	    		return;
	    	}
	    }
	    filterChain.doFilter(servletRequest, servletResponse);
	  }

	  @Override
	  public void destroy() {
		  
	  }
	
}
