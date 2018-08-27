package com.hyva.posretail.pos.posPojo;

import java.sql.Date;

public class StudentDatapojo {
    private Long studentFeeDetailsId;
    private String feeTypeName;
    private Double feeTypeAmount;
    private Double totalPayable;
    private Boolean checkboxstatus;
    private int noOfInstallments;
    private Double installmentsAmount;
    private String status;
    private Double dueAmount;
    private Double totalConfiguredFee;
    private Date dueDate;
    private String paymenttype;
    private String paidAmount;
    private String chequeNo;
    private Date paymentDate;
    private String bankName;
    private String ddNo;
    private Double payingAmount;

    public Long getStudentFeeDetailsId() {
        return studentFeeDetailsId;
    }

    public void setStudentFeeDetailsId(Long studentFeeDetailsId) {
        this.studentFeeDetailsId = studentFeeDetailsId;
    }

    public String getFeeTypeName() {
        return feeTypeName;
    }

    public void setFeeTypeName(String feeTypeName) {
        this.feeTypeName = feeTypeName;
    }

    public Double getFeeTypeAmount() {
        return feeTypeAmount;
    }

    public void setFeeTypeAmount(Double feeTypeAmount) {
        this.feeTypeAmount = feeTypeAmount;
    }

    public Double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(Double totalPayable) {
        this.totalPayable = totalPayable;
    }

    public Boolean getCheckboxstatus() {
        return checkboxstatus;
    }

    public void setCheckboxstatus(Boolean checkboxstatus) {
        this.checkboxstatus = checkboxstatus;
    }

    public int getNoOfInstallments() {
        return noOfInstallments;
    }

    public void setNoOfInstallments(int noOfInstallments) {
        this.noOfInstallments = noOfInstallments;
    }

    public Double getInstallmentsAmount() {
        return installmentsAmount;
    }

    public void setInstallmentsAmount(Double installmentsAmount) {
        this.installmentsAmount = installmentsAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(Double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public Double getTotalConfiguredFee() {
        return totalConfiguredFee;
    }

    public void setTotalConfiguredFee(Double totalConfiguredFee) {
        this.totalConfiguredFee = totalConfiguredFee;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDdNo() {
        return ddNo;
    }

    public void setDdNo(String ddNo) {
        this.ddNo = ddNo;
    }

    public Double getPayingAmount() {
        return payingAmount;
    }

    public void setPayingAmount(Double payingAmount) {
        this.payingAmount = payingAmount;
    }
}
