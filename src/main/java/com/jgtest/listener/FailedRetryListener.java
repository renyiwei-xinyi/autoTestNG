package com.jgtest.listener;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/*
 *实现IAnnotationTransformer接口，修改@Test的retryAnalyzer属性
 */
public class FailedRetryListener implements IAnnotationTransformer {
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        {
            IRetryAnalyzer retry = null;
            try {
                retry = iTestAnnotation.getRetryAnalyzerClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (retry == null) {
                iTestAnnotation.setRetryAnalyzer(FailedRetry.class);
            }
        }
    }
}
