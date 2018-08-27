/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posPojo;

/**
 * @author admin
 */
public class TaxDTO {


    private Long taxid;
    private String taxString;
    private Long taxTypeId;
    private Long taxClassId;
    private Long linkedAccountId;

    private String taxGroup;
    private String taxCode;
    private String taxName;

    private double taxPercentage;
    private boolean flag;
    private String effectiveDate;
    private String expiryDate;
    private String taxStatus;
    private String accountCode;
    private String taxDescription;

    public String getTaxDescription() {
        return taxDescription;
    }

    public void setTaxDescription(String taxDescription) {
        this.taxDescription = taxDescription;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getTaxStatus() {

        return taxStatus;
    }

    public void setTaxStatus(String taxStatusl) {
        this.taxStatus = taxStatusl;
    }

    public String getExpiryDate() {

        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getEffectiveDate() {

        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public boolean isFlag() {

        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public double getTaxPercentage() {

        return taxPercentage;
    }

    public void setTaxPercentage(double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public String getTaxName() {

        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getTaxCode() {

        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxGroup() {

        return taxGroup;
    }

    public void setTaxGroup(String taxGroup) {
        this.taxGroup = taxGroup;
    }

    public Long getLinkedAccountId() {

        return linkedAccountId;
    }

    public void setLinkedAccountId(Long linkedAccountId) {
        this.linkedAccountId = linkedAccountId;
    }

    public Long getTaxClassId() {
        return taxClassId;

    }

    public void setTaxClassId(Long taxClassId) {
        this.taxClassId = taxClassId;
    }

    public String getTaxString() {
        return taxString;
    }

    public void setTaxString(String taxString) {
        this.taxString = taxString;
    }

    public Long getTaxid() {
        return taxid;
    }

    public void setTaxid(Long taxid) {
        this.taxid = taxid;
    }

    public Long getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(Long taxTypeId) {
        this.taxTypeId = taxTypeId;
    }
}
