package com.hyva.posretail.pos.posPojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by azgar.h on 6/30/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentDTO {
    private Long AgentId;
    private String AgentName;
    private String AccountNo;
    private String agentAccountCode;
    private String Email;
    private String Mobile;
    private String Address;
    private BigDecimal Commission;
    private Date effectiveDate;
    private String status;

    private String gstinNo;
    private String ecommerce;

    public Long getAgentId() {
        return AgentId;
    }

    public void setAgentId(Long agentId) {
        AgentId = agentId;
    }

    public String getAgentName() {
        return AgentName;
    }

    public void setAgentName(String agentName) {
        AgentName = agentName;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getAgentAccountCode() {
        return agentAccountCode;
    }

    public void setAgentAccountCode(String agentAccountCode) {
        this.agentAccountCode = agentAccountCode;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public BigDecimal getCommission() {
        return Commission;
    }

    public void setCommission(BigDecimal commission) {
        Commission = commission;
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
