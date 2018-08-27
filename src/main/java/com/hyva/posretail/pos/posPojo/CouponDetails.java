package com.hyva.posretail.pos.posPojo;

import java.util.List;

public class CouponDetails {
    private String paymentType;
    private double totalCOAmountRefunded;
    private double totalCODiscount;
    private double totalCOAmountTendered;
    private double totalCOBeforeDiscount;

    private List<MultiCouponList> couponPaymentList;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public List<MultiCouponList> getCouponPaymentList() {
        return couponPaymentList;
    }

    public void setCouponPaymentList(List<MultiCouponList> couponPaymentList) {
        this.couponPaymentList = couponPaymentList;
    }

    public double getTotalCOAmountRefunded() {
        return totalCOAmountRefunded;
    }

    public void setTotalCOAmountRefunded(double totalCOAmountRefunded) {
        this.totalCOAmountRefunded = totalCOAmountRefunded;
    }

    public double getTotalCODiscount() {
        return totalCODiscount;
    }

    public void setTotalCODiscount(double totalCODiscount) {
        this.totalCODiscount = totalCODiscount;
    }

    public double getTotalCOAmountTendered() {
        return totalCOAmountTendered;
    }

    public void setTotalCOAmountTendered(double totalCOAmountTendered) {
        this.totalCOAmountTendered = totalCOAmountTendered;
    }

    public double getTotalCOBeforeDiscount() {
        return totalCOBeforeDiscount;
    }

    public void setTotalCOBeforeDiscount(double totalCOBeforeDiscount) {
        this.totalCOBeforeDiscount = totalCOBeforeDiscount;
    }
}
