package com.ohh.domainname.entity.godaddy;

import com.ohh.domainname.core.ResponseEntity;
import lombok.Data;

@Data
public class UrlInfo implements ResponseEntity {
    private ExactMatchDomain ExactMatchDomain;
    private String RequestId;
}


