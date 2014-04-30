package org.giffardtechnologies.net.http;

import org.apache.http.StatusLine;

public class HTTPStatusException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private final StatusLine statusLine;
	
	public HTTPStatusException(StatusLine statusLine) {
		super(String.format("Error %d: %s", statusLine.getStatusCode(), statusLine.getReasonPhrase()));
		this.statusLine = statusLine;
	}
	
	public StatusLine getStatusLine() {
		return statusLine;
	}
	
}
