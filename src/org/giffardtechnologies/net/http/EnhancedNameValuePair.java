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
package org.giffardtechnologies.net.http;

import java.util.Locale;

import org.apache.http.NameValuePair;

public class EnhancedNameValuePair implements NameValuePair {

	private final String m_name;
	private final String m_value;

	public EnhancedNameValuePair(String name, String value) {
		super();
		m_name = name;
		m_value = value;
	}

	public EnhancedNameValuePair(String name, int value) {
		super();
		m_name = name;
		m_value = Integer.toString(value);
	}

	public EnhancedNameValuePair(String name, long value) {
		super();
		m_name = name;
		m_value = Long.toString(value);
	}

	public EnhancedNameValuePair(String name, boolean value) {
		super();
		m_name = name;
		m_value = Boolean.toString(value);
	}

	public EnhancedNameValuePair(String name, double value) {
		m_name = name;
		m_value = String.format(Locale.US, "%.2f", value);
	}

	@Override
	public String getName() {
		return m_name;
	}

	@Override
	public String getValue() {
		return m_value;
	}

}
