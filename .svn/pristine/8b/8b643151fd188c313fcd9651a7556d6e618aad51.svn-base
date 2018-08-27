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
 * @author ranjith.g
 */
@Entity

public class CompanyMSICcode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long msiccomid;
    @ManyToOne
    private Msiccode mscid;
    private String msiccode;
    @Column(length = 2024)
    private String descrip;
    private String status;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getMsiccomid() {
        return msiccomid;
    }

    public void setMsiccomid(Long msiccomid) {
        this.msiccomid = msiccomid;
    }

    public Msiccode getMscid() {
        return mscid;
    }

    public void setMscid(Msiccode mscid) {
        this.mscid = mscid;
    }

    public String getMsiccode() {
        return msiccode;
    }

    public void setMsiccode(String msiccode) {
        this.msiccode = msiccode;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
