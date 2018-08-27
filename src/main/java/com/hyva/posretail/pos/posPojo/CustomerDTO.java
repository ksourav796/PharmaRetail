/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posPojo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class CustomerDTO {

    private Long customerId;
    private String customerName;
    private String customerNumber;
    private String customerCode;
    private String customerEmail;
    private String customerContact;
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
    private String website;
    private String billingAddress;
    private double loyaltyPoints;
    private double totalLoyaltyPoints;
    private String phoneNumber;
    private String address;
    private String contactPerson;
    private String companyNumber;
    private String faxTelex;
    private  String creditedLimit;
    private Long terms;
    private String status;
    private String pincode;
    private String vehicleSeries;
    private String stateName;
    private String uin;
    private String creditTerms;
    private List<StateDTO> stateDTOList=new ArrayList<>();
    private List<CountryDTO> countryDTOList = new ArrayList<>();
    private List<CurrencyDTO> currencyDTOList = new ArrayList<>();

    public String getCreditTerms() {
        return creditTerms;
    }

    public void setCreditTerms(String creditTerms) {
        this.creditTerms = creditTerms;
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

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getVehicleSeries() {
        return vehicleSeries;
    }

    public void setVehicleSeries(String vehicleSeries) {
        this.vehicleSeries = vehicleSeries;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTerms() {
        return terms;
    }

    public void setTerms(Long terms) {
        this.terms = terms;
    }

    public String getCreditedLimit() {
        return creditedLimit;
    }

    public void setCreditedLimit(String creditedLimit) {
        this.creditedLimit = creditedLimit;
    }

    public String getFaxTelex() {
        return faxTelex;
    }

    public void setFaxTelex(String faxTelex) {
        this.faxTelex = faxTelex;
    }

    public String getCompanyNumber() {return companyNumber;}

    public void setCompanyNumber(String companyNumber) {this.companyNumber = companyNumber;}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getGstIn() {
        return gstIn;
    }

    public void setGstIn(String gstIn) {
        this.gstIn = gstIn;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public double getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(double loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public double getTotalLoyaltyPoints() {
        return totalLoyaltyPoints;
    }

    public void setTotalLoyaltyPoints(double totalLoyaltyPoints) {
        this.totalLoyaltyPoints = totalLoyaltyPoints;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
