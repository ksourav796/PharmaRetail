/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posEntities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Admin
 */
@Entity

public class InventoryMovement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long inventoryMovementId;
    private String inventoryMovementStatus;
    private String inventoryMovementName;
    private String inventoryMovementDesc;

    public Long getInventoryMovementId() {
        return inventoryMovementId;
    }

    public void setInventoryMovementId(Long inventoryMovementId) {
        this.inventoryMovementId = inventoryMovementId;
    }

    public String getInventoryMovementStatus() {
        return inventoryMovementStatus;
    }

    public void setInventoryMovementStatus(String inventoryMovementStatus) {
        this.inventoryMovementStatus = inventoryMovementStatus;
    }

    public String getInventoryMovementName() {
        return inventoryMovementName;
    }

    public void setInventoryMovementName(String inventoryMovementName) {
        this.inventoryMovementName = inventoryMovementName;
    }

    public String getInventoryMovementDesc() {
        return inventoryMovementDesc;
    }

    public void setInventoryMovementDesc(String inventoryMovementDesc) {
        this.inventoryMovementDesc = inventoryMovementDesc;
    }
}
