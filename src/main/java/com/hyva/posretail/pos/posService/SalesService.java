package com.hyva.posretail.pos.posService;


import com.google.gson.Gson;
import com.hyva.posretail.pos.posEntities.*;
import com.hyva.posretail.pos.posMapper.PosItemMapper;
import com.hyva.posretail.pos.posPojo.*;
import com.hyva.posretail.pos.posRespositories.*;
import com.hyva.posretail.pusher.pusherPojo.SelectedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;

import java.util.*;

import java.util.stream.Collectors;

@Component
public class SalesService {
    @Autowired
    SalesInvoiceRepository salesInvoiceRepository;
    @Autowired
    SalesInvoiceDetailsRepository salesInvoiceDetailsRepository;
    @Autowired
    NewPosPaymentRepository newPosPaymentRepository;
    @Autowired
    PosItemRepository itemRepository;
    @Autowired
    PosTaxRepository taxRepository;
    @Autowired
    PosFormSetupRepository formSetupRepository;
    @Autowired
    PosCustomerRepository customerRepository;
    @Autowired
    PosService posService;
    @Autowired
    SalesReturnRepository salesReturnRepository;
    @Autowired
    SalesReturnDetailsRepository salesReturnDetailsRepository;

    private List<DirectSalesInvoiceDetailsTemplate> createBillingPOSSIDetails(RetailDTO retailDTO, DirectSalesInvoiceTemplate salesInvoice) {
        List<DirectSalesInvoiceDetailsTemplate> sidList = new ArrayList<>();
        for (SelectedItem selectedItem : retailDTO.getSelectedItemsList()) {
            DirectSalesInvoiceDetailsTemplate sid = new DirectSalesInvoiceDetailsTemplate();
            // TODO
            sid.setsINo(salesInvoice.getSiNo());
            sid.setsINo(salesInvoice.getSiNo());
            Item item = itemRepository.findByItemName(selectedItem.getItemName());
            sid.setItemName(item.getItemName());
            if(item!=null) {
                item.setStock(item.getStock() - selectedItem.getQty());
                itemRepository.save(item);
            }
            sid.setItemDesc(selectedItem.getItemDescription());
            sid.setItemCode(item.getItemCode());
            sid.setPrice(selectedItem.getUnitPrice());
            sid.setQtyOrdered(selectedItem.getQty());
            sid.setQtySent(selectedItem.getQty());
            sid.setQtyRemain(selectedItem.getQty());
            sid.setDiscPercent(0.00);
            sid.setItemAmountExcTax(selectedItem.getAmtexclusivetax());
            Tax tax = taxRepository.findByTaxGroupAndTaxName("SALES/OUTPUT",selectedItem.getTaxName());
            if (tax != null) {
                sid.setTaxName(tax.getTaxName());
                sid.setItemTaxPer(tax.getTax_Per());
            }
            sid.setItemTax(selectedItem.getTaxamt());
            sid.setItemAmountIncTax(selectedItem.getAmtinclusivetax());
            sid.setRecStatus("Prepared");
            sid.setDiscountconfigamount(selectedItem.getDiscountConfigAmt());
            sid.setDiscountAmount(selectedItem.getDiscountAmt());
            sid.setCessPer(selectedItem.getCess());
            Double cessTaxAmt = ((selectedItem.getAmtexclusivetax()) * (selectedItem.getCess())) / 100;
            DecimalFormat df = new DecimalFormat("#.##");
            sid.setCessTaxAmt(Double.parseDouble((df.format(cessTaxAmt))));
            sidList.add(sid);
        }
        return sidList;
    }
    private List<SalesReturnDetails> createReturnSIDetails(RetailDTO retailDTO, SalesReturn salesReturn) {
        List<SalesReturnDetails> sidList = new ArrayList<>();
        for (SelectedItem selectedItem : retailDTO.getSelectedItemsList()) {
            if(selectedItem.getReturnQty()>0) {
                SalesReturnDetails sid = new SalesReturnDetails();
                // TODO
                sid.setSrNo(salesReturn.getSrNo());
                Item item = itemRepository.findByItemName(selectedItem.getItemName());
                sid.setItemName(item.getItemName());
                sid.setItemDesc(selectedItem.getItemDescription());
                sid.setItemCode(item.getItemCode());
                sid.setPrice(selectedItem.getUnitPrice());
                sid.setQtyOrdered(selectedItem.getQty());
                sid.setQtyReturned(selectedItem.getReturnQty());
                sid.setDiscPercent(0.00);
                sid.setItemAmountExcTax(selectedItem.getAmtexclusivetax());
                Tax tax = taxRepository.findByTaxGroupAndTaxName("SALES/OUTPUT", selectedItem.getTaxName());
                if (tax != null) {
                    sid.setTaxName(tax.getTaxName());
                    sid.setItemTaxPer(tax.getTax_Per());
                }
                DirectSalesInvoiceDetailsTemplate salesInvoiceDetailsTemplate = salesInvoiceDetailsRepository.findBySINoAndItemName(salesReturn.getsINo(), item.getItemName());
                salesInvoiceDetailsTemplate.setQtyRemain(salesInvoiceDetailsTemplate.getQtyRemain() - selectedItem.getReturnQty());
                if (salesInvoiceDetailsTemplate.getQtyRemain() == 0) {
                    salesInvoiceDetailsTemplate.setRecStatus("Returned");
                }
                item.setStock(item.getStock() + selectedItem.getReturnQty());
                itemRepository.save(item);
                sid.setItemTax(selectedItem.getTaxamt());
                sid.setItemAmountIncTax(selectedItem.getAmtinclusivetax());
                sid.setStatus("Returned");
                Double cessTaxAmt = ((selectedItem.getAmtexclusivetax()) * (selectedItem.getCess())) / 100;
                DecimalFormat df = new DecimalFormat("#.##");
                sid.setCessTaxAmt(Double.parseDouble((df.format(cessTaxAmt))));
                sidList.add(sid);
            }
        }
        List<DirectSalesInvoiceDetailsTemplate> list=salesInvoiceDetailsRepository.findBySINo(salesReturn.getsINo());
        int i=0;
        for(DirectSalesInvoiceDetailsTemplate directSalesInvoiceDetailsTemplate:list){
            if(directSalesInvoiceDetailsTemplate.getQtyRemain()==0){
                i++;
            }
        }
        if(i==list.size()){
            DirectSalesInvoiceTemplate salesInvoiceTemplate=salesInvoiceRepository.findAllBySiNo(salesReturn.getsINo());
            salesInvoiceTemplate.setSiStatus("Returned");
            salesInvoiceRepository.save(salesInvoiceTemplate);
        }
        return sidList;
    }

