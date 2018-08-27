/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hyva.posretail.pos.posEntities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author vijeesh.m
 */
@Entity

public class Msiccode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long mscid;
    private String code;
    @Column(length = 2024)
    private String description;
    private String locationId;
    private String useraccount_id;
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Msiccode() {

    }

    public Msiccode(String code, String description, String status) {
        this.code = code;
        this.description = description;
        this.status=status;
    }


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getUseraccount_id() {
        return useraccount_id;
    }

    public void setUseraccount_id(String useraccount_id) {
        this.useraccount_id = useraccount_id;
    }

    public Long getMscid() {
        return mscid;
    }

    public void setMscid(Long mscid) {
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
