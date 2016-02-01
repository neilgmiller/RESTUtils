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
package org.giffardtechnologies.net.http.request.support;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRawResponseHandler<T> implements ResponseHandler<T> {
	private static Logger s_logger = LoggerFactory.getLogger(AbstractRawResponseHandler.class);
	
	public AbstractRawResponseHandler() {
		super();
	}
	
	/**
	 *  Implement this method to take the response and convert is it to an object of type T.
	 * 
	 * @return an instance of type T, the desired representation of the response.
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	protected abstract T convert(HttpResponse response) throws IOException;
	
	/**
	 * Create and throw an HttpResponseException (or subclass) using the {@link StatusLine} and entity. If unable to process
	 * simply throw nothing and standard error handling will apply.
	 * 
	 * @param statusLine
	 * @param entityString
	 * @throws HttpResponseException
	 */
	protected abstract void createErrorException(StatusLine statusLine, String entityString) throws HttpResponseException;
	
	/*
	 * Handles the boiler-plate work of handling a response. Checks status type and throws the appropriate exception, passes 
	 * responsibility on for 2xx statuses.
	 */
	@Override
	public T handleResponse(final HttpResponse response) throws HttpResponseException, IOException {
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= 500) {
			// Handle server error
			close(response);
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		} else if (statusLine.getStatusCode() >= 400) {
			HttpEntity entity = response.getEntity();
			// check that we have an entity
			if (entity != null) {
				// and that it is not empty
				String entityString = EntityUtils.toString(entity, "utf-8");
				if (entityString.length() != 0) {
					createErrorException(statusLine, entityString);
				}
			}
			// otherwise throw a standard HttpResponseException
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		} else if (statusLine.getStatusCode() >= 300) {
			close(response);
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		} else if (statusLine.getStatusCode() >= 200) {
			// Handle successful response
			return convert(response);
		} else {
			close(response);
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
	}
	
	/**
	 * Closes the entities input stream if there is one.
	 * 
	 * @param response
	 * @throws IOException
	 */
	protected void close(final HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream content = entity.getContent();
			if (content != null) {
				content.close();
			}
		}
	}
	
}