    public RetailDTO createReturnSI(RetailDTO retailDTO){
        DirectSalesInvoiceTemplate directSalesInvoiceTemplate=salesInvoiceRepository.findAllBySiNo(retailDTO.getSiNo());
        SalesReturn salesReturn=new SalesReturn();
        FormSetUp formSetUp = formSetupRepository.findAllByTypename("SalesCreditNote");
        salesReturn.setSrNo(posService.getNextRefInvoice(formSetUp.getTypeprefix(), formSetUp.getNextref()));
        int incValue = Integer.parseInt(formSetUp.getNextref());
        retailDTO.setFormSetupNo(posService.getNextRefInvoice(formSetUp.getTypeprefix(), String.format("%08d", ++incValue)));
        formSetUp.setNextref(String.format("%08d", incValue));
        formSetupRepository.save(formSetUp);
        salesReturn.setSrDate(posService.parseDate(retailDTO.getDateOfInvoice()));
        Customer customer = customerRepository.findAllByCustomerName(retailDTO.getCutomerName());
        salesReturn.setCustomerName(customer.getCustomerName());
        if (retailDTO.getMemo() != null) {
            salesReturn.setMemo(retailDTO.getMemo());
        }
        salesReturn.setsINo(directSalesInvoiceTemplate.getSiNo());
        salesReturn.setSrStatus("Return");
        salesReturn.setFlag("POS Sales Invoice");
        salesReturn.setCessTaxAmt(retailDTO.getCessTotalTaxAmt());
        salesReturn.setTaxInvoice(retailDTO.getTaxType());
        salesReturn.setTotalTaxAmount(retailDTO.getTotalTaxAmt());
        List<SalesReturnDetails> salesReturnDetails = createReturnSIDetails(retailDTO, salesReturn);
        Map<Long, TaxSummary> taxMap = getTaxSummary(retailDTO.getSelectedItemsList());
        List<TaxSummary> taxSummarys = new ArrayList<>();
        taxSummarys.addAll(taxMap.values());
        retailDTO.setTaxSummaryList(taxSummarys);
        salesReturnRepository.save(salesReturn);
        salesReturnDetailsRepository.save(salesReturnDetails);
        return retailDTO;
    }
    public RetailPrintDTO createBillingSalesInvoices(RetailDTO retailDTO, String status) {
        RetailPrintDTO printDTO = new RetailPrintDTO();
        DirectSalesInvoiceTemplate salesInvoice = null;
        if (Long.valueOf(retailDTO.getSiid()) != null && Long.valueOf(retailDTO.getSiid()) > 0) {
            retailDTO.setSiStatus(status);
            DirectSalesInvoiceTemplate salesInvoice1 = salesInvoiceRepository.findAllBySiNo(retailDTO.getSiNo());
            salesInvoice = createBillingPOSSI(salesInvoice1, retailDTO, null);
            newPosPaymentRepository.delete(newPosPaymentRepository.findAllBySiNo(salesInvoice.getSiNo()));
            if (StringUtils.pathEquals(status, "Prepared")) {
                for (SelectedItem selectedItem : retailDTO.getSelectedItemsList()) {
                    Item item = itemRepository.findByItemName(selectedItem.getItemName());
                    DirectSalesInvoiceDetailsTemplate directSalesInvoiceDetailsTemplate = salesInvoiceDetailsRepository.findBySINoAndItemName(salesInvoice1.getSiNo(), item.getItemName());
                    double stock = (item.getStock() + directSalesInvoiceDetailsTemplate.getQtyOrdered());
                    item.setStock(stock - selectedItem.getQty());
                    itemRepository.save(item);
                }
            }
            if (StringUtils.pathEquals(status, "Draft")) {
                for (SelectedItem selectedItem : retailDTO.getSelectedItemsList()) {
                    Item item = itemRepository.findByItemName(selectedItem.getItemName());
                    DirectSalesInvoiceDetailsTemplate directSalesInvoiceDetailsTemplate = salesInvoiceDetailsRepository.findBySINoAndItemName(salesInvoice1.getSiNo(), item.getItemName());
                    double stock = (item.getStock() + directSalesInvoiceDetailsTemplate.getQtyOrdered());
                    item.setStock(stock);
                    itemRepository.save(item);
                }
            }
            salesInvoiceDetailsRepository.delete(salesInvoice.getdSIId());
            List<DirectSalesInvoiceDetailsTemplate> sidList = createBillingPOSSIDetails(retailDTO, salesInvoice);
            salesInvoiceRepository.save(salesInvoice);
            salesInvoiceDetailsRepository.save(sidList);
            Map<Long, TaxSummary> taxMap = getTaxSummary(retailDTO.getSelectedItemsList());
            List<TaxSummary> taxSummarys = new ArrayList<>();
            taxSummarys.addAll(taxMap.values());
            List<TaxSummary> taxPerList = new ArrayList<>();
            savePosPayments(retailDTO, salesInvoice);
            Map<Double, List<SelectedItem>> taxList =
                    retailDTO.getSelectedItemsList().parallelStream().collect(Collectors.groupingBy(w -> w.getTaxpercent()));
            for (Map.Entry m : taxList.entrySet()) {
                TaxSummary taxSummary = new TaxSummary();
                taxSummary.setTaxPercent((Double) m.getKey());
                for (SelectedItem selectedItem : (List<SelectedItem>) m.getValue()) {
                    taxSummary.setTaxName(selectedItem.getTaxName());
                    taxSummary.setAmt(selectedItem.getAmtinclusivetax() + taxSummary.getAmt());
                    taxSummary.setTaxableAmt(selectedItem.getAmtexclusivetax() + taxSummary.getTaxableAmt());
                    taxSummary.setTaxAmount(selectedItem.getTaxamt() + taxSummary.getTaxAmount());
                    taxSummary.setCessAmt(selectedItem.getCessTaxAmt() + taxSummary.getCessAmt());
                }
                taxPerList.add(taxSummary);
            }
            retailDTO.setTaxWiseSummaryList(taxPerList);
            retailDTO.setTaxSummaryList(taxSummarys);
            double totalExAmt = 0;
            double totalInAmt = 0;
            for (SelectedItem selectedItem : retailDTO.getSelectedItemsList()) {
                totalExAmt = selectedItem.getAmtexclusivetax() + totalExAmt;
                totalInAmt = selectedItem.getAmtinclusivetax() + totalInAmt;
            }
            retailDTO.setTotalAmt(totalInAmt);
            printDTO.setMemo(retailDTO.getMemo());

        } else {
            retailDTO.setSiStatus(status);
            FormSetUp formSetUp = formSetupRepository.findAllByTypename("DirectSalesInvoice");
            salesInvoice = createBillingPOSSI(new DirectSalesInvoiceTemplate(), retailDTO, formSetUp);
            salesInvoice.setArBalance(5);
            List<DirectSalesInvoiceDetailsTemplate> sidList = createBillingPOSSIDetails(retailDTO, salesInvoice);
            salesInvoiceRepository.save(salesInvoice);
            salesInvoiceDetailsRepository.save(sidList);
            savePosPayments(retailDTO, salesInvoice);
            Map<Long, TaxSummary> taxMap = getTaxSummary(retailDTO.getSelectedItemsList());
            List<TaxSummary> taxSummarys = new ArrayList<>();
            taxSummarys.addAll(taxMap.values());
            retailDTO.setTaxSummaryList(taxSummarys);
        }
        double totalExAmt = 0;
        double totalInAmt = 0;
        for (SelectedItem selectedItem : retailDTO.getSelectedItemsList()) {
            totalExAmt = selectedItem.getAmtexclusivetax() + totalExAmt;
            totalInAmt = selectedItem.getAmtinclusivetax() + totalInAmt;
        }
        printDTO.setSiData(retailDTO);
        printDTO.setStatus("Success");
        printDTO.setFormSetUpNo(retailDTO.getFormSetupNo());
        return printDTO;
    }

