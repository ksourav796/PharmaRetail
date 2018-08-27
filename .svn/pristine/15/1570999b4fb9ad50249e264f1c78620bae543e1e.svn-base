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

public class DirectSalesInvoiceDetailsTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long sIItemId;
//    @OneToOne
//    Item itemId;
//    @OneToOne
//    private DirectSalesInvoiceTemplate dSIId;
    private String sINo;
    private String itemCode;
    private String itemDesc;
    private double price;
    private double qtyOrdered;
    private double qtySent;
    private double discPercent;
    private double itemAmountExcTax;
    private double itemTaxPer;
    private double itemTax;
    private String taxName;
    private String itemName;
    private double itemAmountIncTax;
    private String recStatus;
//    @ManyToOne
//    private Tax taxId;
    private double discountAmount;
    private double QtyRemain;
    private Double itemCommisionAmount;
    private Double itemCommisionConfigAmount;
    private Double discountconfigamount;
    private double cessPer;
    private double cessTaxAmt;

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

    public Long getsIItemId() {
        return sIItemId;
    }

    public void setsIItemId(Long sIItemId) {
        this.sIItemId = sIItemId;
    }

    public String getsINo() {
        return sINo;
    }

    public void setsINo(String sINo) {
        this.sINo = sINo;
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

    public double getQtySent() {
        return qtySent;
    }

    public void setQtySent(double qtySent) {
        this.qtySent = qtySent;
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

    public String getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getQtyRemain() {
        return QtyRemain;
    }

    public void setQtyRemain(double qtyRemain) {
        QtyRemain = qtyRemain;
    }

    public Double getItemCommisionAmount() {
        return itemCommisionAmount;
    }

    public void setItemCommisionAmount(Double itemCommisionAmount) {
        this.itemCommisionAmount = itemCommisionAmount;
    }

    public Double getItemCommisionConfigAmount() {
        return itemCommisionConfigAmount;
    }

    public void setItemCommisionConfigAmount(Double itemCommisionConfigAmount) {
        this.itemCommisionConfigAmount = itemCommisionConfigAmount;
    }

    public Double getDiscountconfigamount() {
        return discountconfigamount;
    }

    public void setDiscountconfigamount(Double discountconfigamount) {
        this.discountconfigamount = discountconfigamount;
    }

    public double getCessPer() {
        return cessPer;
    }

    public void setCessPer(double cessPer) {
        this.cessPer = cessPer;
    }

    public double getCessTaxAmt() {
        return cessTaxAmt;
    }

    public void setCessTaxAmt(double cessTaxAmt) {
        this.cessTaxAmt = cessTaxAmt;
    }
}
