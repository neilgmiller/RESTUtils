package org.giffardtechnologies.net.http;

public class HTTPDestination {
	
	private final HTTPScheme scheme;
	private final String host;
	private final int port;
	
	public HTTPDestination(String host) {
		this(HTTPScheme.HTTP, host, 80);
	}
	
	public HTTPDestination(HTTPScheme scheme, String host) {
		this(scheme, host, scheme == HTTPScheme.HTTP ? 80 : 443);
	}
	
	public HTTPDestination(HTTPScheme scheme, String host, int port) {
		super();
		this.scheme = scheme;
		this.host = host;
		this.port = port;
	}
	
	public HTTPScheme getScheme() {
		return scheme;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
}