    public NewPosPaymentTypes savePosPayments(RetailDTO retailDTO, DirectSalesInvoiceTemplate salesInvoice) {
        Gson gson = new Gson();
        NewPosPaymentTypes posPaymentTypes = new NewPosPaymentTypes();
        posPaymentTypes.setTotalAmount(retailDTO.getTotalTenderedAmount());
        posPaymentTypes.setTotalCashPayment(retailDTO.getTotalCashPayment());
        posPaymentTypes.setTotalCardPayment(retailDTO.getTotalCardPayment());
        posPaymentTypes.setTotalCoupon(retailDTO.getTotalCouponPayment());
        posPaymentTypes.setTotalVoucherPayment(retailDTO.getTotalVoucherPayment());
        posPaymentTypes.setTotaleWallet(retailDTO.getTotalEWalletPayment());
        if (retailDTO.getCashPayment() != null) {
            posPaymentTypes.setCashPayment(gson.toJson(retailDTO.getCashPayment().getMultiCashPaymentList()));
        }
        if (retailDTO.getVoucherPayment() != null) {
            posPaymentTypes.setVoucherPayment(gson.toJson(retailDTO.getVoucherPayment().getMultiVoucherPayments()));
        }
        if (retailDTO.getCreditPayment() != null) {
            posPaymentTypes.setCreditNotePayment(gson.toJson(retailDTO.getCreditPayment().getCardPaymentList()));
        }
        if (retailDTO.getCouponDetails() != null) {
            posPaymentTypes.setCouponPayment(gson.toJson(retailDTO.getCouponDetails().getCouponPaymentList()));
        }
        if (retailDTO.getBankPayment() != null) {
            posPaymentTypes.setChequePayment(gson.toJson(retailDTO.getBankPayment().getMultiBankPaymentList()));
        }
        if (retailDTO.getEwalletDetails() != null) {
            posPaymentTypes.setEwalletPayment(gson.toJson(retailDTO.getEwalletDetails().getEwalletDEtailsList()));
        }
        if (retailDTO.getGiftCardDetails() != null) {
            posPaymentTypes.setGiftCardPaymnet(gson.toJson(retailDTO.getGiftCardDetails().getGiftCardDetailsList()));
        }
        posPaymentTypes.setSiNo(salesInvoice.getSiNo());
        posPaymentTypes.setCustomerName(salesInvoice.getCustomerName());
        newPosPaymentRepository.save(posPaymentTypes);
        return posPaymentTypes;
    }

