/**
 * Created by harshitha on 18/06/06.
 */
package com.hyva.posretail.pos.posService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class SmsService {
    public String sendSms(String phoneNumber,String message) {
        try{
            URL url = new URL("http://sms.hyvaitsolutions.com/api/v4/?method=sms&api_key=A71210d6e04ce8f4edaba269004814b74&to=" + phoneNumber + "&sender=HVAGPS&message=" + message);
            URLConnection conn = url.openConnection();
            conn.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static String sendSms(String invoiceNo, String supplierName, String mobileNumber,String amt,String message) throws IOException {
        String sentance = message+"    Invoice No is   "+   invoiceNo  +"  You have to pay "  +  amt  + "   Rs to " + supplierName;
        URL url = new URL("http://sms.hyvaitsolutions.com/api/v4/?method=sms&api_key=A71210d6e04ce8f4edaba269004814b74&to=" + mobileNumber + "&sender=HVAGPS&message=" + sentance);
        URLConnection conn = url.openConnection();
        conn.getInputStream();
        return null;
    }
}
