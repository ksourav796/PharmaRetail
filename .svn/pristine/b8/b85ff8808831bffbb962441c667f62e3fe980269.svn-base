/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posEntities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Admin
 */
@Entity
public class Agent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long agentId;
    private String agentName;
    private String accountNo;
    private String agentAccountCode;
    private String email;
    private String mobile;
    private String address;
    private BigDecimal commission;
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;
    private String status;
    private String gstinNo;
    private String ecommerce;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAgentAccountCode() {
        return agentAccountCode;
    }

    public void setAgentAccountCode(String agentAccountCode) {
        this.agentAccountCode = agentAccountCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGstinNo() {
        return gstinNo;
    }

    public void setGstinNo(String gstinNo) {
        this.gstinNo = gstinNo;
    }

    public String getEcommerce() {
        return ecommerce;
    }

    public void setEcommerce(String ecommerce) {
        this.ecommerce = ecommerce;
    }
}
