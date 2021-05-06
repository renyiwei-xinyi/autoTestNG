package com.jgtest.exception;

public class TestException extends RuntimeException {

    /**
     * 序列ID
     */
    private static final long serialVersionUID = 4670114874512893276L;
    /**
     * 是否已打印; 用来避免嵌套调用中重复打印
     */
    private boolean printed = false;

    /**
     * 构造一个<code>TestException</code>对象。
     */
    public TestException() {
        super();
    }

    /**
     * 构造一个<code>TestException</code>对象。
     * @param message           异常描述
     */
    public TestException(String message) {
        super(message);
    }

    /**
     * 构造一个<code>TestException</code>对象。
     * @param cause             异常原因
     */
    public TestException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造一个<code>TestException</code>对象。
     * @param message           异常描述
     * @param cause             异常原因
     */
    public TestException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Getter
     * @return property value of printed
     */
    public boolean isPrinted() {
        return printed;
    }

    /**
     * Setter
     * @param printed value to be assigned to property printed
     */
    public void setPrinted(boolean printed) {
        this.printed = printed;
    }


}
