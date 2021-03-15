package com.ohh.domainname.entity.core;

public abstract class WebSiteEntity {

    public abstract String getApi();

    public abstract Class<? extends EntityClass> getEntityClass();

    public abstract EntityClass parseJson(String json);

}
