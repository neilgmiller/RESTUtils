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

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

/**
 * MockHttpClient that can be injected in place of commons HttpClient instances. Only
 * one way communications (responses) are currently supported.
 */
public class MockHttpClient implements HttpClient {

	private HttpResponse m_httpResponse;

	public MockHttpClient() {
		super();
	}

	public MockHttpClient(HttpResponse httpResponse) {
		super();
		m_httpResponse = httpResponse;
	}

	public MockHttpClient setHttpResponse(HttpResponse httpResponse) {
		m_httpResponse = httpResponse;
		return this;
	}

	@Override
	public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
		return m_httpResponse;
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
	public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> handler)
	        throws IOException, ClientProtocolException
	{
		return handler.handleResponse(execute(request));
	}

	@Override
	public HttpResponse execute(HttpHost arg0, HttpRequest arg1, HttpContext arg2)
	        throws IOException, ClientProtocolException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1, HttpContext arg2)
	        throws IOException, ClientProtocolException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2)
	        throws IOException, ClientProtocolException
	{
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
		throw new UnsupportedOperationException();
	}
}
