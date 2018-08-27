package com.hyva.posretail.pos.posPojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by azgar.h on 4/24/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemBrandDTO {
    private Long brandId;
    private String brandName;
    private String brandDescription;
    private String defaultType;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }
}
