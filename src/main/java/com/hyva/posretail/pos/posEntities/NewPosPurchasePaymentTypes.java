package com.hyva.posretail.pos.posEntities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class NewPosPurchasePaymentTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long paymenetId;
    private String voucherPayment;
    private String cardPayment;
    private double totalCashPayment;
    private double totalVoucherPayment;
    private double totalCardPayment;
    private double totalAmount;
    private String duplicatePrint;
    private String pino;
    private String supplierName;
//    @OneToOne
//    private PurchaseInvoice purchaseInvoice;
    private int count;
    private double totalTaxAmount;
    private double roundingAdjustment;
//    @OneToOne
//    private Supplier supplier;
    private transient String registerNo;
    private transient String typePrefix;
    private transient String nextref;
    private String fileName;
    private String bankPayment;
    private double totalBankAmt;
    private double amountReturn;
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    private double totalCreditNote;
    private double totalCoupon;
    private double totalCheque;
    private double totalGiftCard;
    private double totaleWallet;
    private String creditNotePayment;
    private String couponPayment;
    private String chequePayment;
    private String ewalletPayment;
    private String giftCardPaymnet;
    private String cashPayment;

    public String getPino() {
        return pino;
    }

    public void setPino(String pino) {
        this.pino = pino;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getPaymenetId() {
        return paymenetId;
    }

    public void setPaymenetId(Long paymenetId) {
        this.paymenetId = paymenetId;
    }

    public String getVoucherPayment() {
        return voucherPayment;
    }

    public void setVoucherPayment(String voucherPayment) {
        this.voucherPayment = voucherPayment;
    }

    public String getCardPayment() {
        return cardPayment;
    }

    public void setCardPayment(String cardPayment) {
        this.cardPayment = cardPayment;
    }

    public double getTotalCashPayment() {
        return totalCashPayment;
    }

    public void setTotalCashPayment(double totalCashPayment) {
        this.totalCashPayment = totalCashPayment;
    }

    public double getTotalVoucherPayment() {
        return totalVoucherPayment;
    }

    public void setTotalVoucherPayment(double totalVoucherPayment) {
        this.totalVoucherPayment = totalVoucherPayment;
    }

    public double getTotalCardPayment() {
        return totalCardPayment;
    }

    public void setTotalCardPayment(double totalCardPayment) {
        this.totalCardPayment = totalCardPayment;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDuplicatePrint() {
        return duplicatePrint;
    }

    public void setDuplicatePrint(String duplicatePrint) {
        this.duplicatePrint = duplicatePrint;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public double getRoundingAdjustment() {
        return roundingAdjustment;
    }

    public void setRoundingAdjustment(double roundingAdjustment) {
        this.roundingAdjustment = roundingAdjustment;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getTypePrefix() {
        return typePrefix;
    }

    public void setTypePrefix(String typePrefix) {
        this.typePrefix = typePrefix;
    }

    public String getNextref() {
        return nextref;
    }

    public void setNextref(String nextref) {
        this.nextref = nextref;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBankPayment() {
        return bankPayment;
    }

    public void setBankPayment(String bankPayment) {
        this.bankPayment = bankPayment;
    }

    public double getTotalBankAmt() {
        return totalBankAmt;
    }

    public void setTotalBankAmt(double totalBankAmt) {
        this.totalBankAmt = totalBankAmt;
    }

    public double getAmountReturn() {
        return amountReturn;
    }

    public void setAmountReturn(double amountReturn) {
        this.amountReturn = amountReturn;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getTotalCreditNote() {
        return totalCreditNote;
    }

    public void setTotalCreditNote(double totalCreditNote) {
        this.totalCreditNote = totalCreditNote;
    }

    public double getTotalCoupon() {
        return totalCoupon;
    }

    public void setTotalCoupon(double totalCoupon) {
        this.totalCoupon = totalCoupon;
    }

    public double getTotalCheque() {
        return totalCheque;
    }

    public void setTotalCheque(double totalCheque) {
        this.totalCheque = totalCheque;
    }

    public double getTotalGiftCard() {
        return totalGiftCard;
    }

    public void setTotalGiftCard(double totalGiftCard) {
        this.totalGiftCard = totalGiftCard;
    }

    public double getTotaleWallet() {
        return totaleWallet;
    }

    public void setTotaleWallet(double totaleWallet) {
        this.totaleWallet = totaleWallet;
    }

    public String getCreditNotePayment() {
        return creditNotePayment;
    }

    public void setCreditNotePayment(String creditNotePayment) {
        this.creditNotePayment = creditNotePayment;
    }

    public String getCouponPayment() {
        return couponPayment;
    }

    public void setCouponPayment(String couponPayment) {
        this.couponPayment = couponPayment;
    }

    public String getChequePayment() {
        return chequePayment;
    }

    public void setChequePayment(String chequePayment) {
        this.chequePayment = chequePayment;
    }

    public String getEwalletPayment() {
        return ewalletPayment;
    }

    public void setEwalletPayment(String ewalletPayment) {
        this.ewalletPayment = ewalletPayment;
    }

    public String getGiftCardPaymnet() {
        return giftCardPaymnet;
    }

    public void setGiftCardPaymnet(String giftCardPaymnet) {
        this.giftCardPaymnet = giftCardPaymnet;
    }

    public String getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(String cashPayment) {
        this.cashPayment = cashPayment;
    }
}
