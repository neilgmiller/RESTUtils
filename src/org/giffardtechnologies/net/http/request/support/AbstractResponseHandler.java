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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

public abstract class AbstractResponseHandler<T> extends AbstractRawResponseHandler<T> {

	/**
	 * A method to convert the given Entity (as a string) to an object of type T. This method must be implemented by the 
	 * 
	 * @param statusCode the status code of the response
	 * @param entity the returned entity in a string
	 * 
	 * @return an instance of type T representing the data from the entity.
	 */
	protected abstract T convert(int statusCode, String entity);

	/**
	 *  This method parses the entity to a string, if it is available, before passing control to {@link #convert(int, String)}. If there is no
	 *  entity it returns null directly.
	 *  
	 * @param statusCode the status code of the response
	 * @param entity the returned entity in a string
	 * 
	 * @return an instance of type T representing the data from the entity, or null if the no entity was provided.
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	protected T convert(int statusCode, HttpEntity entity) throws ParseException, IOException {
		if (entity != null) {
			return convert(statusCode, EntityUtils.toString(entity, "utf-8"));
		} else {
			return convert(statusCode, (String) null);
		}
	}

	/**
	 *  This method takes the response and pulls out the information to call {@link #convert(int, HttpEntity)}.
	 * 
	 * @return an instance of type T representing the data from the entity, or null if the no entity was provided.
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	@Override
	protected T convert(HttpResponse response) throws ParseException, IOException {
		return convert(response.getStatusLine().getStatusCode(), response.getEntity());
	}

}