package com.hyva.posretail.pos.posPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kishore on 3/10/2017.
 */
public class SupplierDTO {
    private Long supplierId;
    private String supplierName;
    private String supplierNumber;
    private String supplierEmail;
    private String billingAddress;
    private String website;
    private String phoneNumber1;
    private String phoneNumber2;
    private String fax;
    private String contactName;
    private String generalNote;
    private String gstIn;
    private String state;
    private String bankName;
    private String accountNo;
    private String iFSCCode;
    private String branchName;
    private String personIncharge;
    private String country;
    private String currency;
    private String panNO;
    private String cmpyCountry;
    private List<StateDTO> stateDTOList=new ArrayList<>();
    private List<CountryDTO> countryDTOList = new ArrayList<>();
    private List<CurrencyDTO> currencyDTOList = new ArrayList<>();
    private Long terms;
    private String contactPerson;
    private String creditLimit;
    private String creditDesc;
    private String status;
    private String uin;
    private String stateName;


    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreditDesc() {
        return creditDesc;
    }

    public void setCreditDesc(String creditDesc) {
        this.creditDesc = creditDesc;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Long getTerms() {
        return terms;
    }

    public void setTerms(Long terms) {
        this.terms = terms;
    }

    public String getCmpyCountry() {
        return cmpyCountry;
    }

    public void setCmpyCountry(String cmpyCountry) {
        this.cmpyCountry = cmpyCountry;
    }

    public List<StateDTO> getStateDTOList() {
        return stateDTOList;
    }

    public void setStateDTOList(List<StateDTO> stateDTOList) {
        this.stateDTOList = stateDTOList;
    }

    public List<CountryDTO> getCountryDTOList() {
        return countryDTOList;
    }

    public void setCountryDTOList(List<CountryDTO> countryDTOList) {
        this.countryDTOList = countryDTOList;
    }

    public List<CurrencyDTO> getCurrencyDTOList() {
        return currencyDTOList;
    }

    public void setCurrencyDTOList(List<CurrencyDTO> currencyDTOList) {
        this.currencyDTOList = currencyDTOList;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPanNO() {
        return panNO;
    }

    public void setPanNO(String panNO) {
        this.panNO = panNO;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getiFSCCode() {
        return iFSCCode;
    }

    public void setiFSCCode(String iFSCCode) {
        this.iFSCCode = iFSCCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPersonIncharge() {
        return personIncharge;
    }

    public void setPersonIncharge(String personIncharge) {
        this.personIncharge = personIncharge;
    }

    public String getGstIn() {
        return gstIn;
    }

    public void setGstIn(String gstIn) {
        this.gstIn = gstIn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getGeneralNote() {
        return generalNote;
    }

    public void setGeneralNote(String generalNote) {
        this.generalNote = generalNote;
    }
}
