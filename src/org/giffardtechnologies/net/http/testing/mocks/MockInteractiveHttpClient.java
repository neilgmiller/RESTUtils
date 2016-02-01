/*
 * Copyright 2014 Neil Miller
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.giffardtechnologies.net.http.testing.mocks;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.giffardtechnologies.net.http.testing.InvalidEntityException;
import org.giffardtechnologies.net.http.testing.InvalidParametersException;
import org.giffardtechnologies.net.http.testing.InvalidRequestException;

/**
 * MockHttpClient that can be injected in place of commons HttpClient instances. Only
 * one way communications (responses) are currently supported.
 */
public class MockInteractiveHttpClient implements HttpClient {
	
	private HttpResponseGenerator m_httpResponseGenerator;
	private RequestValidator m_requestValidator;
	
	public interface HttpResponseGenerator {
		
		public HttpResponse generate(HttpUriRequest request, Map<String, String> parameters);
	}
	
	public interface RequestValidator {
		
		public void validate(String method, URI uri) throws InvalidRequestException;
		
		public void validate(Map<String, String> parameters) throws InvalidParametersException;
		
		public void validate(List<NameValuePair> parametersList) throws InvalidParametersException;
		
		public void validate(HttpEntity entity) throws InvalidEntityException;
	}
	
	public MockInteractiveHttpClient() {
		super();
	}
	
	public MockInteractiveHttpClient(HttpResponseGenerator httpResponseGenerator) {
		super();
		m_httpResponseGenerator = httpResponseGenerator;
	}
	
	/**
	 * Sets the HttpResponseGenerator.
	 * 
	 * @param httpResponseGenerator the HttpResponseGenerator to use.
	 * 
	 * @return this - for easy chaining
	 */
	public MockInteractiveHttpClient setHttpResponseGenerator(HttpResponseGenerator httpResponseGenerator) {
		m_httpResponseGenerator = httpResponseGenerator;
		return this;
	}
	
	public MockInteractiveHttpClient setRequestValidator(RequestValidator requestValidator) {
		m_requestValidator = requestValidator;
		return this;
	}
	
	@Override
	public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
		// get the parameter map
		List<NameValuePair> parametersList = URLEncodedUtils.parse(request.getURI(), "UTF-8");
		Map<String, String> parameters = new HashMap<String, String>();
		for (NameValuePair nameValuePair : parametersList) {
			parameters.put(nameValuePair.getName(), nameValuePair.getValue());
		}
		
		// validate them
		if (m_requestValidator != null) {
			m_requestValidator.validate(request.getMethod(), request.getURI());
			m_requestValidator.validate(parametersList);
			m_requestValidator.validate(parameters);
			if (request instanceof HttpPost) {
				HttpPost postRequest = (HttpPost) request;
				m_requestValidator.validate(postRequest.getEntity());
			} else if (request instanceof HttpPut) {
				HttpPut postRequest = (HttpPut) request;
				m_requestValidator.validate(postRequest.getEntity());
			}
		}
		
		// generate the response
		return m_httpResponseGenerator.generate(request, parameters);
	}
	
	@Override
	public HttpResponse execute(HttpUriRequest arg0, HttpContext arg1) throws IOException, ClientProtocolException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public HttpResponse execute(HttpHost arg0, HttpRequest arg1) throws IOException, ClientProtocolException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> handler) throws IOException, ClientProtocolException {
		return handler.handleResponse(execute(request));
	}
	
	@Override
	public HttpResponse execute(HttpHost arg0, HttpRequest arg1, HttpContext arg2) throws IOException, ClientProtocolException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1, HttpContext arg2)
	        throws IOException, ClientProtocolException
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2) throws IOException, ClientProtocolException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2, HttpContext arg3)
	        throws IOException, ClientProtocolException
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ClientConnectionManager getConnectionManager() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public HttpParams getParams() {
		return new MockHttpParams();
	}
}
