package com.ohh.domainname.core;

import org.springframework.web.client.RestTemplate;

public class WorkThread extends Thread {

    private RestTemplate restTemplate;

    public WorkThread(Runnable runnable,RestTemplate restTemplate) {
        super(runnable);
        this.restTemplate = restTemplate;
    }

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }
}
