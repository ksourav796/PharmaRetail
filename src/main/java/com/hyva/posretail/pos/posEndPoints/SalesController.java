package com.hyva.posretail.pos.posEndPoints;

import com.hyva.posretail.pos.posPojo.RetailDTO;
import com.hyva.posretail.pos.posPojo.RetailPrintDTO;
import com.hyva.posretail.pos.posService.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    SalesService salesService;

    @RequestMapping(value = "/savePOSInvoice", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public RetailPrintDTO savePOSInvoice(@RequestBody RetailDTO retailDTO) {
        RetailPrintDTO rpdto = salesService.createBillingSalesInvoices(retailDTO,"Prepared");
        return rpdto;
    }
    @RequestMapping(value = "/returnSI", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public RetailDTO returnSI(@RequestBody RetailDTO retailDTO) {
        RetailDTO rpdto = salesService.createReturnSI(retailDTO);
        return rpdto;
    }

    @RequestMapping(value = "/saveDraftPOSInvoice", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public RetailPrintDTO saveDraftPOSInvoice(@RequestBody RetailDTO retailDTO) {
        RetailPrintDTO rpdto = salesService.createBillingSalesInvoices(retailDTO,"Draft");
        return rpdto;
    }

    @RequestMapping(value = "/getSalesInvoicesList", method = RequestMethod.POST)
    public List<RetailDTO> getSalesInvoicesList(@RequestParam(value = "status") String status,
                                                @RequestParam(value = "search") String search) {
        List<RetailDTO> list = salesService.getSalesInvoicesList(status,search);
        return list;
    }

    @RequestMapping(value = "/getSalesInvoice", method = RequestMethod.POST)
    public RetailDTO getSalesInvoice(@RequestParam(value = "invoiceNo") String invoiceNo) {
        RetailDTO retailDTO = salesService.getSalesInvoice(invoiceNo);
        return retailDTO;
    }
    @RequestMapping(value = "/deletePOSBilling", method = RequestMethod.POST, produces = "text/plain")
    public String deletePOSBilling(@RequestParam("invoiceNo") String invoiceNo) {
        salesService.deletePOSBillingInvoice(invoiceNo);
        return "Deleted Successfully";
    }

    @RequestMapping(value = "/cancelSalesInvoice", method = RequestMethod.POST, produces = "text/plain")
    public String cancelInvoice(@RequestParam(value = "invoiceNo") String invoiceNo) {
        salesService.makeCancelledSalesInvoice(invoiceNo);
        return "Cancelled SuccessFully";
    }
    @RequestMapping(value = "/getReportSalesList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getReportSalesList() {
        return ResponseEntity.status(200).body(salesService.getSalesList());
    }

}
