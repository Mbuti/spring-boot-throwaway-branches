package com.izeye.throwaway.test.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by izeye on 16. 4. 29..
 */
@RestController
@RequestMapping("/test")
public class TestController {
	
	@GetMapping("/httpServletRequest")
	public Map<String, Object> testHttpServletRequest(HttpServletRequest request) {
		StringBuffer requestURL = request.getRequestURL();
		String requestURI = request.getRequestURI();
		String queryString = request.getQueryString();

		Map<String, String> headers = extractHeaders(request);

		String ipAddress = request.getRemoteAddr();

		Map<String, Object> response = new HashMap<>();
		response.put("requestURL", requestURL.toString());
		response.put("requestURI", requestURI);
		response.put("queryString", queryString);
		response.put("headers", headers);
		response.put("ipAddress", ipAddress);
		return response;
	}
	
	@GetMapping("/headers/userAgent")
	public String getUserAgent(@RequestHeader("User-Agent") String userAgent) {
		return userAgent;
	}

	@GetMapping("/triggerOom")
	public void triggerOom() {
		throw new OutOfMemoryError();
	}

	@GetMapping("/print")
	public void print(@RequestParam String value) {
		System.out.println(value);
	}

	private Map<String, String> extractHeaders(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		Map<String, String> headers = new HashMap<>();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			headers.put(headerName, request.getHeader(headerName));
		}
		return headers;
	}

}
