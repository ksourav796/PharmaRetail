/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posPojo;


import java.math.BigDecimal;


//import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author admin
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class RetailPrintDTO {

    private String result;
    private String date;
    private String time;
//    private List<ItemDTO> itemDetils;

    private RetailDTO siData;
    private String recieptFooter;
    private String companyLogoPath;
    private String userName;
    private String footer;
    private String hsnCode;
    private String cmpyLocation;
    private String custLocation;
    private String suppLocation;
    private String billToGST;
    private double totalIncludingTax;
    private double taxAmt;
    private double cessTaxAmt;
    private double totalExcludingTax;
    private double totalPaid;
    private BigDecimal balance;
    private String soNo;
    private String printType;
    private String taxInvoice;
    private String doNo;
    private String OtherContactsName;
    private String status;
    private  String exchangeRateValue;
//    private Currency currencyObj;
//    private OtherDetailsDto otherDetailsDto;
    private String Memo;
    private String formSetUpNo;

    public String getFormSetUpNo() {
        return formSetUpNo;
    }

    public void setFormSetUpNo(String formSetUpNo) {
        this.formSetUpNo = formSetUpNo;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

//    public OtherDetailsDto getOtherDetailsDto() {
//        return otherDetailsDto;
//    }
//
//    public void setOtherDetailsDto(OtherDetailsDto otherDetailsDto) {
//        this.otherDetailsDto = otherDetailsDto;
//    }
//
//
//    public Currency getCurrencyObj() {
//        return currencyObj;
//    }
//
//    public void setCurrencyObj(Currency currencyObj) {
//        this.currencyObj = currencyObj;
//    }

    public String getExchangeRateValue() {
        return exchangeRateValue;
    }

    public void setExchangeRateValue(String exchangeRateValue) {
        this.exchangeRateValue = exchangeRateValue;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOtherContactsName() {
        return OtherContactsName;
    }

    public void setOtherContactsName(String otherContactsName) {
        OtherContactsName = otherContactsName;
    }

//    List<FormSetupTemplateElementDTO> formSetupTemplateElementList;
//
//    public List<FormSetupTemplateElementDTO> getFormSetupTemplateElementList() {
//        return formSetupTemplateElementList;
//    }
//
//    public void setFormSetupTemplateElementList(List<FormSetupTemplateElementDTO> formSetupTemplateElementList) {
//        this.formSetupTemplateElementList = formSetupTemplateElementList;
//    }

    public String getDoNo() {
        return doNo;
    }

    public void setDoNo(String doNo) {
        this.doNo = doNo;
    }

    public String getTaxInvoice() {
        return taxInvoice;
    }

    public void setTaxInvoice(String taxInvoice) {
        this.taxInvoice = taxInvoice;
    }

    public double getCessTaxAmt() {
        return cessTaxAmt;
    }

    public void setCessTaxAmt(double cessTaxAmt) {
        this.cessTaxAmt = cessTaxAmt;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public double getTotalIncludingTax() {
        return totalIncludingTax;
    }

    public void setTotalIncludingTax(double totalIncludingTax) {
        this.totalIncludingTax = totalIncludingTax;
    }

    public double getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(double taxAmt) {
        this.taxAmt = taxAmt;
    }

    public double getTotalExcludingTax() {
        return totalExcludingTax;
    }

    public void setTotalExcludingTax(double totalExcludingTax) {
        this.totalExcludingTax = totalExcludingTax;
    }

    public String getBillToGST() {
        return billToGST;
    }

    public void setBillToGST(String billToGST) {
        this.billToGST = billToGST;
    }

    public String getCmpyLocation() {
        return cmpyLocation;
    }

    public void setCmpyLocation(String cmpyLocation) {
        this.cmpyLocation = cmpyLocation;
    }

    public String getCustLocation() {
        return custLocation;
    }

    public void setCustLocation(String custLocation) {
        this.custLocation = custLocation;
    }

    public String getSuppLocation() {
        return suppLocation;
    }

    public void setSuppLocation(String suppLocation) {
        this.suppLocation = suppLocation;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getCompanyLogoPath() {
        return companyLogoPath;
    }

    public void setCompanyLogoPath(String companyLogoPath) {
        this.companyLogoPath = companyLogoPath;
    }



    public String getRecieptFooter() {
        return recieptFooter;
    }

    public void setRecieptFooter(String recieptFooter) {
        this.recieptFooter = recieptFooter;
    }

    public RetailDTO getSiData() {
        return siData;
    }

    public void setSiData(RetailDTO siData) {
        this.siData = siData;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

//    public List<ItemDTO> getItemDetils() {
//        return itemDetils;
//    }
//
//    public void setItemDetils(List<ItemDTO> itemDetils) {
//        this.itemDetils = itemDetils;
//    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
