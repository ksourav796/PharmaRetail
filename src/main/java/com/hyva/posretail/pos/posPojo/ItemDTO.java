/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.posretail.pos.posPojo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/**
 *
 * @author admin
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO {

    private Long itemId;
    private String itemCode;
    private String itemName;
    private String ItemDesc;
    private Long taxId;
    private Long outputTaxId;
    private double stock;
    private double discountAmountRpercent;
    private boolean isDiscountInPercent;
    private double salesPrice;
    private double purchasePrice;
    private String itemTypeName;
    private String itemCategoryName;
    private String brandName;
    private String Status;
    private String attributeJSON;
    private double unitPrice;
    private double unitPriceIn;
    private String serializableItemId;
    private String serializableStatus;
    private String certificateno;
    private String countType;
    private Long itemCategoryId;
    private Long itemTypeId;
    private Long UnitOfMeasurementId;
    private Long mscid;
    private Long inputTaxId;
    private String qty;
    private Long brandId;
    private Long doId;
    private Long inventoryMovementId;
    private String inventoryMovementName;
    private String mrp;
    private String seializeNo;
    List<ItemCategoryDTO> itemCategoryDTOList;
    List<ItemTypeDTO> itemTypeDTOList;
    List<ItemUOMTypeDTO> itemUOMTypeDTOList;
    List<ItemMSICDTO> itemMSICDTOList;
    List<ItemBrandDTO> itemBrandDTOList;
    List<ItemCountTypeDTO> itemCountTypeDTOList;
    List<ItemIPTaxDTO> itemIPTaxDTOList;
    List<ItemOPTaxDTO> itemOPTaxDTOList;
    List<TaxDTO> taxList;
    public double discountAmt;
    public String type;
    private String cartStatus;
    private String cartValue;
    private String productionName;
    private String serviceDescription;

    public List<TaxDTO> getTaxList() {
        return taxList;
    }

    public void setTaxList(List<TaxDTO> taxList) {
        this.taxList = taxList;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Long getOutputTaxId() {
        return outputTaxId;
    }

    public void setOutputTaxId(Long outputTaxId) {
        this.outputTaxId = outputTaxId;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getDiscountAmountRpercent() {
        return discountAmountRpercent;
    }

    public void setDiscountAmountRpercent(double discountAmountRpercent) {
        this.discountAmountRpercent = discountAmountRpercent;
    }

    public boolean isDiscountInPercent() {
        return isDiscountInPercent;
    }

    public void setDiscountInPercent(boolean discountInPercent) {
        isDiscountInPercent = discountInPercent;
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

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAttributeJSON() {
        return attributeJSON;
    }

    public void setAttributeJSON(String attributeJSON) {
        this.attributeJSON = attributeJSON;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getUnitPriceIn() {
        return unitPriceIn;
    }

    public void setUnitPriceIn(double unitPriceIn) {
        this.unitPriceIn = unitPriceIn;
    }

    public String getSerializableItemId() {
        return serializableItemId;
    }

    public void setSerializableItemId(String serializableItemId) {
        this.serializableItemId = serializableItemId;
    }

    public String getSerializableStatus() {
        return serializableStatus;
    }

    public void setSerializableStatus(String serializableStatus) {
        this.serializableStatus = serializableStatus;
    }

    public String getCertificateno() {
        return certificateno;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public Long getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(Long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public Long getUnitOfMeasurementId() {
        return UnitOfMeasurementId;
    }

    public void setUnitOfMeasurementId(Long unitOfMeasurementId) {
        UnitOfMeasurementId = unitOfMeasurementId;
    }

    public Long getMscid() {
        return mscid;
    }

    public void setMscid(Long mscid) {
        this.mscid = mscid;
    }

    public Long getInputTaxId() {
        return inputTaxId;
    }

    public void setInputTaxId(Long inputTaxId) {
        this.inputTaxId = inputTaxId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getDoId() {
        return doId;
    }

    public void setDoId(Long doId) {
        this.doId = doId;
    }

    public Long getInventoryMovementId() {
        return inventoryMovementId;
    }

    public void setInventoryMovementId(Long inventoryMovementId) {
        this.inventoryMovementId = inventoryMovementId;
    }

    public String getInventoryMovementName() {
        return inventoryMovementName;
    }

    public void setInventoryMovementName(String inventoryMovementName) {
        this.inventoryMovementName = inventoryMovementName;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getSeializeNo() {
        return seializeNo;
    }

    public void setSeializeNo(String seializeNo) {
        this.seializeNo = seializeNo;
    }

    public List<ItemCategoryDTO> getItemCategoryDTOList() {
        return itemCategoryDTOList;
    }

    public void setItemCategoryDTOList(List<ItemCategoryDTO> itemCategoryDTOList) {
        this.itemCategoryDTOList = itemCategoryDTOList;
    }

    public List<ItemTypeDTO> getItemTypeDTOList() {
        return itemTypeDTOList;
    }

    public void setItemTypeDTOList(List<ItemTypeDTO> itemTypeDTOList) {
        this.itemTypeDTOList = itemTypeDTOList;
    }

    public List<ItemUOMTypeDTO> getItemUOMTypeDTOList() {
        return itemUOMTypeDTOList;
    }

    public void setItemUOMTypeDTOList(List<ItemUOMTypeDTO> itemUOMTypeDTOList) {
        this.itemUOMTypeDTOList = itemUOMTypeDTOList;
    }

    public List<ItemMSICDTO> getItemMSICDTOList() {
        return itemMSICDTOList;
    }

    public void setItemMSICDTOList(List<ItemMSICDTO> itemMSICDTOList) {
        this.itemMSICDTOList = itemMSICDTOList;
    }

    public List<ItemBrandDTO> getItemBrandDTOList() {
        return itemBrandDTOList;
    }

    public void setItemBrandDTOList(List<ItemBrandDTO> itemBrandDTOList) {
        this.itemBrandDTOList = itemBrandDTOList;
    }

    public List<ItemCountTypeDTO> getItemCountTypeDTOList() {
        return itemCountTypeDTOList;
    }

    public void setItemCountTypeDTOList(List<ItemCountTypeDTO> itemCountTypeDTOList) {
        this.itemCountTypeDTOList = itemCountTypeDTOList;
    }

    public List<ItemIPTaxDTO> getItemIPTaxDTOList() {
        return itemIPTaxDTOList;
    }

    public void setItemIPTaxDTOList(List<ItemIPTaxDTO> itemIPTaxDTOList) {
        this.itemIPTaxDTOList = itemIPTaxDTOList;
    }

    public List<ItemOPTaxDTO> getItemOPTaxDTOList() {
        return itemOPTaxDTOList;
    }

    public void setItemOPTaxDTOList(List<ItemOPTaxDTO> itemOPTaxDTOList) {
        this.itemOPTaxDTOList = itemOPTaxDTOList;
    }

    public double getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(double discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(String cartStatus) {
        this.cartStatus = cartStatus;
    }

    public String getCartValue() {
        return cartValue;
    }

    public void setCartValue(String cartValue) {
        this.cartValue = cartValue;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
}