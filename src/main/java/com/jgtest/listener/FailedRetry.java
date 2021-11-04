package com.jgtest.listener;

import com.jgtest.exception.TestException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/*
 *案例执行抛异常则重试
 */
public class FailedRetry implements IRetryAnalyzer {
    private int retryCount = 1;
    private static final int maxRetryCount = 3;

    @Override
    public boolean retry(ITestResult iTestResult) {
        //If an exception is thrown, the failure case will be rerun. If it is an assertion error, try again
        if (iTestResult.getThrowable() instanceof AssertionError && retryCount % maxRetryCount != 0) {
            retryCount++;
            return true;
        } else {
            retryCount = 1;
            return false;
        }
    }
}
