package com.reed.micro.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.reed.micro.gateway.feign.client.FeignService;

public class AuthFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(AuthFilter.class);

	private static final String header = "X-User-Token";

	@Autowired
	private FeignService authService;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		// must bigger then PreDecorationFilter, beacause ctx.get("proxy") is
		// setted in it
		return 6;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		//Object route = ctx.get("proxy");
		//if (route == null || route.equals("test") || route.equals("by-url")) {
		//	return false;
		//}
		Object route = ctx.get("serviceId");
		if (route == null) {
			return false;
		}
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String token = request.getHeader(header) != null ? request
				.getHeader(header) : "";
		String auth = authService.checkAuth(token);
		if (!checkResult(auth)) {
			ctx.getResponse().setContentType("application/json;charset=UTF-8");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			ctx.setResponseBody(auth);
		}

		return null;
	}

	private boolean checkResult(String auth) {
		boolean r = false;
		if (auth != null && auth.equals("0")) {
			r = true;
		}
		log.info(String.format("check auth=============>:%s", auth));
		return r;
	}
}