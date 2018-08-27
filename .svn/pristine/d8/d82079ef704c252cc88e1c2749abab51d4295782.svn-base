package com.hyva.posretail.pos.posEntities;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Admin
 */
@Entity
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long itemId;
    private String itemCode;
    private String itemAccountCode;
    private String itemName;
    private String itemDesc;
    private String certificateno;
    private String fileName;
    private String serializableStatus;
//    @OneToOne
//    @JoinColumn(name = "IdItemCategory")
//    private ItemCategory idItemCategory;
    private String categoryName;
    private String typeName;
    private String uomName;
    private String brandName;
    private String ipTaxName;
    private String opTaxName;
    private String countTypeName;
    private String msicName;
//    @OneToOne
//    @JoinColumn(name = "idItemType")
//    private ItemType idItemType;
//    @OneToOne
//    @JoinColumn(name = "inputtax")
//    private Tax inputtax;
//    @OneToOne
//    @JoinColumn(name = "outputTax")
//    private Tax outputTax;
//    @OneToOne
//    @JoinColumn(name = "countType")
//    private InventoryMovement countType;
//    @OneToOne
//    @JoinColumn(name = "idItemUOM")
//    private UnitOfMeasurement idItemUOM;
    private boolean editableDesc;
    private boolean excludeSales;
    private String imageFile;
    private String itemStatus;
    private double stock;
//    @ManyToOne
//    private Msiccode msiccomid;
    private String productionName;
    private double itemQuantityProduction;
    private double itemPrice;
    @Column(name = "logo")/*Multiple definition so used*/
    private String inclusiveJSON;
//    @OneToOne
//    private ItemBrandName brand;
    private float CESS;
    private String serviceDescription;
    private double salesPrice;
    private double purchasePrice;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemAccountCode() {
        return itemAccountCode;
    }

    public void setItemAccountCode(String itemAccountCode) {
        this.itemAccountCode = itemAccountCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getCertificateno() {
        return certificateno;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSerializableStatus() {
        return serializableStatus;
    }

    public void setSerializableStatus(String serializableStatus) {
        this.serializableStatus = serializableStatus;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getIpTaxName() {
        return ipTaxName;
    }

    public void setIpTaxName(String ipTaxName) {
        this.ipTaxName = ipTaxName;
    }

    public String getOpTaxName() {
        return opTaxName;
    }

    public void setOpTaxName(String opTaxName) {
        this.opTaxName = opTaxName;
    }

    public String getCountTypeName() {
        return countTypeName;
    }

    public void setCountTypeName(String countTypeName) {
        this.countTypeName = countTypeName;
    }

    public String getMsicName() {
        return msicName;
    }

    public void setMsicName(String msicName) {
        this.msicName = msicName;
    }

    public boolean isEditableDesc() {
        return editableDesc;
    }

    public void setEditableDesc(boolean editableDesc) {
        this.editableDesc = editableDesc;
    }

    public boolean isExcludeSales() {
        return excludeSales;
    }

    public void setExcludeSales(boolean excludeSales) {
        this.excludeSales = excludeSales;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public double getItemQuantityProduction() {
        return itemQuantityProduction;
    }

    public void setItemQuantityProduction(double itemQuantityProduction) {
        this.itemQuantityProduction = itemQuantityProduction;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getInclusiveJSON() {
        return inclusiveJSON;
    }

    public void setInclusiveJSON(String inclusiveJSON) {
        this.inclusiveJSON = inclusiveJSON;
    }

    public float getCESS() {
        return CESS;
    }

    public void setCESS(float CESS) {
        this.CESS = CESS;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}