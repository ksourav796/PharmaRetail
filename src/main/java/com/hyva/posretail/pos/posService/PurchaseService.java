    package com.hyva.posretail.pos.posService;

    import com.google.gson.Gson;
    import com.hyva.posretail.pos.posEntities.*;
    import com.hyva.posretail.pos.posMapper.PosItemMapper;
    import com.hyva.posretail.pos.posPojo.*;
    import com.hyva.posretail.pos.posRespositories.*;
    import com.hyva.posretail.pusher.pusherPojo.SelectedItem;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.util.StringUtils;

    import java.text.DecimalFormat;
    import java.text.SimpleDateFormat;
    import java.util.*;

    @Service
    public class PurchaseService {
        @Autowired
        PosTaxRepository taxRepository;
        @Autowired
        PosPurchaseInvoiceDetailsRepository purchaseInvoiceDetailsRepository;
        @Autowired
        PosItemRepository itemRepository;
        @Autowired
        PosPurchaseInvoiceRepository purchaseInvoiceRepository;
        @Autowired
        PosSupplierRepository posSupplierRepository;
        @Autowired
        PosFormSetupRepository formSetupRepository;
        @Autowired
        PosItemRepository posItemRepository;
        @Autowired
        PosTaxRepository posTaxRepository;
        @Autowired
        PosPurchasePaymentRepository posPurchasePaymentRepository;
        @Autowired
        PosFormSetupRepository posFormSetupRepository;
        @Autowired
        PosService posService;
        @Autowired
        PurchaseReturnRepository purchaseReturnRepository;
        @Autowired
        PurchaseReturnDetailsRepository purchaseReturnDetailsRepository;

        public String setFormSetupValue(Long invoiceID, String formNo) {
            List<PurchaseInvoiceDetails> invoiceDetailsTemplateList = new ArrayList<>();
            List<PurchaseInvoice> purchaseInvoiceList = purchaseInvoiceRepository.findAllByPiIdGreaterThan(invoiceID);
            String[] parts = formNo.split("DPINV");
            String part2 = parts[1];
            int value = (Integer.parseInt(part2));
            if (purchaseInvoiceList.size()>0) {
                for(PurchaseInvoice purchaseInvoice:purchaseInvoiceList) {
                    purchaseInvoice.setPino("DPINV" + String.format("%08d", value++));
                    purchaseInvoiceRepository.save(purchaseInvoice);
                    invoiceDetailsTemplateList = purchaseInvoiceDetailsRepository.findAllByPINo(purchaseInvoice.getPino());
                    for (PurchaseInvoiceDetails detailsTemplate : invoiceDetailsTemplateList) {
                        detailsTemplate.setpINo(purchaseInvoice.getPino());
                        purchaseInvoiceDetailsRepository.save(detailsTemplate);
                    }
                }
                FormSetUp formSetUp = formSetupRepository.findAllByTypename("DirectPurchaseInvoice");
                formSetUp.setNextref(String.format("%08d", value));
                formSetupRepository.save(formSetUp);
            }else {
                FormSetUp formSetUp = formSetupRepository.findAllByTypename("DirectPurchaseInvoice");
                int val=Integer.parseInt(formSetUp.getNextref());
                formSetUp.setNextref(String.format("%08d", val-1));
                formSetupRepository.save(formSetUp);
            }
            return "Success";
        }

        public String deletePOSPurchaseBillingInvoice(String invoiceNo) {
            PurchaseInvoice posInv = purchaseInvoiceRepository.findAllByPino(invoiceNo);
            String formNo = posInv.getPino();
            setFormSetupValue(posInv.getPiId(), formNo);
            List<PurchaseInvoiceDetails> invoiceDetails =purchaseInvoiceDetailsRepository.findAllByPINo(posInv.getPino());
            for (PurchaseInvoiceDetails invoice : invoiceDetails) {
                Item item = itemRepository.findByItemName(invoice.getItemName());
                item.setStock(item.getStock() - invoice.getQtyOrdered());
                itemRepository.save(item);
            }
            posPurchasePaymentRepository.delete(posPurchasePaymentRepository.findAllByPino(posInv.getPino()));
            purchaseInvoiceDetailsRepository.delete(invoiceDetails);
            purchaseInvoiceRepository.delete(posInv);
            return "SUCCESS";
        }

        public String makeCancelledPurchaseInvoice(String invoiceNo) {
            PurchaseInvoice purchaseInvoice = purchaseInvoiceRepository.findAllByPino(invoiceNo);
            List<PurchaseInvoiceDetails> purchaseInvoiceDetails = purchaseInvoiceDetailsRepository.findAllByPINo(purchaseInvoice.getPino());
            if (StringUtils.pathEquals(purchaseInvoice.getPiStatus(), "Draft")) {
                purchaseInvoice.setPiStatus("Draft Cancelled Invoice");
            } else {
                for (PurchaseInvoiceDetails purchaseInvoiceDetails1 : purchaseInvoiceDetails) {
                    Item item =itemRepository.findByItemName(purchaseInvoiceDetails1.getItemName());
                    double stock = (item.getStock() - purchaseInvoiceDetails1.getQtyOrdered());
                    item.setStock(stock);
                    itemRepository.save(item);
                }
                purchaseInvoice.setPiStatus("Cancelled Invoice");
            }
            int size = purchaseInvoiceRepository.findAllByPinoStartingWith(purchaseInvoice.getPino()).size();
            purchaseInvoice.setPino(purchaseInvoice.getPino()+"c"+size);
            purchaseInvoiceRepository.save(purchaseInvoice);
            return "Success";
        }

        public List<PurchaseDTO> getPurchaseInvoicesList(String status,String search) {
            List<PurchaseDTO> purchaseDTOList = new ArrayList<>();
            List<PurchaseInvoice> purchaseInvoices=new ArrayList<>();
            if(!StringUtils.isEmpty(search)){
                purchaseInvoices = purchaseInvoiceRepository.findAllByPiStatusContainingAndPino(status, search);
            }
            else{
                purchaseInvoices = purchaseInvoiceRepository.findAllByPiStatus(status);
            }
            for (PurchaseInvoice purchaseInvoice : purchaseInvoices) {
                PurchaseDTO purchaseDTO = new PurchaseDTO();
                purchaseDTO.setPiNo(purchaseInvoice.getPino());
                purchaseDTO.setPiStatus(purchaseInvoice.getPiStatus());
                purchaseDTO.setPiid(purchaseInvoice.getPiId());
                purchaseDTO.setTotalTenderedAmount(purchaseInvoice.getTotalAmount());
                purchaseDTO.setSupplierName(purchaseInvoice.getSupplierName());
                purchaseDTO.setDate(purchaseInvoice.getPidate());
                purchaseDTOList.add(purchaseDTO);
            }
            return purchaseDTOList;
        }

        public PurchaseDTO getPurchaseInvoice(String no) {
            PurchaseDTO purchaseDTO = new PurchaseDTO();
            List<SelectedItem> selectedItems = new ArrayList<>();
            PurchaseInvoice purchaseInvoice = purchaseInvoiceRepository.findAllByPino(no);
            purchaseDTO.setPiNo(purchaseInvoice.getPino());
            purchaseDTO.setPiStatus(purchaseInvoice.getPiStatus());
            purchaseDTO.setPiid(purchaseInvoice.getPiId());
            purchaseDTO.setTotalTenderedAmount(purchaseInvoice.getTotalAmount());
            purchaseDTO.setSupplierName(purchaseInvoice.getSupplierName());
//            purchaseDTO.setSupplierId(purchaseInvoice.getSupplierId().getSupplierId());
            purchaseDTO.setDate(purchaseInvoice.getPidate());
            List<PurchaseInvoiceDetails> purchaseInvoiceDetailsList = purchaseInvoiceDetailsRepository.findAllByPINo(purchaseInvoice.getPino());
            for (PurchaseInvoiceDetails purchaseInvoiceDetails : purchaseInvoiceDetailsList) {
                SelectedItem selectedItem = new SelectedItem();
                Item item=itemRepository.findByItemName(purchaseInvoiceDetails.getItemName());
                Tax tax=taxRepository.findByTaxGroupAndTaxName("PURCHASE/INPUT",purchaseInvoiceDetails.getTaxName());
                selectedItem.setQty(purchaseInvoiceDetails.getQtyRemaining());
                selectedItem.setRemainingQty(purchaseInvoiceDetails.getQtyOrdered());
                selectedItem.setItemCode(item.getItemCode());
                selectedItem.setItemId(item.getItemId());
                selectedItem.setItemName(item.getItemName());
                selectedItem.setItemDescription(item.getItemDesc());
                selectedItem.setUomName(item.getUomName());
                selectedItem.setUnitPrice(purchaseInvoiceDetails.getPrice());
                selectedItem.setDiscountAmt(purchaseInvoiceDetails.getDiscountAmount());
                selectedItem.setInclusiveJSON(item.getInclusiveJSON());
                selectedItem.setDiscountConfigAmt(purchaseInvoiceDetails.getDiscountconfigamount());
                selectedItem.setTaxpercent(tax.getTax_Per());
                selectedItem.setTaxName(tax.getTaxName());
                selectedItem.setTaxid(tax.getTaxId());
                selectedItem.setAmtexclusivetax(purchaseInvoiceDetails.getItemAmountExcTax());
                selectedItems.add(selectedItem);
            }
            purchaseDTO.setSelectedItemsList(selectedItems);
            return purchaseDTO;
        }

        public PurchasePrintDTO savePurchaseInvoice(PurchaseDTO purchaseDTO, String status){
            PurchasePrintDTO printDTO = new PurchasePrintDTO();
            PurchaseInvoice purchaseInvoice = new PurchaseInvoice();
            List<PurchaseInvoiceDetails> pidList = null;
            if (purchaseDTO.getPiid() != 0) {
                purchaseDTO.setPiStatus(status);
                purchaseInvoice = purchaseInvoiceRepository.findAllByPino(purchaseDTO.getPiNo());
                purchaseInvoice = createPOSSI(purchaseInvoice, purchaseDTO, null);
                if (StringUtils.pathEquals(status, "Prepared")) {
                    for (SelectedItem selectedItem : purchaseDTO.getSelectedItemsList()) {
                        Item item = itemRepository.findByItemName(selectedItem.getItemName());
                        PurchaseInvoiceDetails purchaseInvoiceDetails = purchaseInvoiceDetailsRepository.findAllByPINoAndItemName(purchaseInvoice.getPino(), item.getItemName());
                        double stock = (item.getStock() - purchaseInvoiceDetails.getQtyOrdered());
                        item.setStock(stock + selectedItem.getQty());
                        itemRepository.save(item);
                    }
                }
                if (StringUtils.pathEquals(status, "Draft")) {
                    for (SelectedItem selectedItem : purchaseDTO.getSelectedItemsList()) {
                        Item item = itemRepository.findByItemName(selectedItem.getItemName());
                        PurchaseInvoiceDetails purchaseInvoiceDetails = purchaseInvoiceDetailsRepository.findAllByPINoAndItemName(purchaseInvoice.getPino(), item.getItemName());
                        double stock = (item.getStock() - purchaseInvoiceDetails.getQtyOrdered());
                        item.setStock(stock);
                        itemRepository.save(item);
                    }
                }
                posPurchasePaymentRepository.delete(posPurchasePaymentRepository.findAllByPino(purchaseInvoice.getPino()));
                List<PurchaseInvoiceDetails> list = purchaseInvoiceDetailsRepository.findAllByPINo(purchaseInvoice.getPino());
                purchaseInvoiceDetailsRepository.delete(list);
                pidList = createPOSSIDetails(purchaseDTO, purchaseInvoice);
                purchaseInvoiceRepository.save(purchaseInvoice);
                purchaseInvoiceDetailsRepository.save(pidList);
                savePosPurchasePayments(purchaseDTO,purchaseInvoice);
                Map<Long, TaxSummary> taxMap = getTaxSummary(purchaseDTO.getSelectedItemsList());
                List<TaxSummary> taxSummarys = new ArrayList<>();
                taxSummarys.addAll(taxMap.values());
                purchaseDTO.setTaxSummaryList(taxSummarys);
                printDTO = getPrintDetails(purchaseDTO, purchaseInvoice);
                printDTO.setStatus("Success");
                printDTO.setPiData(purchaseDTO);
                return printDTO;
            } else {
                purchaseDTO.setPiStatus(status);
                FormSetUp formSetUp = posFormSetupRepository.findAllByTypename("DirectPurchaseInvoice");
                purchaseInvoice = createPOSSI(new PurchaseInvoice(), purchaseDTO, formSetUp);
                purchaseDTO.setPiNo(purchaseInvoice.getPino());
                pidList = createPOSSIDetails(purchaseDTO, purchaseInvoice);
                purchaseInvoiceRepository.save(purchaseInvoice);
                purchaseInvoiceDetailsRepository.save(pidList);
                savePosPurchasePayments(purchaseDTO,purchaseInvoice);
                List<TaxSummary> taxSummarys = new ArrayList<>();
                purchaseDTO.setTaxSummaryList(taxSummarys);
                printDTO = getPrintDetails(purchaseDTO, purchaseInvoice);
                printDTO.setPiNo(purchaseInvoice.getPino());
                printDTO.setPiData(purchaseDTO);
                return printDTO;
            }
        }
        public NewPosPurchasePaymentTypes savePosPurchasePayments(PurchaseDTO purchaseDTO, PurchaseInvoice purchaseInvoice) {
            Gson gson = new Gson();
            NewPosPurchasePaymentTypes posPaymentTypes = new NewPosPurchasePaymentTypes();
            posPaymentTypes.setTotalAmount(purchaseDTO.getTotalTenderedAmount());
            posPaymentTypes.setTotalCashPayment(purchaseDTO.getTotalCashPayment());
            posPaymentTypes.setTotalCardPayment(purchaseDTO.getTotalCardPayment());
            posPaymentTypes.setTotalCoupon(purchaseDTO.getTotalCouponPayment());
            posPaymentTypes.setTotalVoucherPayment(purchaseDTO.getTotalVoucherPayment());
            posPaymentTypes.setTotaleWallet(purchaseDTO.getTotalEWalletPayment());
            if (purchaseDTO.getCashPayment() != null) {
                posPaymentTypes.setCashPayment(gson.toJson(purchaseDTO.getCashPayment().getMultiCashPaymentList()));
            }
            if (purchaseDTO.getVoucherPayment() != null) {
                posPaymentTypes.setVoucherPayment(gson.toJson(purchaseDTO.getVoucherPayment().getMultiVoucherPayments()));
            }
            if (purchaseDTO.getCreditPayment() != null) {
                posPaymentTypes.setCreditNotePayment(gson.toJson(purchaseDTO.getCreditPayment().getCardPaymentList()));
            }
            if (purchaseDTO.getCouponDetails() != null) {
                posPaymentTypes.setCouponPayment(gson.toJson(purchaseDTO.getCouponDetails().getCouponPaymentList()));
            }
            if (purchaseDTO.getBankPayment() != null) {
                posPaymentTypes.setChequePayment(gson.toJson(purchaseDTO.getBankPayment().getMultiBankPaymentList()));
            }
            if (purchaseDTO.getEwalletDetails() != null) {
                posPaymentTypes.setEwalletPayment(gson.toJson(purchaseDTO.getEwalletDetails().getEwalletDEtailsList()));
            }
            if (purchaseDTO.getGiftCardDetails() != null) {
                posPaymentTypes.setGiftCardPaymnet(gson.toJson(purchaseDTO.getGiftCardDetails().getGiftCardDetailsList()));
            }
            posPaymentTypes.setPino(purchaseInvoice.getPino());
            Supplier supplier=posSupplierRepository.findBySupplierName(purchaseDTO.getSupplierName());
            posPaymentTypes.setSupplierName(supplier.getSupplierName());
            posPurchasePaymentRepository.save(posPaymentTypes);
            return posPaymentTypes;
        }

        public static String getNextRefInvoice(String prefix, String nextRef) {
            StringBuilder sb = new StringBuilder();
            return sb.append(prefix).append(nextRef).toString();
        }

        private PurchaseInvoice createPOSSI(PurchaseInvoice purchaseInvoice, PurchaseDTO purchaseDTO, FormSetUp formSetUp) {
            if (StringUtils.isEmpty(purchaseInvoice.getPino())) {
                purchaseInvoice.setPino(getNextRefInvoice(formSetUp.getTypeprefix(), formSetUp.getNextref()));
                int incValue = Integer.parseInt(formSetUp.getNextref());
                purchaseDTO.setFormSetupNo(posService.getNextRefInvoice(formSetUp.getTypeprefix(), String.format("%08d", ++incValue)));
                formSetUp.setNextref(String.format("%08d", incValue));
                formSetupRepository.save(formSetUp);
            }
            purchaseInvoice.setPidate(posService.parseDate(purchaseDTO.getDateOfInvoice()));
            Supplier supplier = posSupplierRepository.findBySupplierName(purchaseDTO.getSupplierName());
//            purchaseInvoice.setSupplierId(supplier);
            purchaseInvoice.setSupplierName(supplier.getSupplierName());
            purchaseInvoice.setMemo(purchaseDTO.getMemo());
            purchaseInvoice.setPiStatus(purchaseDTO.getPiStatus());
            purchaseInvoice.setApprovalNumtoDate(String.valueOf(new Date()));
            purchaseInvoice.setSuppInvNo(purchaseDTO.getSupplierInvNo());
            if (StringUtils.pathEquals(purchaseDTO.getPiStatus(), "Draft")) {
                purchaseInvoice.setAdvancePayment("false");
                purchaseInvoice.setPiStatus("Draft");
                purchaseInvoice.setPosting("No");
            } else {
                purchaseInvoice.setAdvancePayment("false");
                purchaseInvoice.setPiStatus("Prepared");
                purchaseInvoice.setPosting("Yes");
            }
            purchaseInvoice.setTotalAmountEx(purchaseDTO.getTotalCheckOutamt() - (purchaseDTO.getTotalTaxAmt() + purchaseDTO.getCessTotalTaxAmt()));
            if (purchaseDTO.getTdsAmount() == null) purchaseDTO.setTdsAmount("0");
            if (purchaseDTO.getTcsAmount() == null) purchaseDTO.setTcsAmount("0");
            purchaseInvoice.setFlag("DirectPurchaseInvoice");
            purchaseInvoice.setCessTaxAmt(purchaseDTO.getCessTotalTaxAmt());
            purchaseInvoice.setPayableAmountTax(purchaseDTO.getTotalTaxAmt());
            purchaseInvoice.setTotalPayable(purchaseDTO.getTotalCheckOutamt());
            if (purchaseDTO.getAmountReturned() == 0 && purchaseDTO.getTotalTenderedAmount() == 0) {
                purchaseInvoice.setApBalance((purchaseDTO.getTotalCheckOutamt()));
            } else {
                if (purchaseDTO.getAmountReturned() < 0) {
                    purchaseInvoice.setApBalance((-purchaseDTO.getAmountReturned()));
                    purchaseInvoice.setTotalPaid(purchaseDTO.getTotalTenderedAmount());
                } else {
                    purchaseInvoice.setApBalance(0);
                    purchaseInvoice.setTotalPaid(purchaseDTO.getTotalTenderedAmount());
                }
            }
            purchaseInvoice.setTotalTaxAmount(purchaseDTO.getTotalTaxAmt());
            purchaseInvoice.setTaxInvoice(purchaseDTO.getTaxType());
            purchaseInvoice.setTotalAmount(purchaseDTO.getTotalCheckOutamt());
            purchaseInvoice.setTdsAmount(purchaseDTO.getTdsAmount());
            purchaseInvoice.setTcsAmount(purchaseDTO.getTcsAmount());
            return purchaseInvoice;
        }

        private List<PurchaseInvoiceDetails> createPOSSIDetails(PurchaseDTO purchaseDTO, PurchaseInvoice purchaseInvoice) {
            List<PurchaseInvoiceDetails> pidList = new ArrayList<>();
            for (SelectedItem selectedItem : purchaseDTO.getSelectedItemsList()) {
                PurchaseInvoiceDetails pid = new PurchaseInvoiceDetails();
//                pid.setPidetails(purchaseInvoice);
                pid.setpINo(purchaseInvoice.getPino());
                selectedItem.setBatchExpDate(selectedItem.getBatchExpDate());
                selectedItem.setPurchaseDate(purchaseInvoice.getPidate());
                selectedItem.setMessage(purchaseInvoice.getPino());
                if (selectedItem.getItemId() != 0) {
                    Item item = posItemRepository.findByItemName(selectedItem.getItemName());
//                    pid.setItemId(item);
                    pid.setItemCode(item.getItemCode());
                    pid.setItemName(item.getItemName());
                    pid.setItemDesc(item.getItemDesc());
                    item.setStock(item.getStock()+selectedItem.getQty());
                    itemRepository.save(item);
                }
                pid.setPrice(selectedItem.getUnitPrice());
                pid.setQtyOrdered(selectedItem.getQty());
                pid.setQtyReceived(selectedItem.getQty());
                pid.setQtyRemaining(selectedItem.getQty());
                pid.setBatchNo(selectedItem.getBatchNo());
                pid.setItemExpiryDate(selectedItem.getBatchExpDate());
                pid.setSerializableImeiNo(selectedItem.getSerializableIMEINo());
                pid.setSerializableNumbers(selectedItem.getSerializableNumbers());
                pid.setDiscPercent(0.00);
                pid.setDiscountconfigamount(selectedItem.getDiscountConfigAmt());
                pid.setDiscountAmount(selectedItem.getDiscountAmt());
                pid.setItemAmountExcTax(selectedItem.getAmtexclusivetax());
                Tax tax = posTaxRepository.findByTaxGroupAndTaxName("PURCHASE/INPUT",selectedItem.getTaxName());
                pid.setTaxName(tax.getTaxName());
                if(tax!=null){
                    pid.setTaxName(tax.getTaxName());
                    pid.setItemTaxPer(tax.getTax_Per());
                }
                pid.setItemTax(selectedItem.getTaxamt());
                pid.setItemAmountIncTax(selectedItem.getAmtinclusivetax());
                pidList.add(pid);
                pid.setCessTaxPer(selectedItem.getCess());
                Double cessTaxAmt = ((selectedItem.getAmtexclusivetax()) * (selectedItem.getCess())) / 100;
                DecimalFormat df = new DecimalFormat("#.##");
                pid.setCessTaxAmt(Double.valueOf(df.format(cessTaxAmt)));
                pid.setItemDesc(selectedItem.getItemDescription());
            }
            return pidList;
        }

        public PurchasePrintDTO getPrintDetails(PurchaseDTO purchaseDTO, PurchaseInvoice pi) {
            SimpleDateFormat sfmtDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sfmtTime = new SimpleDateFormat("HH:mm:ss");
            PurchasePrintDTO printDTO = new PurchasePrintDTO();
            printDTO.setDate(sfmtDate.format(pi.getPidate()));
            printDTO.setTime(sfmtTime.format(new Date()));
            Supplier supplier=posSupplierRepository.findBySupplierName(pi.getSupplierName());
            purchaseDTO.setSupplierName(supplier.getSupplierName());
            purchaseDTO.setSupplierAddress(supplier.getAddress());
            if (StringUtils.isEmpty(supplier.getStateName())) {
                purchaseDTO.setSupplierState("");
            } else {
                purchaseDTO.setSupplierState(supplier.getStateName());
            }
            purchaseDTO.setSupplierGst(supplier.getGstCode());
            purchaseDTO.setLocationSupplierName(supplier.getSupplierName());
            purchaseDTO.setLocationState(supplier.getStateName());
            purchaseDTO.setLocationAddress(supplier.getAddress());
            purchaseDTO.setLocationGst(supplier.getGstCode());
    //        FormSetUp formterms = posFormSetupRepository.findAllByTypename("DirectPurchaseInvoice");
    //        printDTO.setFooter(formterms.getTermsDesc());
            purchaseDTO.setSupplierEmail(supplier.getEmail());
            purchaseDTO.setPhoneNumber(supplier.getPhoneNumber1());
            purchaseDTO.setSupplierName(supplier.getSupplierName());
            printDTO.setTotalIncludingTax(pi.getTotalAmount());
            printDTO.setTaxAmt(pi.getPayableAmountTax());
            printDTO.setCessTaxAmt(pi.getCessTaxAmt());
            printDTO.setTotalExcludingTax(pi.getTotalAmountEx());
            printDTO.setTotalPaid(pi.getTotalPaid());
            printDTO.setBalance(pi.getApBalance());
            purchaseDTO.setPiNo(pi.getPino());
            purchaseDTO.setPiid(pi.getPiId());
            purchaseDTO.setSupplierInvNo(pi.getSuppInvNo());
            purchaseDTO.setSupplierInvNo(pi.getSuppInvNo());
            purchaseDTO.setItemCount(purchaseDTO.getSelectedItemsList().size());
            Map<Long, TaxSummary> taxMap = getTaxSummary(purchaseDTO.getSelectedItemsList());
            List<TaxSummary> taxSummarys = new ArrayList<>();
            taxSummarys.addAll(taxMap.values());
            purchaseDTO.setTaxSummaryList(taxSummarys);
            printDTO.setPiData(purchaseDTO);
            printDTO.setUserName(purchaseDTO.getUserName());
            if (supplier.getAddress() != null) {
                purchaseDTO.setBillToAddress(supplier.getAddress());
            }
            if (supplier.getShipToAddress() != null) {
                purchaseDTO.setShipToAddress(supplier.getShipToAddress());
            }
            if (supplier.getEmail() != null) {
                purchaseDTO.setEmail(supplier.getEmail());
            }
            if (supplier.getPhoneNumber1() != null) {
                purchaseDTO.setPhoneNumber(supplier.getPhoneNumber1());
            }
            return printDTO;
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
        private List<PurchaseReturnDetails> createReturnPIDetails(PurchaseDTO purchaseDTO, PurchaseReturn purchaseReturn) {
            List<PurchaseReturnDetails> purchaseReturnDetailsArrayList = new ArrayList<>();
            for (SelectedItem selectedItem : purchaseDTO.getSelectedItemsList()) {
                if(selectedItem.getReturnQty()>0) {
                    PurchaseReturnDetails purchaseReturnDetails = new PurchaseReturnDetails();
                    // TODO
                    purchaseReturnDetails.setPrdetails(purchaseReturn);
                    purchaseReturnDetails.setPrNo(purchaseReturn.getPrNo());
                    Item item = itemRepository.findByItemName(selectedItem.getItemName());
                    purchaseReturnDetails.setItemId(item);
                    purchaseReturnDetails.setItemName(item.getItemName());
                    purchaseReturnDetails.setItemDesc(selectedItem.getItemDescription());
                    purchaseReturnDetails.setItemCode(item.getItemCode());
                    purchaseReturnDetails.setPrice(selectedItem.getUnitPrice());
                    purchaseReturnDetails.setQty(selectedItem.getReturnQty());
                    purchaseReturnDetails.setItemAmountExcTax(selectedItem.getAmtexclusivetax());
                    Tax tax = posTaxRepository.findByTaxGroupAndTaxName("PURCHASE/INPUT", selectedItem.getTaxName());
                    if (tax != null) {
                        purchaseReturnDetails.setTax(tax);
                        purchaseReturnDetails.setTaxName(tax.getTaxName());
                        purchaseReturnDetails.setItemTaxPer(tax.getTax_Per());
                    }
                    PurchaseInvoiceDetails purchaseInvoiceDetails = purchaseInvoiceDetailsRepository.findAllByPINoAndItemName(purchaseReturn.getPurchaseInvoiceNo(), item.getItemName());
                    purchaseInvoiceDetails.setQtyRemaining(purchaseInvoiceDetails.getQtyRemaining() - selectedItem.getReturnQty());
                    if (purchaseInvoiceDetails.getQtyRemaining() == 0) {
                        purchaseInvoiceDetails.setPayStatus("Returned");
                    }
                    item.setStock(item.getStock() - selectedItem.getQty());
                    itemRepository.save(item);
                    purchaseInvoiceDetailsRepository.save(purchaseInvoiceDetails);
                    purchaseReturnDetails.setItemTax(selectedItem.getTaxamt());
                    purchaseReturnDetails.setItemAmountIncTax(selectedItem.getAmtinclusivetax());
                    purchaseReturnDetails.setRetStatus("Returned");
                    Double cessTaxAmt = ((selectedItem.getAmtexclusivetax()) * (selectedItem.getCess())) / 100;
                    DecimalFormat df = new DecimalFormat("#.##");
                    purchaseReturnDetails.setCessTaxAmt(Double.parseDouble((df.format(cessTaxAmt))));
                    purchaseReturnDetailsArrayList.add(purchaseReturnDetails);
                }
            }
            List<PurchaseInvoiceDetails> list=purchaseInvoiceDetailsRepository.findAllByPINo(purchaseReturn.getPurchaseInvoiceNo());
            int i=0;
            for(PurchaseInvoiceDetails purchaseInvoiceDetails:list){
                if(purchaseInvoiceDetails.getQtyRemaining()==0){
                    i++;
                }
            }
            if(i==list.size()){
                PurchaseInvoice purchaseInvoice=purchaseInvoiceRepository.findAllByPino(purchaseReturn.getPurchaseInvoiceNo());
                purchaseInvoice.setPiStatus("Returned");
                purchaseInvoiceRepository.save(purchaseInvoice);
            }
            return purchaseReturnDetailsArrayList;
        }

        public PurchaseDTO createReturnPI(PurchaseDTO purchaseDTO){
            PurchaseInvoice purchaseInvoice=purchaseInvoiceRepository.findAllByPino(purchaseDTO.getPiNo());
            PurchaseReturn purchaseReturn=new PurchaseReturn();
            FormSetUp formSetUp = formSetupRepository.findAllByTypename("PurchaseDebitNote");
            purchaseReturn.setPrNo(posService.getNextRefInvoice(formSetUp.getTypeprefix(), formSetUp.getNextref()));
            int incValue = Integer.parseInt(formSetUp.getNextref());
            purchaseDTO.setFormSetupNo(posService.getNextRefInvoice(formSetUp.getTypeprefix(), String.format("%08d", ++incValue)));
            formSetUp.setNextref(String.format("%08d", incValue));
            formSetupRepository.save(formSetUp);
            purchaseReturn.setPrDate(posService.parseDate(purchaseDTO.getDateOfInvoice()));
            Supplier supplier = posSupplierRepository.findBySupplierName(purchaseDTO.getSupplierName());
            purchaseReturn.setSupplierName(supplier.getSupplierName());
            if (purchaseDTO.getMemo() != null) {
                purchaseReturn.setMemo(purchaseDTO.getMemo());
            }
            purchaseReturn.setPurchaseInvoiceNo(purchaseInvoice.getPino());
            purchaseReturn.setStatus("Returned");
            purchaseReturn.setFlag("POS Purchase Invoice");
            purchaseReturn.setCessTaxAmt(purchaseDTO.getCessTotalTaxAmt());
            purchaseReturn.setTaxInvoice(purchaseDTO.getTaxType());
            purchaseReturn.setTotalTaxAmount(String.valueOf(purchaseDTO.getTotalTaxAmt()));
            List<PurchaseReturnDetails> purchaseReturnDetails = createReturnPIDetails(purchaseDTO, purchaseReturn);
            Map<Long, TaxSummary> taxMap = getTaxSummary(purchaseDTO.getSelectedItemsList());
            List<TaxSummary> taxSummarys = new ArrayList<>();
            taxSummarys.addAll(taxMap.values());
            purchaseDTO.setTaxSummaryList(taxSummarys);
            purchaseReturnRepository.save(purchaseReturn);
            purchaseReturnDetailsRepository.save(purchaseReturnDetails);
            return purchaseDTO;
        }

        public List<PurchaseDTO> getPurchaseList() {
            List<PurchaseInvoice> pur=new ArrayList<>();
            pur=purchaseInvoiceRepository.findAll();
            List<PurchaseDTO> purDTO= PosItemMapper.mapPurInvEntityToPojo(pur);
            return purDTO;
        }
    }