    private Map<Long, TaxSummary> getTaxSummary(List<SelectedItem> selectedItems) {
        Map<Long, TaxSummary> taxSummaryMap = new HashMap<>();
        TaxSummary taxSummary = null;
        for (SelectedItem selectedItem : selectedItems) {
            if (taxSummaryMap.containsKey(selectedItem.getTaxid())) {
                taxSummary = taxSummaryMap.get(selectedItem.getTaxid());
                double taxAmt = taxSummary.getTaxAmount() + selectedItem.getTaxamt();
                double itemAmt = taxSummary.getAmt() + selectedItem.getAmtinclusivetax();
                taxSummary.setTaxAmount(taxAmt);
                taxSummary.setAmt(itemAmt);
            } else {
                taxSummary = new TaxSummary();
                taxSummary.setTaxAmount(selectedItem.getTaxamt());
                taxSummary.setTaxId(selectedItem.getTaxid());
                taxSummary.setTaxName(selectedItem.getTaxName());
                taxSummary.setTaxPercent(selectedItem.getTaxpercent());
                taxSummary.setAmt(selectedItem.getAmtinclusivetax());
            }
            taxSummaryMap.put(selectedItem.getTaxid(), taxSummary);
        }
        return taxSummaryMap;
    }

    private DirectSalesInvoiceTemplate createBillingPOSSI(DirectSalesInvoiceTemplate salesInvoice, RetailDTO retailDTO, FormSetUp formSetUp) {
        if(StringUtils.isEmpty(salesInvoice.getSiNo())) {
            salesInvoice.setSiNo(posService.getNextRefInvoice(formSetUp.getTypeprefix(), formSetUp.getNextref()));
            int incValue = Integer.parseInt(formSetUp.getNextref());
            retailDTO.setFormSetupNo(posService.getNextRefInvoice(formSetUp.getTypeprefix(), String.format("%08d", ++incValue)));
            formSetUp.setNextref(String.format("%08d", incValue));
            formSetupRepository.save(formSetUp);
        }
        salesInvoice.setSiDate(posService.parseDate(retailDTO.getDateOfInvoice()));
        Customer customer = customerRepository.findAllByCustomerName(retailDTO.getCutomerName());
        salesInvoice.setCustomerName(customer.getCustomerName());
//       if (retailDTO.getAgentId() != null) {
//            Agent agent = hiposDAO.getAgentObject(retailDTO.getAgentId());
//            salesInvoice.setAgentId(agent);
//       }
        if (!StringUtils.isEmpty(retailDTO.getShippingMethodName())) {
            salesInvoice.setReferenceno(retailDTO.getShippingMethodName());
        }
        if (!StringUtils.isEmpty(retailDTO.getShippingReferenceNo())) {
            salesInvoice.setShippingReferenceNo(retailDTO.getShippingReferenceNo());
        }

        if (retailDTO.getMemo() != null) {
            salesInvoice.setMemo(retailDTO.getMemo());
            salesInvoice.setMemo(retailDTO.getMemo());
        }
        if (retailDTO.getCustomerPo() != null) {
            salesInvoice.setCustPONo(retailDTO.getCustomerPo());
        }
        if (StringUtils.pathEquals(retailDTO.getSiStatus(), "Draft")) {
            salesInvoice.setSiStatus("Draft");
            salesInvoice.setPosting("No");
        } else {
            salesInvoice.setSiStatus("Prepared");
            salesInvoice.setPosting("Yes");
        }
        salesInvoice.setFlag("POS Sales Invoice");
        salesInvoice.setShowReport("No");
        salesInvoice.setSalesTotalTaxAmt(retailDTO.getTotalTaxAmt());
        salesInvoice.setCessTaxAmt(retailDTO.getCessTotalTaxAmt());
        if (retailDTO.getAmountReturned() == 0 && retailDTO.getTotalTenderedAmount() == 0) {
            salesInvoice.setArBalance(retailDTO.getTotalCheckOutamt());
        } else {
            if (retailDTO.getAmountReturned() < 0) {
                salesInvoice.setArBalance(retailDTO.getAmountReturned());
                salesInvoice.setTotalReceived(retailDTO.getTotalTenderedAmount());
                salesInvoice.setCustPONo(String.valueOf(retailDTO.getTotalTenderedAmount()));
            } else {
                salesInvoice.setArBalance(0);
                salesInvoice.setTotalReceived(retailDTO.getTotalTenderedAmount() - retailDTO.getAmountReturned());
                salesInvoice.setCustPONo(String.valueOf(retailDTO.getTotalTenderedAmount() - retailDTO.getAmountReturned()));
            }
        }
        salesInvoice.setTaxInvoice(retailDTO.getTaxType());
        salesInvoice.setTotalAmount(retailDTO.getTotalCheckOutamt());
        salesInvoice.setTotalReceivable(retailDTO.getTotalCheckOutamt());
        salesInvoice.setTotalReceived(retailDTO.getTotalTenderedAmount());
        salesInvoice.setTotalDiscountAmount(retailDTO.getDiscountAmount());
        return salesInvoice;
    }

