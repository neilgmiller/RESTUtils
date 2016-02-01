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

import org.apache.http.params.HttpParams;

public class MockHttpParams implements HttpParams {

	@Override
	public HttpParams copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBooleanParameter(String name, boolean defaultValue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getDoubleParameter(String name, double defaultValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIntParameter(String name, int defaultValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLongParameter(String name, long defaultValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isParameterFalse(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isParameterTrue(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeParameter(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HttpParams setBooleanParameter(String name, boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpParams setDoubleParameter(String name, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpParams setIntParameter(String name, int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpParams setLongParameter(String name, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpParams setParameter(String name, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

}
