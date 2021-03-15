package com.ohh.domainname.entity.eb;

import com.alibaba.fastjson.JSON;
import com.ohh.domainname.entity.core.EntityClass;
import com.ohh.domainname.entity.core.WebSiteEntity;

import java.util.Objects;

public class Eb extends WebSiteEntity {

    private static final String API = "https://www.eb.com.cn/Home/Domain/resulted?domain={domainName}.com";

    private static final Class<UrlEb> CLAZZ = UrlEb.class;

    @Override
    public String getApi() {
        return API;
    }

    @Override
    public Class<? extends EntityClass> getEntityClass() {
        return CLAZZ;
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
