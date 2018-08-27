package com.hyva.posretail.pos.posPojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by azgar.h on 7/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedAccount {
    public String accountName;
    private long accountId;
    private double unitPrice;
    private double qty;
    private double returnQty;
    private double remainingQty;
    private double amtexclusivetax;
    private long    taxid;
    private double taxpercent;
    private double taxamt;
    private double amtinclusivetax;
    private double discountAmt;
    private String itemDescription;
    private String taxName;
    private String taxCode;
    public String serializableItemId;
    public String serializableNumbers;
    public String itemCode;
    private double makingCharge;
    private double actualWeight;
    private double taxPercentageSplit;
    private double cgstsgstsplitvalues;

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public double getCgstsgstsplitvalues() {
        return cgstsgstsplitvalues;
    }

    public void setCgstsgstsplitvalues(double cgstsgstsplitvalues) {
        this.cgstsgstsplitvalues = cgstsgstsplitvalues;
    }

    public double getTaxPercentageSplit() {

        return taxPercentageSplit;
    }

    public void setTaxPercentageSplit(double taxPercentageSplit) {
        this.taxPercentageSplit = taxPercentageSplit;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(double returnQty) {
        this.returnQty = returnQty;
    }

    public double getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(double remainingQty) {
        this.remainingQty = remainingQty;
    }

    public double getAmtexclusivetax() {
        return amtexclusivetax;
    }

    public void setAmtexclusivetax(double amtexclusivetax) {
        this.amtexclusivetax = amtexclusivetax;
    }

    public long getTaxid() {
        return taxid;
    }

    public void setTaxid(long taxid) {
        this.taxid = taxid;
    }

    public double getTaxpercent() {
        return taxpercent;
    }

    public void setTaxpercent(double taxpercent) {
        this.taxpercent = taxpercent;
    }

    public double getTaxamt() {
        return taxamt;
    }

    public void setTaxamt(double taxamt) {
        this.taxamt = taxamt;
    }

    public double getAmtinclusivetax() {
        return amtinclusivetax;
    }

    public void setAmtinclusivetax(double amtinclusivetax) {
        this.amtinclusivetax = amtinclusivetax;
    }

    public double getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(double discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getSerializableItemId() {
        return serializableItemId;
    }

    public void setSerializableItemId(String serializableItemId) {
        this.serializableItemId = serializableItemId;
    }

    public String getSerializableNumbers() {
        return serializableNumbers;
    }

    public void setSerializableNumbers(String serializableNumbers) {
        this.serializableNumbers = serializableNumbers;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getMakingCharge() {
        return makingCharge;
    }

    public void setMakingCharge(double makingCharge) {
        this.makingCharge = makingCharge;
    }

    public double getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(double actualWeight) {
        this.actualWeight = actualWeight;
    }
}
