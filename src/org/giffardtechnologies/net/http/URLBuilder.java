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

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.giffardtechnologies.net.uri.escapers.CharEscapers;
import org.giffardtechnologies.net.uri.escapers.Escaper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLBuilder {
	Logger sLogger = LoggerFactory.getLogger(URLBuilder.class);
	
	private static final String CHARSET = "UTF-8";
	
	private final StringBuilder m_pathPart = new StringBuilder();
	
	private HTTPDestination mDestination;
	
	private final Escaper m_uriPathEscaper = CharEscapers.uriPathEscaper();
	private final List<NameValuePair> m_queryParams = new ArrayList<NameValuePair>();
	
	public URLBuilder(HTTPDestination destination) {
		// make sure the charset is available.
		Charset.forName(CHARSET);
		mDestination = destination;
		m_pathPart.append('/');
	}
	
	public URLBuilder(String baseURL) throws MalformedURLException {
		super();
		// make sure the charset is available.
		Charset.forName(CHARSET);
		// parse the passed base URL
		URL tempURL = new URL(baseURL);
		final int port = tempURL.getPort();
		final HTTPScheme scheme = HTTPScheme.valueOf(tempURL.getProtocol().toUpperCase(Locale.US));
		if (port == -1) {
			mDestination = new HTTPDestination(scheme, tempURL.getHost());
		} else {
			mDestination = new HTTPDestination(scheme, tempURL.getHost(), port);
		}
		
		final String path = tempURL.getPath();
		if (!path.startsWith("/")) {
			m_pathPart.append('/');
		}
		m_pathPart.append(path);
	}
	
	public URLBuilder appendPathElement(String pathElement) {
		if (pathElement == null || pathElement.toString().length() == 0) {
			return this;
		}
		
		if (!hasTrailingSlash()) {
			m_pathPart.append('/');
		}
		m_pathPart.append(m_uriPathEscaper.escape(pathElement));
		return this;
	}
	
	public URLBuilder appendPathElements(String[] pathElements) {
		for (String pathElement : pathElements) {
			appendPathElement(pathElement);
		}
		
		return this;
	}
	
	public URLBuilder addTrailingSlash() {
		if (!hasTrailingSlash()) {
			m_pathPart.append('/');
		}
		
		return this;
	}
	
	public URLBuilder addParam(NameValuePair nameValuePair) {
		m_queryParams.add(nameValuePair);
		
		return this;
	}
	
	@Override
	public String toString() {
		final StringBuilder url = new StringBuilder(mDestination.getScheme().getSchemeName());
		url.append("://").append(mDestination.getHost());
		if ((mDestination.getScheme() == HTTPScheme.HTTP && mDestination.getPort() != 80)
		        || (mDestination.getScheme() == HTTPScheme.HTTPS && mDestination.getPort() != 443)) {
			url.append(':').append(mDestination.getPort());
		} else {
			sLogger.trace("Port number matches default for scheme, skipping");
		}
		// append the path part
		url.append(m_pathPart);
		
		if (!m_queryParams.isEmpty()) {
			url.append("?");
			url.append(URLEncodedUtils.format(m_queryParams, CHARSET));
		}
		return url.toString();
	}
	
	private boolean hasTrailingSlash() {
		return m_pathPart.charAt(m_pathPart.length() - 1) == '/';
	}
	
}
