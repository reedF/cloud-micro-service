package com.reed.micro.gateway.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class LogPostFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(LogPostFilter.class);

	@Override
	public String filterType() {
		return "post";
	}

	/**
	 * refer to org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter
	 */
	@Override
	public int filterOrder() {
		return 1000;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		if (ctx.getResponseDataStream() != null) {
			try (final InputStream responseDataStream = ctx
					.getResponseDataStream()) {
				final String responseData = CharStreams
						.toString(new InputStreamReader(responseDataStream,
								"UTF-8"));
				// setting response for other filers
				ctx.setResponseBody(responseData);
				log.info(String.format("POST=============>response is: %s",
						responseData));
			} catch (IOException e) {
				log.warn("Error reading body", e);
			}
		}
		return null;
	}
}