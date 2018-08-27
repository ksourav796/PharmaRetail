package com.hyva.posretail.pos.posEndPoints;

import com.hyva.posretail.pos.posPojo.PurchaseDTO;
import com.hyva.posretail.pos.posPojo.PurchasePrintDTO;
import com.hyva.posretail.pos.posService.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;
    @RequestMapping(value = "/save",method = RequestMethod.POST,consumes="application/json")
    public PurchasePrintDTO save( @RequestBody PurchaseDTO purchaseDTO) {
        PurchasePrintDTO rpdto = purchaseService.savePurchaseInvoice(purchaseDTO,"Prepared");
        rpdto.setResult("SUCCESS");
        return rpdto;
    }
    @RequestMapping(value = "/saveDraftPurchaseInvoice",method = RequestMethod.POST,consumes="application/json")
    public PurchasePrintDTO saveDraftPurchaseInvoice(@RequestBody PurchaseDTO purchaseDTO) {
        PurchasePrintDTO rpdto = purchaseService.savePurchaseInvoice(purchaseDTO,"Draft");
        rpdto.setResult("SUCCESS");
        return rpdto;
    }
    @RequestMapping(value = "/getPurchaseInvoicesList",method = RequestMethod.POST)
    public List<PurchaseDTO> getPurchaseInvoicesList(@RequestParam(value = "status") String status,
                                                     @RequestParam(value = "search") String search) {
        List<PurchaseDTO> list = purchaseService.getPurchaseInvoicesList(status,search);
        return list;
    }
    @RequestMapping(value = "/deletePOSPurchaseBilling", method = RequestMethod.POST, produces = "text/plain")
    public String deletePOSBilling(@RequestParam("invoiceNo") String invoiceNo) {
        purchaseService.deletePOSPurchaseBillingInvoice(invoiceNo);
        return "Deleted Successfully";
    }
    @RequestMapping(value = "/getPurchaseInvoice",method = RequestMethod.POST)
    public PurchaseDTO getPurchaseInvoice(@RequestParam(value = "no")String no) {
        PurchaseDTO purchaseDTO = purchaseService.getPurchaseInvoice(no);
        return purchaseDTO;
    }
    @RequestMapping(value= "/cancelPurchaseInvoice", method = RequestMethod.POST,produces = "text/plain")
    public String cancelInvoice(@RequestParam(value = "no")String  no){
        purchaseService.makeCancelledPurchaseInvoice(no);
        return "Cancelled SuccessFully";
    }
    @RequestMapping(value = "/returnPI", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public PurchaseDTO returnPI(@RequestBody PurchaseDTO purchaseDTO) {
        PurchaseDTO rpdto = purchaseService.createReturnPI(purchaseDTO);
        return rpdto;
    }
    @RequestMapping(value = "/getReportPurchaseList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getReportPurchaseList() {
        return ResponseEntity.status(200).body(purchaseService.getPurchaseList());
    }


}
