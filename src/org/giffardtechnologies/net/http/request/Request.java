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
package org.giffardtechnologies.net.http.request;

import java.io.IOException;
import java.lang.reflect.Constructor;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.cache.CacheResponseStatus;
import org.apache.http.client.cache.HttpCacheContext;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.giffardtechnologies.net.http.HTTPMethod;
import org.giffardtechnologies.net.http.HTTPStatusException;
import org.giffardtechnologies.net.http.URLBuilder;
import org.giffardtechnologies.reflection.SuperClassInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Request<T, R extends Request<T, R>> {
	private static Logger s_logger = LoggerFactory.getLogger(Request.class);
	
	private final Class<T> responseClass;
	
	protected Request() {
		responseClass = new SuperClassInspector(getClass()).findActualClassArgumentToSuper(0);
	}
	
	@SuppressWarnings("incomplete-switch")
	protected T execute() throws ClientProtocolException, IOException, HTTPStatusException {
		HttpUriRequest request;
		String url = getURL().toString();
		switch (getHTTPMethod()) {
			case GET:
				request = new HttpGet(url);
				break;
			case POST:
				request = createPostRequest(url);
				break;
			case PUT:
				request = createPutRequest(url);
				break;
			case DELETE:
				request = new HttpDelete(url);
				break;
			default:
				throw new UnsupportedOperationException(getHTTPMethod().name() + " is not supported.");
		}
		setupHeaders(request);
		s_logger.debug(request.getMethod() + " request to: " + url + " with headers: ");
		final Header[] allHeaders = request.getAllHeaders();
		for (Header header : allHeaders) {
			if (header.getName().startsWith("X")) {
				s_logger.debug(header.getName() + ": " + header.getValue());
			}
		}
		HttpCacheContext context = HttpCacheContext.create();
		T responseObject = getHttpClient().execute(request, getResponseHandler(), context);
		CacheResponseStatus responseStatus = context.getCacheResponseStatus();
		switch (responseStatus) {
			case CACHE_HIT:
				s_logger.debug("A response was generated from the cache with " + "no requests sent upstream");
				break;
			case CACHE_MODULE_RESPONSE:
				s_logger.debug("The response was generated directly by the " + "caching module");
				break;
			case CACHE_MISS:
				s_logger.debug("The response came from an upstream server");
				break;
			case VALIDATED:
				s_logger.debug("The response was generated from the cache " + "after validating the entry with the origin server");
				break;
		}
		
		return responseObject;
	}
	
	protected abstract ResponseHandler<T> getResponseHandler();
	
	protected abstract URLBuilder getURL();
	
	protected HTTPMethod getHTTPMethod() {
		return HTTPMethod.GET;
	}
	
	protected HttpEntity getEntity() {
		return null;
	}
	
	// TODO rename
	protected abstract HttpClient getHttpClient();
	
	protected Class<T> getResponseClass() {
		return responseClass;
	}
	
	protected void setupHeaders(@SuppressWarnings("unused") HttpRequest request) {
		// NO-OP
	}
	
	private HttpUriRequest createPostRequest(String url) {
		HttpPost request = new HttpPost(url);
		handleEntity(request);
		return request;
	}
	
	private HttpUriRequest createPutRequest(String url) {
		HttpPut request = new HttpPut(url);
		handleEntity(request);
		return request;
	}
	
	private void handleEntity(HttpEntityEnclosingRequest request) {
		HttpEntity entity = getEntity();
		if (entity != null) {
			request.setEntity(entity);
		}
	}
	
	public abstract static class RequestBuilder<R, B extends RequestBuilder<R, B>> {
		
		private final Class<R> requestClass;
		private final Class<B> builderClass;
		
		protected RequestBuilder() {
			super();
			SuperClassInspector superClassInspector = new SuperClassInspector(getClass());
			try {
				requestClass = superClassInspector.findActualClassArgumentToSuper(0);
				builderClass = superClassInspector.findActualClassArgumentToSuper(1);
			} catch (RuntimeException e) {
				// TODO log this
				throw e;
			}
			if (!this.getClass().isAssignableFrom(builderClass)) {
				throw new IllegalStateException("This builder class is not a subclass of of the generic parameter B.");
			}
		}
		
		@SuppressWarnings("unchecked")
		protected B self() {
			return (B) this;
		}
		
		protected R construct() {
			try {
				try {
					Constructor<R> declaredConstructor = requestClass.getDeclaredConstructor(builderClass);
					declaredConstructor.setAccessible(true);
					return declaredConstructor.newInstance(this);
				} catch (NoSuchMethodException e) {
					return requestClass.newInstance();
				}
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		
		protected abstract void validate() throws IllegalStateException;
		
		public R build() {
			validate();
			return construct();
		}
	}
}
