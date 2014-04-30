package org.giffardtechnologies.net.http;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.giffardtechnologies.net.uri.escapers.CharEscapers;
import org.giffardtechnologies.net.uri.escapers.Escaper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLBuilder {
	Logger sLogger = LoggerFactory.getLogger(URLBuilder.class);
	
	private static final String CHARSET = "UTF-8";
	
	private final StringBuilder m_url;
	
	private final Escaper m_uriPathEscaper = CharEscapers.uriPathEscaper();
	private final List<NameValuePair> m_queryParams = new ArrayList<NameValuePair>();
	
	public URLBuilder(HTTPDestination destination) {
		// make sure the charset is available.
		Charset.forName(CHARSET);
		// initialize the String builder for building our URL
		m_url = new StringBuilder(destination.getScheme().getSchemeName());
		m_url.append("://").append(destination.getHost());
		if ((destination.getScheme() == HTTPScheme.HTTP && destination.getPort() != 80)
		        || (destination.getScheme() == HTTPScheme.HTTPS && destination.getPort() != 443)) {
			m_url.append(':').append(destination.getPort());
		} else {
			sLogger.trace("Port number matches default for scheme, skipping");
		}
		m_url.append("/");
	}
	
	public URLBuilder(String baseURL) {
		super();
		// make sure the charset is available.
		Charset.forName(CHARSET);
		m_url = new StringBuilder(baseURL);
	}
	
	public URLBuilder appendPathElement(String pathElement) {
		if (pathElement == null || pathElement.toString().length() == 0) {
			return this;
		}
		
		if (hasTrailingSlash()) {
			m_url.append('/');
		}
		m_url.append(m_uriPathEscaper.escape(pathElement));
		
		return this;
	}
	
	public URLBuilder appendPathElements(String[] pathElements) {
		for (String pathElement : pathElements) {
			appendPathElement(pathElement);
		}
		
		return this;
	}
	
	public URLBuilder addTrailingSlash() {
		if (hasTrailingSlash()) {
			m_url.append('/');
		}
		
		return this;
	}
	
	public URLBuilder addParam(NameValuePair nameValuePair) {
		m_queryParams.add(nameValuePair);
		
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder url = new StringBuilder(m_url.toString());
		if (!m_queryParams.isEmpty()) {
			url.append("?");
			url.append(URLEncodedUtils.format(m_queryParams, CHARSET));
		}
		return url.toString();
	}
	
	private boolean hasTrailingSlash() {
		return m_url.charAt(m_url.length() - 1) != '/';
	}
	
}
