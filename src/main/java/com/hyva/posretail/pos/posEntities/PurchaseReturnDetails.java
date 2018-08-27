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

public class PurchaseReturnDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long prItemId;
    @ManyToOne
    @JoinColumn(name = "prdetails")
    private PurchaseReturn prdetails;
    @OneToOne
    private Item itemId;
    @OneToOne
    private Tax tax;
    private String itemCode;
    private String itemDesc;
    private String itemName;
    private String taxName;
    private String prNo;
    private double price;
    private double qty;
    private double qtyReceived;
    private double itemAmountExcTax;
    private double itemTaxPer;
    private double itemTax;
    private double itemAmountIncTax;
    private String retStatus;
    @OneToOne
    private PurchaseInvoiceDetails piItemId;
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

    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    public Long getPrItemId() {
        return prItemId;
    }

    public void setPrItemId(Long prItemId) {
        this.prItemId = prItemId;
    }

    public PurchaseReturn getPrdetails() {
        return prdetails;
    }

    public void setPrdetails(PurchaseReturn prdetails) {
        this.prdetails = prdetails;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
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

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(double qtyReceived) {
        this.qtyReceived = qtyReceived;
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

    public String getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(String retStatus) {
        this.retStatus = retStatus;
    }

    public PurchaseInvoiceDetails getPiItemId() {
        return piItemId;
    }

    public void setPiItemId(PurchaseInvoiceDetails piItemId) {
        this.piItemId = piItemId;
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