    public String makeCancelledSalesInvoice(String invoiceNo) {
        DirectSalesInvoiceTemplate salesInvoiceTemplate = salesInvoiceRepository.findAllBySiNo(invoiceNo);
        List<DirectSalesInvoiceDetailsTemplate> directSalesInvoiceDetailsTemplates = salesInvoiceDetailsRepository.findBySINo(salesInvoiceTemplate.getSiNo());
        if (StringUtils.pathEquals(salesInvoiceTemplate.getSiStatus(), "Draft")) {
            salesInvoiceTemplate.setSiStatus("Draft Cancelled Invoice");
        } else {
            for (DirectSalesInvoiceDetailsTemplate salesInvoiceDetailsTemplate : directSalesInvoiceDetailsTemplates) {
                Item item = itemRepository.findByItemName(salesInvoiceDetailsTemplate.getItemName());
                double stock = (item.getStock() + salesInvoiceDetailsTemplate.getQtyOrdered());
                item.setStock(stock);
                itemRepository.save(item);
            }
            salesInvoiceTemplate.setSiStatus("Cancelled Invoice");
        }
        int size = salesInvoiceRepository.findAllBySiNoStartingWith(salesInvoiceTemplate.getSiNo()).size();
        salesInvoiceTemplate.setSiNo(salesInvoiceTemplate.getSiNo()+"c"+size);
        salesInvoiceRepository.save(salesInvoiceTemplate);
        return "Success";
    }

