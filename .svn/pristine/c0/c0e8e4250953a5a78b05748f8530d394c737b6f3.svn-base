
package com.hyva.posretail.pos.posEntities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Admin
 */
@Entity

public class SalesReturnDetails implements Serializable {
    //    @OneToOne
//    protected Tax tax;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int srItemId;
    //    @ManyToOne
//    @JoinColumn(name = "siitemId")
//    private DirectSalesInvoiceDetailsTemplate siitemId;
//    @ManyToOne
//    @JoinColumn(name = "scProducts")
//    private SalesReturn scProducts;
//    @ManyToOne
//    @JoinColumn(name = "itemId")
//    private Item itemId;
    private String srNo;
    private String siNo;
    private String itemName;
    private String taxName;
    private String itemCode;
    private String itemDesc;
    private double price;
    private double qtyOrdered;
    private double discPercent;
    private double itemAmountExcTax;
    private double itemTaxPer;
    private double itemTax;
    private double itemAmountIncTax;
    private String status;
    private double qtyReturned;
    private double cessper;
    private double cessTaxAmt;

    public int getSrItemId() {
        return srItemId;
    }

    public void setSrItemId(int srItemId) {
        this.srItemId = srItemId;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getSiNo() {
        return siNo;
    }

    public void setSiNo(String siNo) {
        this.siNo = siNo;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getQtyReturned() {
        return qtyReturned;
    }

    public void setQtyReturned(double qtyReturned) {
        this.qtyReturned = qtyReturned;
    }

    public double getCessper() {
        return cessper;
    }

    public void setCessper(double cessper) {
        this.cessper = cessper;
    }

    public double getCessTaxAmt() {
        return cessTaxAmt;
    }

    public void setCessTaxAmt(double cessTaxAmt) {
        this.cessTaxAmt = cessTaxAmt;
    }
}
