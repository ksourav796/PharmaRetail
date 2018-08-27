package com.hyva.posretail.pos.posPojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by azgar.h on 4/24/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemCategoryDTO {
    private Long   itemCategoryId;

    private String itemCategoryName;
    private String itemCategoryDesc;
    private String ledgerINV;
    private String ledgerCOGS;
    private String ledgerIncome;
    private String ledgerAdjustment;
    private String defaultType;
    private String itemCode;
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }

    public Long getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }


    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getItemCategoryDesc() {
        return itemCategoryDesc;
    }

    public void setItemCategoryDesc(String itemCategoryDesc) {
        this.itemCategoryDesc = itemCategoryDesc;
    }

    public String getLedgerINV() {
        return ledgerINV;
    }

    public void setLedgerINV(String ledgerINV) {
        this.ledgerINV = ledgerINV;
    }

    public String getLedgerCOGS() {
        return ledgerCOGS;
    }

    public void setLedgerCOGS(String ledgerCOGS) {
        this.ledgerCOGS = ledgerCOGS;
    }

    public String getLedgerIncome() {
        return ledgerIncome;
    }

    public void setLedgerIncome(String ledgerIncome) {
        this.ledgerIncome = ledgerIncome;
    }

    public String getLedgerAdjustment() {
        return ledgerAdjustment;
    }

    public void setLedgerAdjustment(String ledgerAdjustment) {
        this.ledgerAdjustment = ledgerAdjustment;
    }
}
