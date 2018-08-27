/*
 * To change this template; choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posEntities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Admin
 */
@Entity

public class DirectSalesInvoiceTemplate {

//    @OneToOne
//    Customer CustomerId;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long dSIId;
    private String siNo;
    @Temporal(TemporalType.DATE)
    private Date siDate;
    private String CustPONo;
    private String referenceno;
    private String shipTo;
    private String Memo;
    private String flag;
    private String taxInvoice;
    private double FreightCharge;
    private double totalAmount;
    private double totalReceived;
    private double salesTotalTaxAmt;
    private double totalReceivable;
    private String siStatus;
    private double arBalance;
    private double discountAmount;
    private String currencyName;
    private String customerName;
    private String agentName;
//    @OneToOne
//    private Currency currencyId;
//    @OneToOne
//    private Agent agentId;
    private String showReport;
    private double exchangeRateValue;
    private String posting;
    private String locationId;
    private String useraccount_id;
    private double totalDiscountAmount;
    public String serialNumber;
    private double cessTaxAmt;
    private String shippingReferenceNo;
    private String otherDetailsId;
    private String otherContactId;

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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Long getdSIId() {
        return dSIId;
    }

    public void setdSIId(Long dSIId) {
        this.dSIId = dSIId;
    }

    public String getSiNo() {
        return siNo;
    }

    public void setSiNo(String siNo) {
        this.siNo = siNo;
    }

    public Date getSiDate() {
        return siDate;
    }

    public void setSiDate(Date siDate) {
        this.siDate = siDate;
    }

    public String getCustPONo() {
        return CustPONo;
    }

    public void setCustPONo(String custPONo) {
        CustPONo = custPONo;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTaxInvoice() {
        return taxInvoice;
    }

    public void setTaxInvoice(String taxInvoice) {
        this.taxInvoice = taxInvoice;
    }

    public double getFreightCharge() {
        return FreightCharge;
    }

    public void setFreightCharge(double freightCharge) {
        FreightCharge = freightCharge;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(double totalReceived) {
        this.totalReceived = totalReceived;
    }

    public double getSalesTotalTaxAmt() {
        return salesTotalTaxAmt;
    }

    public void setSalesTotalTaxAmt(double salesTotalTaxAmt) {
        this.salesTotalTaxAmt = salesTotalTaxAmt;
    }

    public double getTotalReceivable() {
        return totalReceivable;
    }

    public void setTotalReceivable(double totalReceivable) {
        this.totalReceivable = totalReceivable;
    }

    public String getSiStatus() {
        return siStatus;
    }

    public void setSiStatus(String siStatus) {
        this.siStatus = siStatus;
    }

    public double getArBalance() {
        return arBalance;
    }

    public void setArBalance(double arBalance) {
        this.arBalance = arBalance;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getShowReport() {
        return showReport;
    }

    public void setShowReport(String showReport) {
        this.showReport = showReport;
    }

    public double getExchangeRateValue() {
        return exchangeRateValue;
    }

    public void setExchangeRateValue(double exchangeRateValue) {
        this.exchangeRateValue = exchangeRateValue;
    }

    public String getPosting() {
        return posting;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getUseraccount_id() {
        return useraccount_id;
    }

    public void setUseraccount_id(String useraccount_id) {
        this.useraccount_id = useraccount_id;
    }

    public double getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(double totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getCessTaxAmt() {
        return cessTaxAmt;
    }

    public void setCessTaxAmt(double cessTaxAmt) {
        this.cessTaxAmt = cessTaxAmt;
    }

    public String getShippingReferenceNo() {
        return shippingReferenceNo;
    }

    public void setShippingReferenceNo(String shippingReferenceNo) {
        this.shippingReferenceNo = shippingReferenceNo;
    }

    public String getOtherDetailsId() {
        return otherDetailsId;
    }

    public void setOtherDetailsId(String otherDetailsId) {
        this.otherDetailsId = otherDetailsId;
    }

    public String getOtherContactId() {
        return otherContactId;
    }

    public void setOtherContactId(String otherContactId) {
        this.otherContactId = otherContactId;
    }
}