    public List<RetailDTO> getSalesInvoicesList(String status,String search) {
        List<RetailDTO> retailDTOList = new ArrayList<>();
        List<DirectSalesInvoiceTemplate> salesInvoiceTemplates=new ArrayList<>();
        if(!StringUtils.isEmpty(search)){
            salesInvoiceTemplates = salesInvoiceRepository.findAllBySiStatusContainingAndSiNo(status, search);
        }
        else{
            salesInvoiceTemplates = salesInvoiceRepository.findAllBySiStatus(status);
        }
        for (DirectSalesInvoiceTemplate salesInvoiceTemplate : salesInvoiceTemplates) {
            RetailDTO retailDTO = new RetailDTO();
            retailDTO.setSiNo(salesInvoiceTemplate.getSiNo());
            retailDTO.setSiStatus(salesInvoiceTemplate.getSiStatus());
            retailDTO.setSiid(salesInvoiceTemplate.getdSIId());
            retailDTO.setTotalTenderedAmount(salesInvoiceTemplate.getTotalAmount());
            retailDTO.setCutomerName(salesInvoiceTemplate.getCustomerName());
            retailDTO.setInvoiceDate(salesInvoiceTemplate.getSiDate());
            retailDTOList.add(retailDTO);
        }
        return retailDTOList;
    }

