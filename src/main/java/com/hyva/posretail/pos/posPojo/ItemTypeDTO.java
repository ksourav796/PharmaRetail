package com.hyva.posretail.pos.posPojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by azgar.h on 4/24/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemTypeDTO {

    private Long itemTypeId;
    private String itemTypeName;
    private String itemTypeDesc;

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getItemTypeDesc() {
        return itemTypeDesc;
    }

    public void setItemTypeDesc(String itemTypeDesc) {
        this.itemTypeDesc = itemTypeDesc;
    }
}
