package com.hyva.posretail.sms;
import com.hyva.posretail.pos.posEntities.FormSetUp;
import com.hyva.posretail.pos.posEntities.SmsConfigurator;
import com.hyva.posretail.pos.posPojo.SmsConfiguratorDto;
import com.hyva.posretail.pos.posRespositories.PosFormSetupRepository;
import com.hyva.posretail.pos.posRespositories.smsConfiguratorRepository;
import com.hyva.posretail.pos.posService.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class MessageService {
    @Autowired
    PosFormSetupRepository posFormSetupRepository;
    @Autowired
    smsConfiguratorRepository smsConfiguratorRepository;

    @Transactional
    public String getMobNumber(SmsConfiguratorDto smsConfiguratorDto) throws IOException {
        FormSetUp formterms =posFormSetupRepository.findAllByTypename(smsConfiguratorDto.getType());
        SmsConfigurator smsConfigurator=smsConfiguratorRepository.findOne(formterms.getSmsId());
        if (StringUtils.equals(smsConfigurator.getEnableSms(),"true")) {
            SmsService.sendSms(smsConfiguratorDto.getInvoiceNo(), smsConfiguratorDto.getName(), smsConfiguratorDto.getPhoneNumber(),String.valueOf(smsConfiguratorDto.getInvAmt()), smsConfigurator.getMessage());
        }
        return smsConfiguratorDto.getType();
    }

    public SmsConfiguratorDto saveSmsConfig(SmsConfiguratorDto smsConfiguratorDto) {
        FormSetUp formsetup = posFormSetupRepository.findOne(smsConfiguratorDto.getFormsetupId());
        try {
            SmsConfigurator smsConfigurator =smsConfiguratorRepository.findOne(formsetup.getSmsId());
            smsConfigurator.setMessage(smsConfiguratorDto.getMessage());
            smsConfiguratorRepository.save(smsConfigurator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return smsConfiguratorDto;
    }
}
