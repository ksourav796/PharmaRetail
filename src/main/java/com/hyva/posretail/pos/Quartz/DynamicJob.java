package com.hyva.posretail.pos.Quartz;
import com.hyva.posretail.pos.posPojo.MailSchedulerData;
import com.hyva.posretail.pos.posService.MailService;
import com.hyva.posretail.pos.posService.SmsService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Map;
import javax.mail.MessagingException;


@Component
public class DynamicJob implements Job {
    private final Logger log = Logger.getLogger(getClass());
    @Autowired
    SmsService smsService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Map<String, Object> mergedJobDataMap = jobExecutionContext.getMergedJobDataMap().getWrappedMap();
        MailSchedulerData schedulerProps = (MailSchedulerData) mergedJobDataMap.get("jobData");
            Calendar cal = Calendar.getInstance();
            String[] monthName = {"January", "February",
                    "March", "April", "May", "June", "July",
                    "August", "September", "October", "November",
                    "December"};
            String month = monthName[cal.get(Calendar.MONTH)];
            String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH) + 1);

            String sentense = "Dear Parents," + "\n School fee for " + month + "  has to be paid before the " + day + "th of " + month + ". \n Ignore if you have paid already.\n\nThe Principle " +
                    "\nBrainyStars";
            String message = "Dear Parents," + " School fee for " + month + "  has to be paid before the " + day + "th of " + month + ".  Ignore if you have paid already." +
                    "The Principle " +
                    "BrainyStars";

//            if (StringUtils.isNotEmpty(stud.getFatherContactNo())) {
//                smsService.sendSms(stud.getFatherContactNo(), message);
//            }
            log.info("Executing Job = " + jobExecutionContext.getJobDetail().getKey());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TenantContext.setCurrentTenant(schedulerProps.getDbKeyword());
            String filename = null;

            try {
                MailService.sendMailWithAttachment(schedulerProps.getFromMail(),
                        schedulerProps.getToEmail(), "", "Brainy Stars",
                        sentense, schedulerProps.getReportType(),
                        outputStream.toByteArray(), filename);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }

    }
}
