package com.hyva.posretail.pusher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
public class PusherService {
    @Autowired
    private KafkaPusherCreds kafkaPusherCreds;

    @Transactional
    public String  SavePusher(String jsonInString,String Url,String typeDoc) throws JSONException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("typeDoc",typeDoc);
        objectNode.put("Url",Url);
        JSONObject jsonObject = new JSONObject(jsonInString);
        objectNode.putPOJO("jsonData",jsonObject);
        objectNode.put("from_reg","58C2B6672FCC4282" );
        objectNode.put("to_reg", "9616DBD001234C48");
        objectNode.put("typeDoc", "SIN");
        objectNode.put("typeFlag","Purchase");
        objectNode.put("status","Accepted");
        objectNode.put("organizationId","21");
        objectNode.put("branchId","3");
        String jsonCompleteData = objectNode.toString();
        //  Spring Rest Client Call
           String strUrl="http://localhost:8090/pusher/savePusher";
        String Url1="https://www.hiaccounts.in/karts/edms/api/Desktop/filter";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonCompleteData, headers);
        ResponseEntity<String> responseEntity=null;
        try {
            responseEntity = restTemplate.exchange(Url1, HttpMethod.POST, entity, String.class);
            // responseEntity = restTemplate.exchange("https://www.hiaccounts.in/karts/edms/api/Desktop/filter", HttpMethod.POST, entity, String.class);
        }catch(Exception e){
            return "Success";
        }
        return jsonInString;
    }


}



//
//package com.hyva.posretail.pos.posService;
//        import com.hyva.posretail.pos.posPojo.AdmissionPojo;
//        import com.hyva.posretail.pos.posPojo.StudentPojo;
//        import org.springframework.http.*;
//        import org.springframework.stereotype.Service;
//        import org.springframework.web.client.RestTemplate;
//
//@Service
//public class PusherService {
//    public String  SavePusher(StudentPojo saveStudentDetails) {
//        AdmissionPojo admissionPojo=new AdmissionPojo();
//        admissionPojo.setDOB("-----");
//        admissionPojo.setFk_levelid(saveStudentDetails.getGradeMaster().getGradeName());
////        admissionPojo.setFk_classid();
////        admissionPojo.setFk_organisationid();
////        admissionPojo.setUid();
//        admissionPojo.setFk_branchid("MainBranch");
//        admissionPojo.setAdmission_date(saveStudentDetails.getDateOfAdmission().toString());
//        admissionPojo.setAdmissionno(saveStudentDetails.getAdmissionFormNo());
//        admissionPojo.setStudentname(saveStudentDetails.getStudentName());
//        admissionPojo.setBloodgroup(saveStudentDetails.getBloodGroup());
//        admissionPojo.setAcademicyear(saveStudentDetails.getAcdYearId().toString());
//        admissionPojo.setJoiningdate(saveStudentDetails.getDateOfJoining().toString());
//        admissionPojo.setFathersname(saveStudentDetails.getFatherName());
//        admissionPojo.setFathersemail(saveStudentDetails.getFatherEmailId());
//        admissionPojo.setFathersmobile(saveStudentDetails.getFatherContactNo());
//        admissionPojo.setFathersoccupation(saveStudentDetails.getFatherOccupation());
//        admissionPojo.setMothername(saveStudentDetails.getMotherName());
//        admissionPojo.setMotheremail(saveStudentDetails.getMotherEmailId());
//        admissionPojo.setMothermobile(saveStudentDetails.getMotherContactNo());
//        admissionPojo.setMotheroccupation(saveStudentDetails.getMotherOccupation());
//        admissionPojo.setPrimarycontact(saveStudentDetails.getPrimaryContactNo());
//        //        admissionPojo.setPlace(saveStudentDetails.get);
////        admissionPojo.setGender();
////        admissionPojo.setStatus();
//        String jsonInString = saveStudentDetails.toString();
//        String Url = "https://www.hiaccounts.in/karts/edms/api/School/admission";
//        String strUrl = "http://localhost:8090/pusher/savePusher";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<String>(jsonInString, headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange(Url, HttpMethod.POST, entity, String.class);
//        return  jsonInString;
//    }
//
//}




