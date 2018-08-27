/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posPojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author admin
 */
public class SalesInvoiceDTO {
    private Long SIId;
    private String SINo;
    private BigDecimal ARBalance;
    private Long customerId;
    private Date SIDate;
    //handle 'null' during join
    private Integer dueDays;
    private String customerName;
    private String email;
    private Date date;
    private String SQNo;

    private double totalAmount;
    private double totalReceivable;
    private String SQStatus;
    private String currencySymbol;
    private String InventoryLocationName;


    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSQNo() {
        return SQNo;
    }

    public void setSQNo(String SQNo) {
        this.SQNo = SQNo;
    }

    public double getTotalReceivable() {
        return totalReceivable;
    }

    public void setTotalReceivable(double totalReceivable) {
        this.totalReceivable = totalReceivable;
    }

    public String getSQStatus() {
        return SQStatus;
    }

    public void setSQStatus(String SQStatus) {
        this.SQStatus = SQStatus;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getInventoryLocationName() {
        return InventoryLocationName;
    }

    public void setInventoryLocationName(String inventoryLocationName) {
        InventoryLocationName = inventoryLocationName;
    }

    public BigDecimal getARBalance() {
        return ARBalance;
    }

    public void setARBalance(BigDecimal ARBalance) {
        this.ARBalance = ARBalance;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getSIDate() {
        return SIDate;
    }

    public void setSIDate(Date SIDate) {
        this.SIDate = SIDate;
    }

    public Integer getDueDays() {
        return dueDays;
    }

    public void setDueDays(Integer dueDays) {
        this.dueDays = dueDays;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSIId() {
        return SIId;
    }

    public void setSIId(Long SIId) {
        this.SIId = SIId;
    }

    public String getSINo() {
        return SINo;
    }

    public void setSINo(String SINo) {
        this.SINo = SINo;
    }
    
}
