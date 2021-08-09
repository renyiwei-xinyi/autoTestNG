package com.jgtest;

import com.jgtest.common.SetUpTearDown;
import com.jgtest.extension.*;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Iterator;


public class BaseTestNG extends SetUpTearDown {

    protected static final String single = "single";
    protected static final String parallel = "parallel";

    @DataProvider(name = "single")
    public static Iterator<Object[]> single(Method method){
        return ProviderUtil.getFileSource(method);
    }

    @DataProvider(name = "parallel", parallel = true)
    public static Iterator<Object[]> parallel(Method method){
        return ProviderUtil.getFileSource(method);
    }



}
