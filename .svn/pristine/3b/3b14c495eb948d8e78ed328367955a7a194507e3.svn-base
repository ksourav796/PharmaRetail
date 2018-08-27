package com.hyva.posretail.sms;

import com.hyva.posretail.pos.posPojo.SmsConfiguratorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/sms")
public class MessageController {
    @Autowired
    MessageService messageService;

    @RequestMapping(value= "/sendSMSInvoice", method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    public ResponseEntity sendSMSInvoice(@RequestBody SmsConfiguratorDto smsConfiguratorDto) throws IOException {
        String purchaseInvoiceObj = messageService.getMobNumber(smsConfiguratorDto);
        return ResponseEntity.status(200).body(purchaseInvoiceObj);
    }
    @RequestMapping(value = "/saveSms",method = RequestMethod.POST,consumes = "application/json",produces="application/json")
    public ResponseEntity saveSms(@RequestBody SmsConfiguratorDto sms ) {
        return ResponseEntity.status(200).body(messageService.saveSmsConfig(sms));
    }
}
