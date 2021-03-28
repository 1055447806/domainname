package com.ohh.domainname.entity.eb;

import com.alibaba.fastjson.JSON;
import com.ohh.domainname.core.WebSiteAdaptor;

import java.util.Objects;

public class Eb extends WebSiteAdaptor {

    private static final String API = "https://www.eb.com.cn/Home/Domain/resulted?domain={domainName}.com";

    private static final Class<UrlEb> CLAZZ = UrlEb.class;

    @Override
    public String getApi() {
        return API;
    }

    @Override
    public UrlEb parseJson(String json) {
        UrlEb urlEb = JSON.parseObject(json, CLAZZ);
        if (Objects.nonNull(urlEb) && urlEb.isResult()) {
            return urlEb;
        }
        return null;
    }
}
