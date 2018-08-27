package com.hyva.posretail.pos.posPojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by azgar.h on 4/27/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemOPTaxDTO {
    private Long   TaxOPId;
    private String TaxCode;
    private String TaxName;
    private double Tax_Per;
    private String TaxDescription;
    private String linkedId;
    private String TaxGroup;
    private String TaxStatus;

    public Long getTaxOPId() {
        return TaxOPId;
    }

    public void setTaxOPId(Long taxOPId) {
        TaxOPId = taxOPId;
    }

    public String getTaxCode() {
        return TaxCode;
    }

    public void setTaxCode(String taxCode) {
        TaxCode = taxCode;
    }

    public String getTaxName() {
        return TaxName;
    }

    public void setTaxName(String taxName) {
        TaxName = taxName;
    }

    public double getTax_Per() {
        return Tax_Per;
    }

    public void setTax_Per(double tax_Per) {
        Tax_Per = tax_Per;
    }

    public String getTaxDescription() {
        return TaxDescription;
    }

    public void setTaxDescription(String taxDescription) {
        TaxDescription = taxDescription;
    }

    public String getLinkedId() {
        return linkedId;
    }

    public void setLinkedId(String linkedId) {
        this.linkedId = linkedId;
    }

    public String getTaxGroup() {
        return TaxGroup;
    }

    public void setTaxGroup(String taxGroup) {
        TaxGroup = taxGroup;
    }

    public String getTaxStatus() {
        return TaxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        TaxStatus = taxStatus;
    }
}
