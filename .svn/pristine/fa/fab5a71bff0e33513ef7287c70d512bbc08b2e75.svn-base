/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posEntities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Admin
 */
@Entity

public class ItemType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
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
