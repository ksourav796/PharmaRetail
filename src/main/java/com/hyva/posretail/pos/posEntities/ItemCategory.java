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

public class ItemCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long itemCategoryId;

    private String itemCategoryName;
    private String itemCategoryDesc;
    private String status;
    private String defaultType;

    public Long getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }



    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getItemCategoryDesc() {
        return itemCategoryDesc;
    }

    public void setItemCategoryDesc(String itemCategoryDesc) {
        this.itemCategoryDesc = itemCategoryDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }
}