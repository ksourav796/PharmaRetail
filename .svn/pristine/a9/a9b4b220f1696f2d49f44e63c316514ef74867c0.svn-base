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
@Table(name = "salesreturn", uniqueConstraints = @UniqueConstraint(columnNames = {"srNo"}))

public class SalesReturn implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long srId;
    private String srNo;
    @Temporal(TemporalType.DATE)
    private Date srDate;
    private String shipTo;
    private String flag;
    private String customerpoNo;
    @Column(name = "memo", columnDefinition = "LONGTEXT")
    private String memo;
    private double totalcreditNote;
    private double totalTaxAmount;
    private String srStatus;
    private String posting;
//    @OneToOne
//    private Customer customerId;
//    @OneToOne
//    private Currency currencyId;
    private double exchangeRateValue;
    private String sINo;
    private String currencyName;
    private String customerName;
    //    @OneToOne
//    private DirectSalesInvoiceTemplate siId;
    private String hiConnectStatus;
    private String custLocation;
    private String taxInvoice;
    private String returnReasion;
    private double totalChequePymtAmtReturned;
    private String chequeNumber;
    private double totalCashPymtAmtReturned;
    private double totalVoucherPymtAmtReturned;
    private String voucherNumber;
    private double cessTaxAmt;

    public Long getSrId() {
        return srId;
    }

    public void setSrId(Long srId) {
        this.srId = srId;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public Date getSrDate() {
        return srDate;
    }

    public void setSrDate(Date srDate) {
        this.srDate = srDate;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCustomerpoNo() {
        return customerpoNo;
    }

    public void setCustomerpoNo(String customerpoNo) {
        this.customerpoNo = customerpoNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getTotalcreditNote() {
        return totalcreditNote;
    }

    public void setTotalcreditNote(double totalcreditNote) {
        this.totalcreditNote = totalcreditNote;
    }

    public double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getSrStatus() {
        return srStatus;
    }

    public void setSrStatus(String srStatus) {
        this.srStatus = srStatus;
    }

    public String getPosting() {
        return posting;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }

    public double getExchangeRateValue() {
        return exchangeRateValue;
    }

    public void setExchangeRateValue(double exchangeRateValue) {
        this.exchangeRateValue = exchangeRateValue;
    }

    public String getsINo() {
        return sINo;
    }

    public void setsINo(String sINo) {
        this.sINo = sINo;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getHiConnectStatus() {
        return hiConnectStatus;
    }

    public void setHiConnectStatus(String hiConnectStatus) {
        this.hiConnectStatus = hiConnectStatus;
    }

    public String getCustLocation() {
        return custLocation;
    }

    public void setCustLocation(String custLocation) {
        this.custLocation = custLocation;
    }

    public String getTaxInvoice() {
        return taxInvoice;
    }

    public void setTaxInvoice(String taxInvoice) {
        this.taxInvoice = taxInvoice;
    }

    public String getReturnReasion() {
        return returnReasion;
    }

    public void setReturnReasion(String returnReasion) {
        this.returnReasion = returnReasion;
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

    public double getCessTaxAmt() {
        return cessTaxAmt;
    }

    public void setCessTaxAmt(double cessTaxAmt) {
        this.cessTaxAmt = cessTaxAmt;
    }
}
