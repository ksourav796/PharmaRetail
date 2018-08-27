/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posPojo;



import java.util.List;

/**
 *
 * @author admin
 */
public class CashPayment {
    private String paymentType;
    private double totalCPAmountRefunded;
    private double totalCPDiscount;
    private double totalCPAmountTendered;
    private double totalCPBeforDiscount;
    private List<MultiCashPayment> multiCashPaymentList;

    public List<MultiCashPayment> getMultiCashPaymentList() {
        return multiCashPaymentList;
    }

    public void setMultiCashPaymentList(List<MultiCashPayment> multiCashPaymentList) {
        this.multiCashPaymentList = multiCashPaymentList;
    }

    private List<CashPaymentList> cashPaymentLists;

    public List<CashPaymentList> getCashPaymentLists() {
        return cashPaymentLists;
    }

    public void setCashPaymentLists(List<CashPaymentList> cashPaymentLists) {
        this.cashPaymentLists = cashPaymentLists;
    }

    public double getTotalCPBeforDiscount() {
        return totalCPBeforDiscount;
    }

    public void setTotalCPBeforDiscount(double totalCPBeforDiscount) {
        this.totalCPBeforDiscount = totalCPBeforDiscount;
    }

//    private List<MultiCashPayment> multiCashPaymentList;
//
//    public List<MultiCashPayment> getMultiCashPaymentList() {
//        return multiCashPaymentList;
//    }
//
//    public void setMultiCashPaymentList(List<MultiCashPayment> multiCashPaymentList) {
//        this.multiCashPaymentList = multiCashPaymentList;
//    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getTotalCPAmountRefunded() {
        return totalCPAmountRefunded;
    }

    public void setTotalCPAmountRefunded(double totalCPAmountRefunded) {
        this.totalCPAmountRefunded = totalCPAmountRefunded;
    }

    public double getTotalCPDiscount() {
        return totalCPDiscount;
    }

    public void setTotalCPDiscount(double totalCPDiscount) {
        this.totalCPDiscount = totalCPDiscount;
    }

    public double getTotalCPAmountTendered() {
        return totalCPAmountTendered;
    }

    public void setTotalCPAmountTendered(double totalCPAmountTendered) {
        this.totalCPAmountTendered = totalCPAmountTendered;
    }

    


}
