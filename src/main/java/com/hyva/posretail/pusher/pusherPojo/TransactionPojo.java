package com.hyva.posretail.pusher.pusherPojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionPojo extends AbstractResponse implements Serializable {
    private List<SelectedItem> selectedItemsList = new ArrayList<>();

//    private List<MultiPartialPayment> multiPartialPaymentList = new ArrayList<>();
//    private CompanyInfo companydupData;
//    private CompanyInfoDto companyData;
//    private CashPayment cashPayment;
//    private CreditCardPayment creditPayment;
//    private VoucherPayment voucherPayment;
//    private MultiPayment multiPayment;
//    private BankPayment bankPayment;
//    private RedeemPoints redeemPayment;
//    private CustomerDTO customer;



    private String operation;
    private float hiPosServiceCharge;
    private String paymentType;
    private double totalCheckOutamt;
    private double totalTaxAmt;
    private double cessTotalTaxAmt;
    private int itemCount;
    private long customerId;
    private String taxType;
    private long siid;
    private String customerEmail;
    private double totalServiceCharge;
    private String siNo;
    private String srlnNo;
    private String cutomerName;

    private double balanceAmount;

    private double discountAmount;
    private double totalTenderedAmount;
    private String userName;
    private  String printType;
    private String billToAddress;
    private String shipToAddress;
    private String email;
    private String phoneNumber;
    private String inventoryAddress;
    private String inventoryContactNo;
    private String inventoryEmail;
    private String inventoryFax;
    private String advancepayment;
    private String formNo;
    private String totalCashPymtAmtReturned;
    private String totalChequePymtAmtReturned;
    private String totalVoucherPymtAmtReturned;
    private String chequeNumber;
    private String voucherNumber;
    private String salesOrderNo;
    private String salesDeliveryOrderNO;
    private double totalActualWeight;
    private double totalSellableWeight;
    private String from_reg;
    private String to_reg;
    private String user_id;
    private String  typeDoc;
    private String  type_flag;
    private String recieptFooter;
    private String companyLogoPath;
    private String cmpyLocation;
    private String custLocation;
    private int termsId;
    private String dateOfInvoice;
    private String agentIdOfInvoice;
    private String projectIdOfInvoice;
    private String termCondIdOfInvoice;
    private String currencyIdOfInvoice;
    private String exchangeRateIdOfInvoice;
    private double totalCashPayment;
    private double totalVoucherPayment;
    private double totalCardPayment;
    private String customerAddress;
    private String customerState;
    private String customerGst;
    private String locationAddress;
    private String locationState;
    private String locationGst;
    private BigDecimal ARBalance;
    private String salesOrderId;
    private String salesOrderDetailsId;
    private String memo;
    private String returnReason;
    private String totalRemaininBalance;
    private Date shippingDate;
    private String shippingDateString;
    private String shippingReferenceNo;
    private String referenceNo;
    private String shippingmethodId;
    private String salesQuotationId;
    private String proFormaId;
    private String proFormaDetailsId;
    private double totalVoucherAmt;
    private double totalCreditCardAmt;
    private double totalCashAmt;
    private String patientId;
    private String serviceCharge;
    private String agentName;
    private String exchangerateValue;
    private String tc_value;
    private String salesQuotationDetailsId;
    private String otherContactId;
    private Date invoiceDate;
    private double invoiceAmt;
    private double incrementAmt;
    private String employeeId;
    private double tokenNo;
    private String table;
    private String invoiceNo;
    private Date formDate;
    private String projectName;
    private String shippingMethodName;
    private Long invokeOrderId;
    private String invokeOrderName;
    private String generatedVoucherNo;
    private String customerPo;
    private String serviceDeliveryId;
    private String exportInvoice;
    private double invokeRemaningQty;
    private double amtToBePaid;
    private String siStatus;
    private boolean checkForHoldStock;
    private String emailSub;
    private String emailBody;
    private String emailInvoice;
    private String discountType;
    private String piStatus;
    private String customerName;
    private String custNotiId;


    private long supplierId;
    private long agentId;
    private long exchangeRateId;
    private double apBalance;
    private long piid;
    private String supplierEmail;
    private String piNo;
    private String prlnNo;
    private String supplierName;
    private String supplierInvNo;
    private String cmpyLoc;
    private String suppLoc;
    private  double amountReturned;
    private String selfBuildInvoice;
    private String supplierAddress;
    private String supplierState;
    private String supplierGst;
    private String locationName;
    private String locationSupplierName;
    private String exchangerateId;
    private String currencyId;
    private String projectId;
    private String termsandConditionsId;
    private String purchaseQuotationId;
    private String purchaseQuotationDetailsId;
    private Long poId;
    private String purchaseOrderId;
    private Long receiveItemId;
    private Date date;
    private String typeOfInvoice;
    private String cashAmtForReturn;
    private String chequeAmtForReturn;
    private double dutyAmount;
    private double frightCharges;
    private String otherCharges;
    private Long transactionId;
    private Long notificationId;
    private String totaltax;
    private String fromCompname;
    private String toCompname;


    public List<SelectedItem> getSelectedItemsList() {
        return selectedItemsList;
    }

    public void setSelectedItemsList(List<SelectedItem> selectedItemsList) {
        this.selectedItemsList = selectedItemsList;
    }



    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }


    public float getHiPosServiceCharge() {
        return hiPosServiceCharge;
    }

    public void setHiPosServiceCharge(float hiPosServiceCharge) {
        this.hiPosServiceCharge = hiPosServiceCharge;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getTotalCheckOutamt() {
        return totalCheckOutamt;
    }

    public void setTotalCheckOutamt(double totalCheckOutamt) {
        this.totalCheckOutamt = totalCheckOutamt;
    }

    public double getTotalTaxAmt() {
        return totalTaxAmt;
    }

    public void setTotalTaxAmt(double totalTaxAmt) {
        this.totalTaxAmt = totalTaxAmt;
    }

    public double getCessTotalTaxAmt() {
        return cessTotalTaxAmt;
    }

    public void setCessTotalTaxAmt(double cessTotalTaxAmt) {
        this.cessTotalTaxAmt = cessTotalTaxAmt;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public long getSiid() {
        return siid;
    }

    public void setSiid(long siid) {
        this.siid = siid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public double getTotalServiceCharge() {
        return totalServiceCharge;
    }

    public void setTotalServiceCharge(double totalServiceCharge) {
        this.totalServiceCharge = totalServiceCharge;
    }

    public String getSiNo() {
        return siNo;
    }

    public void setSiNo(String siNo) {
        this.siNo = siNo;
    }

    public String getSrlnNo() {
        return srlnNo;
    }

    public void setSrlnNo(String srlnNo) {
        this.srlnNo = srlnNo;
    }

    public String getCutomerName() {
        return cutomerName;
    }

    public void setCutomerName(String cutomerName) {
        this.cutomerName = cutomerName;
    }


    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }


    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getTotalTenderedAmount() {
        return totalTenderedAmount;
    }

    public void setTotalTenderedAmount(double totalTenderedAmount) {
        this.totalTenderedAmount = totalTenderedAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getBillToAddress() {
        return billToAddress;
    }

    public void setBillToAddress(String billToAddress) {
        this.billToAddress = billToAddress;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInventoryAddress() {
        return inventoryAddress;
    }

    public void setInventoryAddress(String inventoryAddress) {
        this.inventoryAddress = inventoryAddress;
    }

    public String getInventoryContactNo() {
        return inventoryContactNo;
    }

    public void setInventoryContactNo(String inventoryContactNo) {
        this.inventoryContactNo = inventoryContactNo;
    }

    public String getInventoryEmail() {
        return inventoryEmail;
    }

    public void setInventoryEmail(String inventoryEmail) {
        this.inventoryEmail = inventoryEmail;
    }

    public String getInventoryFax() {
        return inventoryFax;
    }

    public void setInventoryFax(String inventoryFax) {
        this.inventoryFax = inventoryFax;
    }

    public String getAdvancepayment() {
        return advancepayment;
    }

    public void setAdvancepayment(String advancepayment) {
        this.advancepayment = advancepayment;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getTotalCashPymtAmtReturned() {
        return totalCashPymtAmtReturned;
    }

    public void setTotalCashPymtAmtReturned(String totalCashPymtAmtReturned) {
        this.totalCashPymtAmtReturned = totalCashPymtAmtReturned;
    }

    public String getTotalChequePymtAmtReturned() {
        return totalChequePymtAmtReturned;
    }

    public void setTotalChequePymtAmtReturned(String totalChequePymtAmtReturned) {
        this.totalChequePymtAmtReturned = totalChequePymtAmtReturned;
    }

    public String getTotalVoucherPymtAmtReturned() {
        return totalVoucherPymtAmtReturned;
    }

    public void setTotalVoucherPymtAmtReturned(String totalVoucherPymtAmtReturned) {
        this.totalVoucherPymtAmtReturned = totalVoucherPymtAmtReturned;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getSalesDeliveryOrderNO() {
        return salesDeliveryOrderNO;
    }

    public void setSalesDeliveryOrderNO(String salesDeliveryOrderNO) {
        this.salesDeliveryOrderNO = salesDeliveryOrderNO;
    }

    public double getTotalActualWeight() {
        return totalActualWeight;
    }

    public void setTotalActualWeight(double totalActualWeight) {
        this.totalActualWeight = totalActualWeight;
    }

    public double getTotalSellableWeight() {
        return totalSellableWeight;
    }

    public void setTotalSellableWeight(double totalSellableWeight) {
        this.totalSellableWeight = totalSellableWeight;
    }

    public String getFrom_reg() {
        return from_reg;
    }

    public void setFrom_reg(String from_reg) {
        this.from_reg = from_reg;
    }

    public String getTo_reg() {
        return to_reg;
    }

    public void setTo_reg(String to_reg) {
        this.to_reg = to_reg;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTypeDoc() {
        return typeDoc;
    }

    public void setTypeDoc(String typeDoc) {
        this.typeDoc = typeDoc;
    }

    public String getType_flag() {
        return type_flag;
    }

    public void setType_flag(String type_flag) {
        this.type_flag = type_flag;
    }

    public String getRecieptFooter() {
        return recieptFooter;
    }

    public void setRecieptFooter(String recieptFooter) {
        this.recieptFooter = recieptFooter;
    }

    public String getCompanyLogoPath() {
        return companyLogoPath;
    }

    public void setCompanyLogoPath(String companyLogoPath) {
        this.companyLogoPath = companyLogoPath;
    }

    public String getCmpyLocation() {
        return cmpyLocation;
    }

    public void setCmpyLocation(String cmpyLocation) {
        this.cmpyLocation = cmpyLocation;
    }

    public String getCustLocation() {
        return custLocation;
    }

    public void setCustLocation(String custLocation) {
        this.custLocation = custLocation;
    }

    public int getTermsId() {
        return termsId;
    }

    public void setTermsId(int termsId) {
        this.termsId = termsId;
    }

    public String getDateOfInvoice() {
        return dateOfInvoice;
    }

    public void setDateOfInvoice(String dateOfInvoice) {
        this.dateOfInvoice = dateOfInvoice;
    }

    public String getAgentIdOfInvoice() {
        return agentIdOfInvoice;
    }

    public void setAgentIdOfInvoice(String agentIdOfInvoice) {
        this.agentIdOfInvoice = agentIdOfInvoice;
    }

    public String getProjectIdOfInvoice() {
        return projectIdOfInvoice;
    }

    public void setProjectIdOfInvoice(String projectIdOfInvoice) {
        this.projectIdOfInvoice = projectIdOfInvoice;
    }

    public String getTermCondIdOfInvoice() {
        return termCondIdOfInvoice;
    }

    public void setTermCondIdOfInvoice(String termCondIdOfInvoice) {
        this.termCondIdOfInvoice = termCondIdOfInvoice;
    }

    public String getCurrencyIdOfInvoice() {
        return currencyIdOfInvoice;
    }

    public void setCurrencyIdOfInvoice(String currencyIdOfInvoice) {
        this.currencyIdOfInvoice = currencyIdOfInvoice;
    }

    public String getExchangeRateIdOfInvoice() {
        return exchangeRateIdOfInvoice;
    }

    public void setExchangeRateIdOfInvoice(String exchangeRateIdOfInvoice) {
        this.exchangeRateIdOfInvoice = exchangeRateIdOfInvoice;
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

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCustomerGst() {
        return customerGst;
    }

    public void setCustomerGst(String customerGst) {
        this.customerGst = customerGst;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationState() {
        return locationState;
    }

    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    public String getLocationGst() {
        return locationGst;
    }

    public void setLocationGst(String locationGst) {
        this.locationGst = locationGst;
    }

    public BigDecimal getARBalance() {
        return ARBalance;
    }

    public void setARBalance(BigDecimal ARBalance) {
        this.ARBalance = ARBalance;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getSalesOrderDetailsId() {
        return salesOrderDetailsId;
    }

    public void setSalesOrderDetailsId(String salesOrderDetailsId) {
        this.salesOrderDetailsId = salesOrderDetailsId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getTotalRemaininBalance() {
        return totalRemaininBalance;
    }

    public void setTotalRemaininBalance(String totalRemaininBalance) {
        this.totalRemaininBalance = totalRemaininBalance;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getShippingDateString() {
        return shippingDateString;
    }

    public void setShippingDateString(String shippingDateString) {
        this.shippingDateString = shippingDateString;
    }

    public String getShippingReferenceNo() {
        return shippingReferenceNo;
    }

    public void setShippingReferenceNo(String shippingReferenceNo) {
        this.shippingReferenceNo = shippingReferenceNo;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getShippingmethodId() {
        return shippingmethodId;
    }

    public void setShippingmethodId(String shippingmethodId) {
        this.shippingmethodId = shippingmethodId;
    }

    public String getSalesQuotationId() {
        return salesQuotationId;
    }

    public void setSalesQuotationId(String salesQuotationId) {
        this.salesQuotationId = salesQuotationId;
    }

    public String getProFormaId() {
        return proFormaId;
    }

    public void setProFormaId(String proFormaId) {
        this.proFormaId = proFormaId;
    }

    public String getProFormaDetailsId() {
        return proFormaDetailsId;
    }

    public void setProFormaDetailsId(String proFormaDetailsId) {
        this.proFormaDetailsId = proFormaDetailsId;
    }

    public double getTotalVoucherAmt() {
        return totalVoucherAmt;
    }

    public void setTotalVoucherAmt(double totalVoucherAmt) {
        this.totalVoucherAmt = totalVoucherAmt;
    }

    public double getTotalCreditCardAmt() {
        return totalCreditCardAmt;
    }

    public void setTotalCreditCardAmt(double totalCreditCardAmt) {
        this.totalCreditCardAmt = totalCreditCardAmt;
    }

    public double getTotalCashAmt() {
        return totalCashAmt;
    }

    public void setTotalCashAmt(double totalCashAmt) {
        this.totalCashAmt = totalCashAmt;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getExchangerateValue() {
        return exchangerateValue;
    }

    public void setExchangerateValue(String exchangerateValue) {
        this.exchangerateValue = exchangerateValue;
    }

    public String getTc_value() {
        return tc_value;
    }

    public void setTc_value(String tc_value) {
        this.tc_value = tc_value;
    }

    public String getSalesQuotationDetailsId() {
        return salesQuotationDetailsId;
    }

    public void setSalesQuotationDetailsId(String salesQuotationDetailsId) {
        this.salesQuotationDetailsId = salesQuotationDetailsId;
    }

    public String getOtherContactId() {
        return otherContactId;
    }

    public void setOtherContactId(String otherContactId) {
        this.otherContactId = otherContactId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getInvoiceAmt() {
        return invoiceAmt;
    }

    public void setInvoiceAmt(double invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }

    public double getIncrementAmt() {
        return incrementAmt;
    }

    public void setIncrementAmt(double incrementAmt) {
        this.incrementAmt = incrementAmt;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(double tokenNo) {
        this.tokenNo = tokenNo;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getFormDate() {
        return formDate;
    }

    public void setFormDate(Date formDate) {
        this.formDate = formDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getShippingMethodName() {
        return shippingMethodName;
    }

    public void setShippingMethodName(String shippingMethodName) {
        this.shippingMethodName = shippingMethodName;
    }

    public Long getInvokeOrderId() {
        return invokeOrderId;
    }

    public void setInvokeOrderId(Long invokeOrderId) {
        this.invokeOrderId = invokeOrderId;
    }

    public String getInvokeOrderName() {
        return invokeOrderName;
    }

    public void setInvokeOrderName(String invokeOrderName) {
        this.invokeOrderName = invokeOrderName;
    }

    public String getGeneratedVoucherNo() {
        return generatedVoucherNo;
    }

    public void setGeneratedVoucherNo(String generatedVoucherNo) {
        this.generatedVoucherNo = generatedVoucherNo;
    }

    public String getCustomerPo() {
        return customerPo;
    }

    public void setCustomerPo(String customerPo) {
        this.customerPo = customerPo;
    }

    public String getServiceDeliveryId() {
        return serviceDeliveryId;
    }

    public void setServiceDeliveryId(String serviceDeliveryId) {
        this.serviceDeliveryId = serviceDeliveryId;
    }

    public String getExportInvoice() {
        return exportInvoice;
    }

    public void setExportInvoice(String exportInvoice) {
        this.exportInvoice = exportInvoice;
    }

    public double getInvokeRemaningQty() {
        return invokeRemaningQty;
    }

    public void setInvokeRemaningQty(double invokeRemaningQty) {
        this.invokeRemaningQty = invokeRemaningQty;
    }

    public double getAmtToBePaid() {
        return amtToBePaid;
    }

    public void setAmtToBePaid(double amtToBePaid) {
        this.amtToBePaid = amtToBePaid;
    }

    public String getSiStatus() {
        return siStatus;
    }

    public void setSiStatus(String siStatus) {
        this.siStatus = siStatus;
    }

    public boolean isCheckForHoldStock() {
        return checkForHoldStock;
    }

    public void setCheckForHoldStock(boolean checkForHoldStock) {
        this.checkForHoldStock = checkForHoldStock;
    }

    public String getEmailSub() {
        return emailSub;
    }

    public void setEmailSub(String emailSub) {
        this.emailSub = emailSub;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getEmailInvoice() {
        return emailInvoice;
    }

    public void setEmailInvoice(String emailInvoice) {
        this.emailInvoice = emailInvoice;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getPiStatus() {
        return piStatus;
    }

    public void setPiStatus(String piStatus) {
        this.piStatus = piStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustNotiId() {
        return custNotiId;
    }

    public void setCustNotiId(String custNotiId) {
        this.custNotiId = custNotiId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public long getExchangeRateId() {
        return exchangeRateId;
    }

    public void setExchangeRateId(long exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
    }

    public double getApBalance() {
        return apBalance;
    }

    public void setApBalance(double apBalance) {
        this.apBalance = apBalance;
    }

    public long getPiid() {
        return piid;
    }

    public void setPiid(long piid) {
        this.piid = piid;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getPiNo() {
        return piNo;
    }

    public void setPiNo(String piNo) {
        this.piNo = piNo;
    }

    public String getPrlnNo() {
        return prlnNo;
    }

    public void setPrlnNo(String prlnNo) {
        this.prlnNo = prlnNo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }



    public String getSupplierInvNo() {
        return supplierInvNo;
    }

    public void setSupplierInvNo(String supplierInvNo) {
        this.supplierInvNo = supplierInvNo;
    }

    public String getCmpyLoc() {
        return cmpyLoc;
    }

    public void setCmpyLoc(String cmpyLoc) {
        this.cmpyLoc = cmpyLoc;
    }

    public String getSuppLoc() {
        return suppLoc;
    }

    public void setSuppLoc(String suppLoc) {
        this.suppLoc = suppLoc;
    }

    public double getAmountReturned() {
        return amountReturned;
    }

    public void setAmountReturned(double amountReturned) {
        this.amountReturned = amountReturned;
    }

    public String getSelfBuildInvoice() {
        return selfBuildInvoice;
    }

    public void setSelfBuildInvoice(String selfBuildInvoice) {
        this.selfBuildInvoice = selfBuildInvoice;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierState() {
        return supplierState;
    }

    public void setSupplierState(String supplierState) {
        this.supplierState = supplierState;
    }

    public String getSupplierGst() {
        return supplierGst;
    }

    public void setSupplierGst(String supplierGst) {
        this.supplierGst = supplierGst;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationSupplierName() {
        return locationSupplierName;
    }

    public void setLocationSupplierName(String locationSupplierName) {
        this.locationSupplierName = locationSupplierName;
    }

    public String getExchangerateId() {
        return exchangerateId;
    }

    public void setExchangerateId(String exchangerateId) {
        this.exchangerateId = exchangerateId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTermsandConditionsId() {
        return termsandConditionsId;
    }

    public void setTermsandConditionsId(String termsandConditionsId) {
        this.termsandConditionsId = termsandConditionsId;
    }

    public String getPurchaseQuotationId() {
        return purchaseQuotationId;
    }

    public void setPurchaseQuotationId(String purchaseQuotationId) {
        this.purchaseQuotationId = purchaseQuotationId;
    }

    public String getPurchaseQuotationDetailsId() {
        return purchaseQuotationDetailsId;
    }

    public void setPurchaseQuotationDetailsId(String purchaseQuotationDetailsId) {
        this.purchaseQuotationDetailsId = purchaseQuotationDetailsId;
    }

    public Long getPoId() {
        return poId;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getReceiveItemId() {
        return receiveItemId;
    }

    public void setReceiveItemId(Long receiveItemId) {
        this.receiveItemId = receiveItemId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTypeOfInvoice() {
        return typeOfInvoice;
    }

    public void setTypeOfInvoice(String typeOfInvoice) {
        this.typeOfInvoice = typeOfInvoice;
    }

    public String getCashAmtForReturn() {
        return cashAmtForReturn;
    }

    public void setCashAmtForReturn(String cashAmtForReturn) {
        this.cashAmtForReturn = cashAmtForReturn;
    }

    public String getChequeAmtForReturn() {
        return chequeAmtForReturn;
    }

    public void setChequeAmtForReturn(String chequeAmtForReturn) {
        this.chequeAmtForReturn = chequeAmtForReturn;
    }

    public double getDutyAmount() {
        return dutyAmount;
    }

    public void setDutyAmount(double dutyAmount) {
        this.dutyAmount = dutyAmount;
    }

    public double getFrightCharges() {
        return frightCharges;
    }

    public void setFrightCharges(double frightCharges) {
        this.frightCharges = frightCharges;
    }

    public String getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(String otherCharges) {
        this.otherCharges = otherCharges;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getTotaltax() {
        return totaltax;
    }

    public void setTotaltax(String totaltax) {
        this.totaltax = totaltax;
    }

    public String getFromCompname() {
        return fromCompname;
    }

    public void setFromCompname(String fromCompname) {
        this.fromCompname = fromCompname;
    }

    public String getToCompname() {
        return toCompname;
    }

    public void setToCompname(String toCompname) {
        this.toCompname = toCompname;
    }
}
