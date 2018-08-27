/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posEntities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Admin
 */
@Entity
public class PurchaseInvoiceDetails implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long piitemid;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "piDetails")
//    private PurchaseInvoice pidetails;
    private String pINo;
//    @OneToOne
//    private Item itemId;
    private String itemCode;
    @Column(name = "itemDesc", columnDefinition = "LONGTEXT")
    private String itemDesc;
    private String itemName;
    private String taxName;
    private double price;
    private double qtyOrdered;
    private double qtyReceived;
    private double qtyRemaining;
    private double discPercent;
    private double itemAmountExcTax;
    private double itemTaxPer;
    private double itemTax;
    private double itemAmountIncTax;
    private String payStatus;
//    @OneToOne
//    private Tax taxId;
    private double cessTaxPer;
    private double cessTaxAmt;
    private double exchanrate;
    private double exchanamt;
    private String batchNo;
    private String itemExpiryDate;
    private double discountAmount;
    private Double discountconfigamount;
    private double itemCommisionAmount;
    private Double itemCommisionConfigAmount;
    private String serializableNumbers;
    private String serializableImeiNo;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Long getPiitemid() {
        return piitemid;
    }

    public void setPiitemid(Long piitemid) {
        this.piitemid = piitemid;
    }

    public String getpINo() {
        return pINo;
    }

    public void setpINo(String pINo) {
        this.pINo = pINo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(double qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public double getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(double qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public double getQtyRemaining() {
        return qtyRemaining;
    }

    public void setQtyRemaining(double qtyRemaining) {
        this.qtyRemaining = qtyRemaining;
    }

    public double getDiscPercent() {
        return discPercent;
    }

    public void setDiscPercent(double discPercent) {
        this.discPercent = discPercent;
    }

    public double getItemAmountExcTax() {
        return itemAmountExcTax;
    }

    public void setItemAmountExcTax(double itemAmountExcTax) {
        this.itemAmountExcTax = itemAmountExcTax;
    }

    public double getItemTaxPer() {
        return itemTaxPer;
    }

    public void setItemTaxPer(double itemTaxPer) {
        this.itemTaxPer = itemTaxPer;
    }

    public double getItemTax() {
        return itemTax;
    }

    public void setItemTax(double itemTax) {
        this.itemTax = itemTax;
    }

    public double getItemAmountIncTax() {
        return itemAmountIncTax;
    }

    public void setItemAmountIncTax(double itemAmountIncTax) {
        this.itemAmountIncTax = itemAmountIncTax;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public double getCessTaxPer() {
        return cessTaxPer;
    }

    public void setCessTaxPer(double cessTaxPer) {
        this.cessTaxPer = cessTaxPer;
    }

    public double getCessTaxAmt() {
        return cessTaxAmt;
    }

    public void setCessTaxAmt(double cessTaxAmt) {
        this.cessTaxAmt = cessTaxAmt;
    }

    public double getExchanrate() {
        return exchanrate;
    }

    public void setExchanrate(double exchanrate) {
        this.exchanrate = exchanrate;
    }

    public double getExchanamt() {
        return exchanamt;
    }

    public void setExchanamt(double exchanamt) {
        this.exchanamt = exchanamt;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getItemExpiryDate() {
        return itemExpiryDate;
    }

    public void setItemExpiryDate(String itemExpiryDate) {
        this.itemExpiryDate = itemExpiryDate;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getDiscountconfigamount() {
        return discountconfigamount;
    }

    public void setDiscountconfigamount(Double discountconfigamount) {
        this.discountconfigamount = discountconfigamount;
    }

    public double getItemCommisionAmount() {
        return itemCommisionAmount;
    }

    public void setItemCommisionAmount(double itemCommisionAmount) {
        this.itemCommisionAmount = itemCommisionAmount;
    }

    public Double getItemCommisionConfigAmount() {
        return itemCommisionConfigAmount;
    }

    public void setItemCommisionConfigAmount(Double itemCommisionConfigAmount) {
        this.itemCommisionConfigAmount = itemCommisionConfigAmount;
    }

    public String getSerializableNumbers() {
        return serializableNumbers;
    }

    public void setSerializableNumbers(String serializableNumbers) {
        this.serializableNumbers = serializableNumbers;
    }

    public String getSerializableImeiNo() {
        return serializableImeiNo;
    }

    public void setSerializableImeiNo(String serializableImeiNo) {
        this.serializableImeiNo = serializableImeiNo;
    }
}