    public RetailDTO getSalesInvoice(String invoiceNo) {
        RetailDTO retailDTO = new RetailDTO();
        List<SelectedItem> selectedItems = new ArrayList<>();
        DirectSalesInvoiceTemplate salesInvoiceTemplate = salesInvoiceRepository.findAllBySiNo(invoiceNo);
        retailDTO.setSiNo(salesInvoiceTemplate.getSiNo());
        retailDTO.setSiStatus(salesInvoiceTemplate.getSiStatus());
        retailDTO.setSiid(salesInvoiceTemplate.getdSIId());
        retailDTO.setTotalTenderedAmount(salesInvoiceTemplate.getTotalAmount());
        retailDTO.setCutomerName(salesInvoiceTemplate.getCustomerName());
//        retailDTO.setCustomerId(salesInvoiceTemplate.getCustomerId());
        retailDTO.setInvoiceDate(salesInvoiceTemplate.getSiDate());
        List<DirectSalesInvoiceDetailsTemplate> invoiceDetailsTemplates = salesInvoiceDetailsRepository.findBySINo(salesInvoiceTemplate.getSiNo());
        for (DirectSalesInvoiceDetailsTemplate salesInvoiceDetailsTemplate : invoiceDetailsTemplates) {
            SelectedItem selectedItem = new SelectedItem();
            selectedItem.setQty(salesInvoiceDetailsTemplate.getQtyRemain());
            selectedItem.setRemainingQty(salesInvoiceDetailsTemplate.getQtyOrdered());
            Item item=itemRepository.findByItemName(salesInvoiceDetailsTemplate.getItemName());
            selectedItem.setItemId(item.getItemId());
            selectedItem.setItemName(item.getItemName());
            selectedItem.setInclusiveJSON(item.getInclusiveJSON());
            selectedItem.setItemCode(item.getItemCode());
            selectedItem.setItemDescription(item.getItemDesc());
            selectedItem.setUomName(item.getUomName());
            selectedItem.setUnitPrice(salesInvoiceDetailsTemplate.getPrice());
            selectedItem.setDiscountAmt(salesInvoiceDetailsTemplate.getDiscountAmount());
            selectedItem.setDiscountConfigAmt(salesInvoiceDetailsTemplate.getDiscountconfigamount());
            Tax tax=taxRepository.findByTaxGroupAndTaxName("SALES/OUTPUT",salesInvoiceDetailsTemplate.getTaxName());
            selectedItem.setTaxpercent(tax.getTax_Per());
            selectedItem.setTaxName(tax.getTaxName());
            selectedItem.setTaxid(tax.getTaxId());
            selectedItem.setAmtexclusivetax(salesInvoiceDetailsTemplate.getItemAmountExcTax());
            selectedItems.add(selectedItem);
        }
        retailDTO.setSelectedItemsList(selectedItems);
        return retailDTO;
    }
    public String setFormSetupValue(Long invoiceID, String formNo) {
        List<DirectSalesInvoiceDetailsTemplate> invoiceDetailsTemplateList = new ArrayList<>();
        List<DirectSalesInvoiceTemplate> salesInvoiceTemplateslist = salesInvoiceRepository.findAllByDSIIdGreaterThan(invoiceID);
        String[] parts = formNo.split("DSINV");
        String part2 = parts[1];
        int value = (Integer.parseInt(part2));
        if (salesInvoiceTemplateslist.size()>0) {
            for(DirectSalesInvoiceTemplate salesInvoiceTemplates:salesInvoiceTemplateslist) {
                salesInvoiceTemplates.setSiNo("DSINV" + String.format("%08d", value++));
                salesInvoiceRepository.save(salesInvoiceTemplates);
                invoiceDetailsTemplateList = salesInvoiceDetailsRepository.findBySINo(salesInvoiceTemplates.getSiNo());
                for (DirectSalesInvoiceDetailsTemplate detailsTemplate : invoiceDetailsTemplateList) {
                    detailsTemplate.setsINo(salesInvoiceTemplates.getSiNo());
                    salesInvoiceDetailsRepository.save(detailsTemplate);
                }
            }
            FormSetUp formSetUp = formSetupRepository.findAllByTypename("DirectSalesInvoice");
            formSetUp.setNextref(String.format("%08d", value));
            formSetupRepository.save(formSetUp);
        }else {
            FormSetUp formSetUp = formSetupRepository.findAllByTypename("DirectSalesInvoice");
            int val=Integer.parseInt(formSetUp.getNextref());
            formSetUp.setNextref(String.format("%08d", val-1));
            formSetupRepository.save(formSetUp);
        }
        return "Success";
    }

