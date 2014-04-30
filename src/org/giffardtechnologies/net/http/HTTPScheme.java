package org.giffardtechnologies.net.http;

public enum HTTPScheme {
	HTTP("http"), HTTPS("https")
	
	;
	
	private String m_schemeName;
	
	private HTTPScheme(String scheme) {
		m_schemeName = scheme;
	}
	
	public String getSchemeName() {
		return m_schemeName;
	}
	
}