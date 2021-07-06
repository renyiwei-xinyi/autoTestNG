package com.jgtest.exception;

import org.testng.SkipException;

public class FilterException extends SkipException {


    public FilterException(String skipMessage) {
        super(skipMessage);
    }

    public FilterException(String skipMessage, Throwable cause) {
        super(skipMessage, cause);
    }

    @Override
    public boolean isSkip() {
        return false;
    }
}
