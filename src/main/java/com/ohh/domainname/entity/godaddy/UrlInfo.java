package com.ohh.domainname.entity.godaddy;

import com.ohh.domainname.entity.core.EntityClass;
import lombok.Data;

@Data
public class UrlInfo implements EntityClass {
    private ExactMatchDomain ExactMatchDomain;
    private String RequestId;
}


