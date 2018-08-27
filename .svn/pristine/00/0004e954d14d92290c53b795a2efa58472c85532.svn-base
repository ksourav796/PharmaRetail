/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Admin
 */
@Entity
@Table(name = "purchaseinvoice")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaseInvoice implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long piId;
    private String pino;
    private String disno;
    @Temporal(TemporalType.DATE)
    private Date pidate;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private FormSetUp formsetupid;
//    @OneToOne(fetch = FetchType.LAZY)
//    private Supplier supplierId;
    private String supplierName;
    private String suppInvNo;
    private String localsuppInvNo;
    private String shipTo;
    @Column(name = "memo", columnDefinition = "LONGTEXT")
    private String memo;
    private String taxInvoice;
    private double totalAmount;
    private double totalPaid;
    private String posting;
    private String flag;
    private double totalPayable;
    private String piStatus;
    private double apBalance;
//    @OneToOne(fetch = FetchType.LAZY)
//    private Currency currencyId;
//    @OneToOne(fetch = FetchType.LAZY)
//    private Agent agentId;
    @Column(length = 5000)
    private String termsDesc;
    private double totalTaxAmount;
    private double advancetotalAmount;
    private double totalAmountEx;
    private double AdvanceAmountTax;
    private double PayableAmountTax;
    private double totalothercharges;
    private String ApprovalNum;
    private String ApprovalNumFromDtae;
    private String ApprovalNumtoDate;
    private String hiConnectStatus;
    private double cessTaxAmt;
    private String referenceNo;
    @Temporal(TemporalType.DATE)
    private Date shippingDate;
    private String supplierLocation;
    @Column(columnDefinition = "TINYINT(1)", length = 1)
    @ColumnDefault(value = "0")
    private int alertStatus;
    private String advancePayment;
    private String tcsAmount;
    private String tdsAmount;

    public Long getPiId() {
        return piId;
    }

    public void setPiId(Long piId) {
        this.piId = piId;
    }

    public String getPino() {
        return pino;
    }

    public void setPino(String pino) {
        this.pino = pino;
    }

    public String getDisno() {
        return disno;
    }

    public void setDisno(String disno) {
        this.disno = disno;
    }

    public Date getPidate() {
        return pidate;
    }

    public void setPidate(Date pidate) {
        this.pidate = pidate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSuppInvNo() {
        return suppInvNo;
    }

    public void setSuppInvNo(String suppInvNo) {
        this.suppInvNo = suppInvNo;
    }

    public String getLocalsuppInvNo() {
        return localsuppInvNo;
    }

    public void setLocalsuppInvNo(String localsuppInvNo) {
        this.localsuppInvNo = localsuppInvNo;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTaxInvoice() {
        return taxInvoice;
    }

    public void setTaxInvoice(String taxInvoice) {
        this.taxInvoice = taxInvoice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getPosting() {
        return posting;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }

    public String getPiStatus() {
        return piStatus;
    }

    public void setPiStatus(String piStatus) {
        this.piStatus = piStatus;
    }

    public double getApBalance() {
        return apBalance;
    }

    public void setApBalance(double apBalance) {
        this.apBalance = apBalance;
    }

    public String getTermsDesc() {
        return termsDesc;
    }

    public void setTermsDesc(String termsDesc) {
        this.termsDesc = termsDesc;
    }

    public double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public double getAdvancetotalAmount() {
        return advancetotalAmount;
    }

    public void setAdvancetotalAmount(double advancetotalAmount) {
        this.advancetotalAmount = advancetotalAmount;
    }

    public double getTotalAmountEx() {
        return totalAmountEx;
    }

    public void setTotalAmountEx(double totalAmountEx) {
        this.totalAmountEx = totalAmountEx;
    }

    public double getAdvanceAmountTax() {
        return AdvanceAmountTax;
    }

    public void setAdvanceAmountTax(double advanceAmountTax) {
        AdvanceAmountTax = advanceAmountTax;
    }

    public double getPayableAmountTax() {
        return PayableAmountTax;
    }

    public void setPayableAmountTax(double payableAmountTax) {
        PayableAmountTax = payableAmountTax;
    }

    public double getTotalothercharges() {
        return totalothercharges;
    }

    public void setTotalothercharges(double totalothercharges) {
        this.totalothercharges = totalothercharges;
    }

    public String getApprovalNum() {
        return ApprovalNum;
    }

    public void setApprovalNum(String approvalNum) {
        ApprovalNum = approvalNum;
    }

    public String getApprovalNumFromDtae() {
        return ApprovalNumFromDtae;
    }

    public void setApprovalNumFromDtae(String approvalNumFromDtae) {
        ApprovalNumFromDtae = approvalNumFromDtae;
    }

    public String getApprovalNumtoDate() {
        return ApprovalNumtoDate;
    }

    public void setApprovalNumtoDate(String approvalNumtoDate) {
        ApprovalNumtoDate = approvalNumtoDate;
    }

    public String getHiConnectStatus() {
        return hiConnectStatus;
    }

    public void setHiConnectStatus(String hiConnectStatus) {
        this.hiConnectStatus = hiConnectStatus;
    }

    public double getCessTaxAmt() {
        return cessTaxAmt;
    }

    public void setCessTaxAmt(double cessTaxAmt) {
        this.cessTaxAmt = cessTaxAmt;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getSupplierLocation() {
        return supplierLocation;
    }

    public void setSupplierLocation(String supplierLocation) {
        this.supplierLocation = supplierLocation;
    }

    public int getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(int alertStatus) {
        this.alertStatus = alertStatus;
    }

    public String getAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(String advancePayment) {
        this.advancePayment = advancePayment;
    }

    public String getTcsAmount() {
        return tcsAmount;
    }

    public void setTcsAmount(String tcsAmount) {
        this.tcsAmount = tcsAmount;
    }

    public String getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(String tdsAmount) {
        this.tdsAmount = tdsAmount;
    }
}