package com.yen.springThreadPool.service;

public interface HelloService {

    public String printNumMultiThread();

    public void slowHello() throws InterruptedException;
}
