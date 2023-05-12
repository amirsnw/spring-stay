
package com.stay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotRecordFoundException extends RuntimeException {
	public NotRecordFoundException() {
		super("Entity with the ID given not found.");
	}

	public NotRecordFoundException(String message) {
		super(message);
	}

	public NotRecordFoundException(Throwable cause) {
		super(cause);
	}

	public NotRecordFoundException(String message,
								   Throwable cause) {
		super(message, cause);
	}

	public NotRecordFoundException(String message,
								   Throwable cause,
								   boolean enableSuppression,
								   boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
