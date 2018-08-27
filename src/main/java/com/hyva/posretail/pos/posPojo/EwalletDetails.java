package com.hyva.posretail.pos.posPojo;

public class EwalletDetails {
    private double eWalletAmt;
    private String ewalletMobile;
    private String ewalletCode;

    public double geteWalletAmt() {
        return eWalletAmt;
    }

    public void seteWalletAmt(double eWalletAmt) {
        this.eWalletAmt = eWalletAmt;
    }

    public String getEwalletMobile() {
        return ewalletMobile;
    }

    public void setEwalletMobile(String ewalletMobile) {
        this.ewalletMobile = ewalletMobile;
    }

    public String getEwalletCode() {
        return ewalletCode;
    }

    public void setEwalletCode(String ewalletCode) {
        this.ewalletCode = ewalletCode;
    }
}