    public String deletePOSBillingInvoice(String invoiceNo) {
        DirectSalesInvoiceTemplate posInv = salesInvoiceRepository.findAllBySiNo(invoiceNo);
        String formNo = posInv.getSiNo();
        setFormSetupValue(posInv.getdSIId(), formNo);
        List<DirectSalesInvoiceDetailsTemplate> invoiceDetails =salesInvoiceDetailsRepository.findBySINo(posInv.getSiNo());
        for (DirectSalesInvoiceDetailsTemplate invoice : invoiceDetails) {
            Item item = itemRepository.findByItemName(invoice.getItemName());
            item.setStock(item.getStock() + invoice.getQtyOrdered());
            itemRepository.save(item);
        }
        newPosPaymentRepository.delete(newPosPaymentRepository.findAllBySiNo(posInv.getSiNo()));
        salesInvoiceDetailsRepository.delete(invoiceDetails);
        salesInvoiceRepository.delete(posInv);
        return "SUCCESS";
    }

    public List<SalesInvoiceDTO> getSalesList() {
        List<DirectSalesInvoiceTemplate> sales=new ArrayList<>();
        sales=salesInvoiceRepository.findAll();
        List<SalesInvoiceDTO> salesDTO= PosItemMapper.mapSalesInvEntityToPojo(sales);
        return salesDTO;
    }
}