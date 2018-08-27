package com.hyva.posretail.pos.posPojo;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by prasad on 7/1/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitOfMeasurementDTO {
    private Long unitOfMeasurementId;
    private String unitOfMeasurementName;
    private String unitOfMeasurementDescription;
    private String unitOfMeasurementStatus;

    public Long getUnitOfMeasurementId() {
        return unitOfMeasurementId;
    }

    public void setUnitOfMeasurementId(Long unitOfMeasurementId) {
        this.unitOfMeasurementId = unitOfMeasurementId;
    }

    public String getUnitOfMeasurementName() {
        return unitOfMeasurementName;
    }

    public void setUnitOfMeasurementName(String unitOfMeasurementName) {
        this.unitOfMeasurementName = unitOfMeasurementName;
    }

    public String getUnitOfMeasurementDescription() {
        return unitOfMeasurementDescription;
    }

    public void setUnitOfMeasurementDescription(String unitOfMeasurementDescription) {
        this.unitOfMeasurementDescription = unitOfMeasurementDescription;
    }

    public String getUnitOfMeasurementStatus() {
        return unitOfMeasurementStatus;
    }

    public void setUnitOfMeasurementStatus(String unitOfMeasurementStatus) {
        this.unitOfMeasurementStatus = unitOfMeasurementStatus;
    }
}
