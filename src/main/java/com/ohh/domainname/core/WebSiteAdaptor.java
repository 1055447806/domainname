package com.ohh.domainname.core;

public abstract class WebSiteAdaptor {

    public abstract String getApi();

    public abstract ResponseEntity parseJson(String json);

}
