/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posEntities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author Admin
 */
@Entity

public class UnitOfMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
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
