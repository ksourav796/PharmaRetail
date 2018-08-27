package com.hyva.posretail.pos.posPojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by azgar.h on 4/24/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemMSICDTO {

    private long mscid;
    private String code;
    private String description;


    public long getMscid() {
        return mscid;
    }

    public void setMscid(long mscid) {
        this.mscid = mscid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
