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
package org.giffardtechnologies.net.http.testing;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.giffardtechnologies.net.http.HTTPScheme;
import org.giffardtechnologies.net.http.testing.mocks.MockInteractiveHttpClient.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRequestValidator implements RequestValidator {
	private static Logger s_logger = LoggerFactory.getLogger(AbstractRequestValidator.class);
	
	private final String m_method;
	private final HTTPScheme m_scheme;
	private final String m_path;
	
	public AbstractRequestValidator(String method, HTTPScheme scheme, String path) {
		super();
		m_method = method;
		m_scheme = scheme;
		m_path = path;
	}
	
	@Override
	public void validate(String method, URI uri) throws InvalidRequestException {
		s_logger.debug("Validating: " + uri.toString());
		if (!method.equals(m_method)) {
			throw new InvalidRequestException("Method must be '" + m_method + "' is '" + method + "'.");
		}
		if (!uri.getScheme().equalsIgnoreCase(m_scheme.name())) {
			throw new InvalidRequestException("Scheme must be '" + m_scheme.name() + "' is '" + uri.getScheme().toUpperCase() + "'.");
		}
		if (!uri.getRawPath().equals(m_path)) {
			throw new InvalidRequestException("URI path part must be '" + m_path + "' is '" + uri.getRawPath() + "'.");
		}
	}
	
	protected void checkFor(String key, Map<String, String> parameters) {
		if (!parameters.containsKey(key)) {
			throw new InvalidParametersException("Missing '" + key + "'.");
		}
	}
	
	protected void checkForEquals(String key, String value, Map<String, String> parameters) {
		if (!parameters.containsKey(key)) {
			throw new InvalidParametersException("Missing '" + key + "'.");
		}
		String pValue = parameters.get(key);
		if (!pValue.equals(value)) {
			throw new InvalidParametersException("'" + key + "' equals '" + pValue + "' not '" + value + "'.");
		}
	}
	
	protected void checkEqualsAndOrder(String key, List<NameValuePair> parametersList, String... parameters) {
		LinkedList<String> keyedParams = new LinkedList<String>();
		for (NameValuePair nameValuePair : parametersList) {
			if (nameValuePair.getName().equals(key)) {
				keyedParams.add(nameValuePair.getValue());
			}
		}
		if (keyedParams.size() != parameters.length) {
			throw new InvalidParametersException(
			        String.format("Number of values for '%s' does not match expects. Was %d, but expected %d.",
			                      key,
			                      keyedParams.size(),
			                      parameters.length));
		}
		int i = 1;
		for (String expectedValue : parameters) {
			String passedValue = keyedParams.removeFirst();
			if (!passedValue.equals(expectedValue)) {
				throw new InvalidParametersException(String.format("Parameter %d of '%s' equals '%s' not '%s'.",
				                                                   i,
				                                                   key,
				                                                   passedValue,
				                                                   expectedValue));
			}
			i++;
		}
	}
	
	@Override
	public void validate(List<NameValuePair> parametersList) throws InvalidParametersException {
		// default is to do nothing
	}
	
	@Override
	public void validate(HttpEntity entity) {
		// default is to do nothing
	}
	
}
