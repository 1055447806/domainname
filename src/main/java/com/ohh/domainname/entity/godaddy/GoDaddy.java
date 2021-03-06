package com.ohh.domainname.entity.godaddy;

import com.alibaba.fastjson.JSON;
import com.ohh.domainname.core.WebSiteAdaptor;

import java.util.Objects;

public class GoDaddy extends WebSiteAdaptor {

    private static final String API = "https://sg.godaddy.com/zh/domainfind/v1/search/exact?q={domainName}";

    private static final Class<UrlInfo> CLAZZ = UrlInfo.class;

    @Override
    public String getApi() {
        return API;
    }

    @Override
    public UrlInfo parseJson(String json) {
        UrlInfo urlInfo = JSON.parseObject(json, CLAZZ);
        if (Objects.nonNull(urlInfo) &&
                (urlInfo.getExactMatchDomain().isIsAvailable() ||
                        !urlInfo.getExactMatchDomain().getPriceDisplay().equals(""))) {
            return urlInfo;
        }
        return null;
    }
}
