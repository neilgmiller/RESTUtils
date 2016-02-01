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

import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.HttpParams;

public class MockHttpResponse implements HttpResponse {

	private BasicStatusLine m_statusLine;
	private HttpEntity      m_httpEntity;

	@Override
	public ProtocolVersion getProtocolVersion() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsHeader(String name) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Header[] getHeaders(String name) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Header getFirstHeader(String name) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Header getLastHeader(String name) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Header[] getAllHeaders() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void addHeader(Header header) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void addHeader(String name, String value) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHeader(Header header) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHeader(String name, String value) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHeaders(Header[] headers) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeHeader(Header header) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeHeaders(String name) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public HeaderIterator headerIterator() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public HeaderIterator headerIterator(String name) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public HttpParams getParams() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setParams(HttpParams params) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public StatusLine getStatusLine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusLine(StatusLine statusline) {
		m_statusLine = new BasicStatusLine(statusline.getProtocolVersion(), statusline.getStatusCode(),
		        statusline.getReasonPhrase());
	}

	@Override
	public void setStatusLine(ProtocolVersion ver, int code) {
		m_statusLine = new BasicStatusLine(ver, code, "");
	}

	@Override
	public void setStatusLine(ProtocolVersion ver, int code, String reason) {
		m_statusLine = new BasicStatusLine(ver, code, reason);
	}

	@Override
	public void setStatusCode(int code) throws IllegalStateException {
		if (m_statusLine != null) {
			m_statusLine = new BasicStatusLine(m_statusLine.getProtocolVersion(), code, m_statusLine.getReasonPhrase());
		} else {
			throw new IllegalStateException("StatusLine not set.");
		}
	}

	@Override
	public void setReasonPhrase(String reason) throws IllegalStateException {
		if (m_statusLine != null) {
			m_statusLine = new BasicStatusLine(m_statusLine.getProtocolVersion(), m_statusLine.getStatusCode(), reason);
		} else {
			throw new IllegalStateException("StatusLine not set.");
		}
	}

	@Override
	public HttpEntity getEntity() {
		return m_httpEntity;
	}

	@Override
	public void setEntity(HttpEntity entity) {
		m_httpEntity = entity;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLocale(Locale loc) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
