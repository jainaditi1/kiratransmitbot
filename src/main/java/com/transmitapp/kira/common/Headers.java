package com.transmitapp.kira.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Headers {
	
	public void setHeaders(HttpHeaders headers) {
		headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
	}
	
}
