/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posEntities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Admin
 */
@Entity

public class PurchaseReturn implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long prId;
    private String prNo;
    @Temporal(TemporalType.DATE)
    private Date prDate;
//    @OneToOne
//    private Supplier supplierId;
    private String supplierName;
    private String currencyName;
    private String supplierInvoiceNo;
    @Column(name = "memo", columnDefinition = "LONGTEXT")
    private String memo;
    private double totalDebitNote;
    private String status;
//    @OneToOne
//    private Currency currencyId;
    private double exchangeRateValue;
    private String totalTaxAmount;
    private String flag;
//    @OneToOne
//    private PurchaseInvoice piId;
    private String purchaseInvoiceNo;
    private String posting;
    private String postingDate;
    private String suppLocation;
    private String taxInvoice;
    private double cessTaxAmt;
    private double totalChequePymtAmtReturned;
    private String chequeNumber;
    private double totalCashPymtAmtReturned;
    private double totalVoucherPymtAmtReturned;
    private String voucherNumber;
    private String posPurchaPaymentTypeId;

    public Long getPrId() {
        return prId;
    }

    public void setPrId(Long prId) {
        this.prId = prId;
    }

    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    public Date getPrDate() {
        return prDate;
    }

    public void setPrDate(Date prDate) {
        this.prDate = prDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getSupplierInvoiceNo() {
        return supplierInvoiceNo;
    }

    public void setSupplierInvoiceNo(String supplierInvoiceNo) {
        this.supplierInvoiceNo = supplierInvoiceNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getTotalDebitNote() {
        return totalDebitNote;
    }

    public void setTotalDebitNote(double totalDebitNote) {
        this.totalDebitNote = totalDebitNote;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getExchangeRateValue() {
        return exchangeRateValue;
    }

    public void setExchangeRateValue(double exchangeRateValue) {
        this.exchangeRateValue = exchangeRateValue;
    }

    public String getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(String totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getPurchaseInvoiceNo() {
        return purchaseInvoiceNo;
    }

    public void setPurchaseInvoiceNo(String purchaseInvoiceNo) {
        this.purchaseInvoiceNo = purchaseInvoiceNo;
    }

    public String getPosting() {
        return posting;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getSuppLocation() {
        return suppLocation;
    }

    public void setSuppLocation(String suppLocation) {
        this.suppLocation = suppLocation;
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

    public double getTotalChequePymtAmtReturned() {
        return totalChequePymtAmtReturned;
    }

    public void setTotalChequePymtAmtReturned(double totalChequePymtAmtReturned) {
        this.totalChequePymtAmtReturned = totalChequePymtAmtReturned;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public double getTotalCashPymtAmtReturned() {
        return totalCashPymtAmtReturned;
    }

    public void setTotalCashPymtAmtReturned(double totalCashPymtAmtReturned) {
        this.totalCashPymtAmtReturned = totalCashPymtAmtReturned;
    }

    public double getTotalVoucherPymtAmtReturned() {
        return totalVoucherPymtAmtReturned;
    }

    public void setTotalVoucherPymtAmtReturned(double totalVoucherPymtAmtReturned) {
        this.totalVoucherPymtAmtReturned = totalVoucherPymtAmtReturned;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getPosPurchaPaymentTypeId() {
        return posPurchaPaymentTypeId;
    }

    public void setPosPurchaPaymentTypeId(String posPurchaPaymentTypeId) {
        this.posPurchaPaymentTypeId = posPurchaPaymentTypeId;
    }
}
