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
package org.giffardtechnologies.net.http.testing.mocks.support;

import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.giffardtechnologies.net.http.HTTPScheme;
import org.giffardtechnologies.net.http.testing.mocks.MockInteractiveHttpClient.HttpResponseGenerator;

import com.google.gson.Gson;

public class GsonHttpResponseGenerator<T> implements HttpResponseGenerator {
	private final HTTPScheme m_scheme;
	private final Gson m_gson;
	private final T m_obj;
	
	public GsonHttpResponseGenerator(HTTPScheme scheme, Gson gson, T obj) {
		super();
		m_scheme = scheme;
		m_gson = gson;
		m_obj = obj;
	}
	
	@Override
	public HttpResponse generate(HttpUriRequest request, Map<String, String> parameters) {
		HttpResponse response = new BasicHttpResponse(new ProtocolVersion(m_scheme.name(), 1, 1), 200, "OK");
		HttpEntity entity;
		// serialize it
		String json = m_gson.toJson(m_obj);
		entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		System.out.println(entity.getContentEncoding());
		
		response.setEntity(entity);
		
		return response;
	}
	
}
