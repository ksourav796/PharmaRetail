package com.hyva.posretail.pos.posPojo;

public class HsnDTO {
    private Long mscid;
    private String msiccode;
    private String descrip;
    private String status;
    private String locationId;
    private String useraccount_id;

    public Long getMscid() {
        return mscid;
    }

    public void setMscid(Long mscid) {
        this.mscid = mscid;
    }

    public String getMsiccode() {
        return msiccode;
    }

    public void setMsiccode(String msiccode) {
        this.msiccode = msiccode;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getUseraccount_id() {
        return useraccount_id;
    }

    public void setUseraccount_id(String useraccount_id) {
        this.useraccount_id = useraccount_id;
    }
}
