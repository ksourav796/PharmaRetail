package com.hyva.posretail.pos.posEndPoints;

import com.hyva.posretail.pos.posEntities.*;
import com.hyva.posretail.pos.posPojo.*;
import com.hyva.posretail.pos.posService.PosService;
import com.hyva.posretail.pos.posPojo.EntityResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/pos")
public class PosController {
    @Autowired
    PosService posService;

    @RequestMapping(value = "/login", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse login(@RequestBody User credentials) throws Exception {
        String accessToken = "12345";
        if (StringUtils.isBlank(credentials.getEmail()) || StringUtils.isBlank(credentials.getUserName()) || StringUtils.isBlank(credentials.getPasswordUser())) {
            return new EntityResponse(HttpStatus.OK.value(), "Invalid User");
        }
        return new EntityResponse(HttpStatus.OK.value(), "success", credentials);
    }

    @RequestMapping(value = "/saveLoginDetails", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User saveLoginDetails(@RequestBody PosUserPojo posUserPojo) {
        return posService.saveUserDetails(posUserPojo);
    }

    @RequestMapping(value = "/getItemCategoryList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getItemCategoryList(@RequestParam(value = "type", required = false) String type,
                                              @RequestParam(value="searchText",required = false)String searchText) {
        return ResponseEntity.status(200).body(posService.getItemCategoryList(type,searchText));
    }
//    @RequestMapping(value = "/getPaginatedItemCategoryList", method = RequestMethod.POST, produces = "application/json")
//    public ResponseEntity getPaginatedItemCategoryList(@RequestParam(value = "type", required = false) String type,@RequestParam(value = "searchText",required = false) String searchText,
//                                                       @RequestBody BasePojo basePojo) {
//        return ResponseEntity.status(200).body(posService.getPaginatedItemCategoryList(type,searchText,basePojo));
//    }
    @RequestMapping(value = "/saveCategory", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveCategory(@RequestBody ItemCategoryDTO itemCategory) {
        return ResponseEntity.status(200).body(posService.SaveCategory(itemCategory));
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST, produces = "application/json")
    public void deleteCategory(@RequestParam(value = "id", required = false) Long id) {
        posService.deleteCategory(id);
    }

    @RequestMapping(value = "/editCategory", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editCategory(@RequestParam(value = "catName", required = false) String name) {
        return ResponseEntity.status(200).body(posService.editCategory(name));
    }

    @RequestMapping(value = "/userValidate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public User userValidate(@RequestBody PosUserPojo posUserPojo) {
        return posService.userValidate(posUserPojo);
    }

    @RequestMapping(value = "/getUserDetailsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityResponse getUserDetailsList() {
        List<PosUserPojo> posUserPojos = posService.sassUserList();
        return new EntityResponse(HttpStatus.OK.value(), " success", posUserPojos);
    }

    @RequestMapping(value = "/getStateList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getStateList(@RequestParam(value = "type", required = false) String type,
                                       @RequestParam(value = "searchText",required = false) String searchText) {
        return ResponseEntity.status(200).body(posService.getStateList(type,searchText));
    }
    @RequestMapping(value = "/getPaginatedStateList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedStateList(@RequestParam(value = "type", required = false) String type,
                                       @RequestParam(value = "searchText",required = false) String searchText,
                                       @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedStateList(type,basePojo,searchText));
    }

    @RequestMapping(value = "/saveState", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveState(@RequestBody StateDTO state) {
        return ResponseEntity.status(200).body(posService.saveState(state));
    }

    @RequestMapping(value = "/deleteState", method = RequestMethod.POST, produces = "application/json")
    public void deleteState(@RequestParam(value = "stateName", required = false) String  stateName) {
        posService.deleteState(stateName);
    }

    @RequestMapping(value = "/editState", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editState(@RequestParam(value = "stateName", required = false) String  stateName) {
        return ResponseEntity.status(200).body(posService.editState(stateName));
    }
    @RequestMapping(value = "/getCountryList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getCountryList(@RequestParam(value = "type", required = false) String type,
     @RequestParam(value = "searchText",required = false) String searchText) {
        return ResponseEntity.status(200).body(posService.getCountryList(type,searchText));
    }
    @RequestMapping(value = "/getPaginatedCountryList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedCountryList(@RequestParam(value = "type", required = false) String type,
                                                  @RequestParam(value = "searchText",required = false) String searchText,
                                                  @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedCountryList(type,basePojo,searchText));
    }
    @RequestMapping(value = "/saveCountry", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveCountry(@RequestBody CountryDTO countryDTO) {
        return ResponseEntity.status(200).body(posService.saveCountry(countryDTO));
    }

    @RequestMapping(value = "/deleteCountry", method = RequestMethod.POST, produces = "application/json")
    public void deleteCountry(@RequestParam(value = "countryName", required = false) String countryName) {
        posService.deleteCountry(countryName);
    }

    @RequestMapping(value = "/editCountry", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editCountry(@RequestParam(value = "countryName", required = false) String countryName) {
        return ResponseEntity.status(200).body(posService.editCountry(countryName));
    }
    @RequestMapping(value = "/getCurrencyList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getCurrencyList(@RequestParam(value = "type", required = false) String type,
                                          @RequestParam(value = "searchText",required = false) String searchText) {
        return ResponseEntity.status(200).body(posService.getCurrencyList(type,searchText));
    }
    @RequestMapping(value = "/getPaginatedCurrencyList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedCurrencyList(@RequestParam(value = "type", required = false) String type,
                                                  @RequestParam(value = "searchText",required = false) String searchText,
                                                  @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedCurrencyList(type,basePojo,searchText));
    }
    @RequestMapping(value = "/saveCurrency", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveCurrency(@RequestBody CurrencyDTO currencyDTO) {
        return ResponseEntity.status(200).body(posService.saveCurrency(currencyDTO));
    }

    @RequestMapping(value = "/deleteCurrency", method = RequestMethod.POST, produces = "application/json")
    public void deleteCurrency(@RequestParam(value = "currencyName", required = false) String currencyName) {
        posService.deleteCurrency(currencyName);
    }

    @RequestMapping(value = "/editCurrency", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editCurrency(@RequestParam(value = "currencyName", required = false) String currencyName) {
        return ResponseEntity.status(200).body(posService.editCurrency(currencyName));
    }
    @RequestMapping(value = "/getSupplierList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getSupplierList(@RequestParam(value = "type", required = false) String type,
                                          @RequestParam(value = "searchText",required = false) String searchText) {
        return ResponseEntity.status(200).body(posService.getSupplierList(type,searchText));
    }
    @RequestMapping(value = "/getPaginatedSupplierList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedSupplierList(@RequestParam(value = "type", required = false) String type,
                                                   @RequestParam(value = "searchText",required = false) String searchText,
                                                   @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedSupplierList(type,basePojo,searchText));
    }
    @RequestMapping(value = "/saveSupplier", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        return ResponseEntity.status(200).body(posService.saveSupplier(supplierDTO));
    }

    @RequestMapping(value = "/deleteSupplier", method = RequestMethod.POST, produces = "application/json")
    public void deleteSupplier(@RequestParam(value = "id", required = false) Long id) {
        posService.deleteSupplier(id);
    }

    @RequestMapping(value = "/editSupplier", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editSupplier(@RequestParam(value = "supplierName", required = false) String supplierName) {
        return ResponseEntity.status(200).body(posService.editSupplier(supplierName));
    }
    @RequestMapping(value = "/getCustomerList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getCustomerList(@RequestParam(value = "type", required = false) String type,
                                          @RequestParam(value = "searchText",required = false) String searchText) {
        return ResponseEntity.status(200).body(posService.getCustomerList(type,searchText));
    }
    @RequestMapping(value = "/getPaginatedCustomerList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedCustomerList(@RequestParam(value = "type", required = false) String type,
                                                   @RequestParam(value = "searchText",required = false) String searchText,
                                                   @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedCustomerList(type,basePojo,searchText));
    }
    @RequestMapping(value = "/saveCustomer", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(200).body(posService.saveCustomer(customerDTO));
    }

    @RequestMapping(value = "/deleteCustomer", method = RequestMethod.POST, produces = "application/json")
    public void deleteCustomer(@RequestParam(value = "id", required = false) Long id) {
        posService.deleteCustomer(id);
    }

    @RequestMapping(value = "/editCustomer", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editCustomer(@RequestParam(value = "customerName", required = false) String customerName) {
        return ResponseEntity.status(200).body(posService.editCustomer(customerName));
    }
    @RequestMapping(value = "/getAllLists", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity  getAllLists() {
        return ResponseEntity.status(200).body(posService.getAllLists());
    }
    @RequestMapping(value = "/getCountryState", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getCountryState(@RequestParam(value = "countryName", required = false) String countryName) {
        return ResponseEntity.status(200).body(posService.getCountryState(countryName));
    }
    @RequestMapping(value = "/getUomList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getUomList(@RequestParam(value = "type", required = false) String type,
                                     @RequestParam(value = "searchText",required = false) String searchText) {
        return ResponseEntity.status(200).body(posService.getUOMList(type,searchText));
    }
    @RequestMapping(value = "/getPaginatedUomList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedUomList(@RequestParam(value = "type", required = false) String type,
                                              @RequestParam(value = "searchText", required = false) String searchText,
                                              @RequestBody BasePojo basepojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedUOMList(type,searchText,basepojo));
    }

    @RequestMapping(value = "/getPaginatedCategoryList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedCategoryList(@RequestParam(value = "type", required = false) String type,
                                              @RequestParam(value = "searchText", required = false) String searchText,
                                              @RequestBody BasePojo basepojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedCategoryList(type,searchText,basepojo));
    }


    @RequestMapping(value = "/saveUom", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveUom(@RequestBody UnitOfMeasurementDTO uomDTO) {
        return ResponseEntity.status(200).body(posService.saveUom(uomDTO));
    }

    @RequestMapping(value = "/deleteUom", method = RequestMethod.POST, produces = "application/json")
    public void deleteUom(@RequestParam(value = "unitOfMeasurementName", required = false) String unitOfMeasurementName) {
        posService.deleteUom(unitOfMeasurementName);
    }
    @RequestMapping(value = "/editUom", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editUom(@RequestParam(value = "unitOfMeasurementName", required = false) String unitOfMeasurementName) {
        return ResponseEntity.status(200).body(posService.editUom(unitOfMeasurementName));
    }
    @RequestMapping(value = "/getBrandList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getBrandList(@RequestParam(value = "type", required = false) String type,
                                       @RequestParam(value = "searchText",required = false) String searchText
                                      ) {
        return ResponseEntity.status(200).body(posService.getBrandList(type,searchText));
    }
    @RequestMapping(value = "/getPaginatedBrandList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedBrandList(@RequestParam(value = "type", required = false) String type,@RequestParam(value = "searchText",required = false) String searchText,
                                       @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedBrandList(type,searchText,basePojo));
    }
    @RequestMapping(value = "/saveBrand", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveBrand(@RequestBody ItemBrandDTO brandDTO) {
        return ResponseEntity.status(200).body(posService.saveBrand(brandDTO));
    }


//    @RequestMapping(value = "/getcategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public EntityResponse getcategory() {
//        List<ItemCategoryDTO> categoryDTOS = posService.getcategory1();
//        return new EntityResponse(HttpStatus.OK.value(), " success"); }

    @RequestMapping(value = "/saveMail", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity saveMail(@RequestBody MailDTO saveMailDetails) {
        MailDTO camDTO = null;
        camDTO = posService.createSaveMailDetails(saveMailDetails);
        return ResponseEntity.status(200).body(camDTO);
    }

    @RequestMapping(value = "/getPaginatedMailList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedMailList() {
        return ResponseEntity.status(200).body(posService.getMailList());
    }
    @RequestMapping(value = "/editMail", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editMail(@RequestParam(value = "userName", required = false)String  userName) {
        return ResponseEntity.status(200).body(posService.editMail(userName));
    }

    @RequestMapping(value = "/deleteBrand", method = RequestMethod.POST, produces = "application/json")
    public void deleteBrand(@RequestParam(value = "brandName", required = false) String brandName) {
        posService.deleteBrand(brandName);
    }

    @RequestMapping(value = "/deleteMail", method = RequestMethod.POST, produces = "application/json")
    public void deleteMail(@RequestParam(value = "userName", required = false) String userName) {
        posService.deleteMail(userName);
    }
    @RequestMapping(value = "/editBrand", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editBrand(@RequestParam(value = "brandName", required = false)String  brandName) {
        return ResponseEntity.status(200).body(posService.editBrand(brandName));
    }
    @RequestMapping(value = "/getTaxList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getTaxList(@RequestParam(value = "type", required = false) String type,
                                     @RequestParam(value = "searchText",required = false) String searchText) {
        return ResponseEntity.status(200).body(posService.getTaxList(type,searchText));
    }
    @RequestMapping(value = "/getPaginatedTaxList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedTaxList(@RequestParam(value = "type", required = false) String type,
                                              @RequestParam(value="searchText",required = false)String searchText,
                                              @RequestBody BasePojo basepojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedTaxList(type,basepojo,searchText));
    }
    @RequestMapping(value = "/getPageLoadData", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPageLoadData() {
        return ResponseEntity.status(200).body(posService.getPageLoadData());
    }
    @RequestMapping(value = "/saveTax", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveTax(@RequestBody ItemTaxDTO taxDTO) {
        return ResponseEntity.status(200).body(posService.saveTax(taxDTO));
    }

    @RequestMapping(value = "/deleteTax", method = RequestMethod.POST, produces = "application/json")
    public void deleteTax(@RequestParam(value = "taxName", required = false) String taxName,@RequestParam(value = "groupName", required = false) String groupName) {
        posService.deleteTax(taxName,groupName);
    }
    @RequestMapping(value = "/editTax", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editTax(@RequestParam(value = "taxName", required = false) String taxName,@RequestParam(value = "groupName", required = false) String groupName) {
        return ResponseEntity.status(200).body(posService.editTax(taxName,groupName));
    }
    @RequestMapping(value = "/getTaxTypeList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getTaxTypeList(@RequestParam(value = "type", required = false) String type) {
        return ResponseEntity.status(200).body(posService.getTaxTypeList(type));
    }
    @RequestMapping(value = "/getPaginatedTaxTypeList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedTaxTypeList(@RequestParam(value = "searchText")String searchText,
                                                    @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedTaxTypeList(basePojo,searchText));
    }
    @RequestMapping(value = "/saveTaxType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveTax(@RequestBody TaxDTO taxDTO) {
        return ResponseEntity.status(200).body(posService.saveTaxType(taxDTO));
    }

    @RequestMapping(value = "/deleteTaxType", method = RequestMethod.POST, produces = "application/json")
    public void deleteTaxType(@RequestParam(value = "taxTypeName", required = false) String taxTypeName) {
        posService.deleteTaxType(taxTypeName);
    }
    @RequestMapping(value = "/editTaxType", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editTaxType(@RequestParam(value = "taxTypeName", required = false) String taxTypeName) {
        return ResponseEntity.status(200).body(posService.editTaxType(taxTypeName));
    }
    @RequestMapping(value = "/getHsnList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getHsnList(@RequestParam(value = "type", required = false) String type,
                                     @RequestParam(value = "searchText",required = false) String searchText) {
        return ResponseEntity.status(200).body(posService.getHsnList(type,searchText));
    }
    @RequestMapping(value = "/getPaginatedHsnList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedHsnList(@RequestParam(value = "type", required = false) String type,
                                              @RequestParam(value="searchText",required = false)String searchText,
                                              @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedHSNList(type,basePojo,searchText));
    }
    @RequestMapping(value = "/saveHsn", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveHsn(@RequestBody HsnDTO hsnDTO) {
        return ResponseEntity.status(200).body(posService.saveHsn(hsnDTO));
    }

    @RequestMapping(value = "/deleteHsn", method = RequestMethod.POST, produces = "application/json")
    public void deleteHsn(@RequestParam(value = "msiccode", required = false) String msiccode) {
        posService.deleteHsn(msiccode);
    }
    @RequestMapping(value = "/editHsn", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editHsn(@RequestParam(value = "msiccode", required = false) String msiccode) {
        return ResponseEntity.status(200).body(posService.editHsn(msiccode));
    }
    @RequestMapping(value = "/getItemList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getItemList(@RequestParam(value = "type", required = false) String type,@RequestParam(value = "searchText")String searchText) {
        return ResponseEntity.status(200).body(posService.getItemList(type,searchText));
    }
    @RequestMapping(value = "/getPaginatedItemList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedItemList(@RequestParam(value = "type", required = false) String type
                                                         ,@RequestParam(value = "searchText")String searchText,
                                               @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedItemList(type,basePojo,searchText));
    }
    @RequestMapping(value = "/saveItem", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveItem(@RequestBody addNewItemDTO addNewItemDTO) {
        return ResponseEntity.status(200).body(posService.saveItem(addNewItemDTO));
    }

    @RequestMapping(value = "/deleteItem", method = RequestMethod.POST, produces = "application/json")
    public void deleteItem(@RequestParam(value = "itemName", required = false) String itemName) {
        posService.deleteItem(itemName);
    }
    @RequestMapping(value = "/editItem", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editItem(@RequestParam(value = "itemName", required = false) String itemName) {
        return ResponseEntity.status(200).body(posService.editItem(itemName));
    }
    @RequestMapping(value = "/getItem", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getItem(@RequestParam(value = "itemName", required = false) String itemName) {
        return ResponseEntity.status(200).body(posService.getItem(itemName));
    }
    @RequestMapping(value = "/getItemDetails", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity  getItemDetails() {
        return ResponseEntity.status(200).body(posService.getAllItemsLists());
    }
    @RequestMapping(value = "/saveBank", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveBank(@RequestBody BankDTO bank) {
        return ResponseEntity.status(200).body(posService.saveBank(bank));
    }

    @RequestMapping(value = "/deleteBank", method = RequestMethod.POST, produces = "application/json")
    public void deleteBank(@RequestParam(value = "bankName", required = false) String bankName) {
        posService.deleteBank(bankName);
    }

    @RequestMapping(value = "/editBank", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editBank(@RequestParam(value = "bankName", required = false) String bankName) {
        return ResponseEntity.status(200).body(posService.editBank(bankName));
    }

    @RequestMapping(value = "/getBankList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getBankList(@RequestParam(value = "type", required = false) String type) {
        return ResponseEntity.status(200).body(posService.getBankList(type));
    }
    @RequestMapping(value = "/saveAgent", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveAgent(@RequestBody AgentDTO agentDTO) {
        return ResponseEntity.status(200).body(posService.saveAgent(agentDTO));
    }

    @RequestMapping(value = "/deleteAgent", method = RequestMethod.POST, produces = "application/json")
    public void deleteAgent(@RequestParam(value = "agentName", required = false) String agentName) {
        posService.deleteAgent(agentName);
    }

    @RequestMapping(value = "/editAgent", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editAgent(@RequestParam(value = "agentName", required = false) String agentName) {
        return ResponseEntity.status(200).body(posService.editAgent(agentName));
    }
    @RequestMapping(value = "/getAgentList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getAgentList(@RequestParam(value = "type", required = false) String type) {
        return ResponseEntity.status(200).body(posService.getAgentList(type));
    }

    @RequestMapping(value = "/getPaymentMethodList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPAymentMethodList(@RequestParam(value = "type", required = false) String type) {
        return ResponseEntity.status(200).body(posService.getPaymentMethodList(type));
    }
    @RequestMapping(value = "/getPaginatedPaymentMethodList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedPaymentMethodList(@RequestParam(value = "type", required = false) String type
                                                        ,@RequestParam(value = "searchText")String searchText,
                                                        @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedPayMethodList(type,basePojo,searchText));
    }
    @RequestMapping(value = "/savePaymentMethod", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity savePaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDTO) {
        return ResponseEntity.status(200).body(posService.savePaymentMethod(paymentMethodDTO));
    }

    @RequestMapping(value = "/deletePaymentMethod", method = RequestMethod.POST, produces = "application/json")
    public void deletePaymentMethod(@RequestParam(value = "id", required = false) Long id) {
        posService.deletePaymentMethod(id);
    }

    @RequestMapping(value = "/editPaymentMethod", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editPaymentMethod(@RequestParam(value = "id", required = false) Long id) {
        return ResponseEntity.status(200).body(posService.editPaymentMethod(id));
    }
    @RequestMapping(value = "/saveformsetup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity saveformsetup(@RequestBody FormsetupDTO formsetupDTO) {
        return ResponseEntity.status(200).body(posService.saveFormSetup(formsetupDTO));
    }
    @RequestMapping(value = "/getFormsetupList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getFormsetupList() {
        return ResponseEntity.status(200).body(posService.getFormSetupList());
    }
    @RequestMapping(value = "/getPaginatedFormsetupList", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getPaginatedFormSetupList(@RequestParam(value = "searchText")String searchText,
                                               @RequestBody BasePojo basePojo) {
        return ResponseEntity.status(200).body(posService.getPaginatedFormSetUpList(basePojo,searchText));
    }
    @RequestMapping(value = "/editFormSetupMethod", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editFormSetupMethod(@RequestParam(value = "typeName", required = false) String typeName) {
        return ResponseEntity.status(200).body(posService.editFormsetupMethod(typeName));
    }
    @RequestMapping(value = "/getFormSetup", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getFormSetup(@RequestParam(value = "type", required = false) String  type) {
        return ResponseEntity.status(200).body(posService.getFormSetup(type));
    }

    @RequestMapping(value = "/schedulerList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity schedulerList() {
        return ResponseEntity.status(HttpStatus.OK).body(posService.getSchedulerList());
    }
    @RequestMapping(value = "/saveScheduler", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveScheduler(@RequestBody MailSchedulerData mailSchedulerData)throws Exception {
        posService.saveMailSchedule(mailSchedulerData);
        return ResponseEntity.status(HttpStatus.OK).body(mailSchedulerData  );
    }
    @RequestMapping(value = "/deleteMailScheduler", method = RequestMethod.POST)
    public ResponseEntity deleteMailScheduler(@RequestParam(value = "searchSchedulerText") String schedulerSearch) {
        posService.deleteMailSchedulerDetails(schedulerSearch);
        return null;

    }
    @RequestMapping(path = "/itemExport", method = RequestMethod.GET)
    public ResponseEntity itemExport(@RequestParam(value = "type") String type,
                                      @RequestParam(value = "val") String searchText,
                                      @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadItemPdf(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Item.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadItemExcelSheet(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Item.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
    @RequestMapping(path = "/itemCategoryExport", method = RequestMethod.GET)
    public ResponseEntity itemCategoryExport(@RequestParam(value = "type") String type,
                                          @RequestParam(value = "val") String searchText,
                                          @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadItemCatPdf(outputStream,statustype, searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "ItemCategoryExport.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadItemCategoryExcelSheet(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "ItemCategory.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }


    @RequestMapping(value = "/categoryImportSave" ,method = RequestMethod.POST)
    public ResponseEntity categoryImportSave(@RequestParam("myFile") MultipartFile uploadfiles) throws Exception {
        System.out.println(uploadfiles.getOriginalFilename());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(uploadfiles.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if(row==null)
                    break;
                if(row!=null&&row.getPhysicalNumberOfCells()>0) {
//

                    org.apache.poi.ss.usermodel.Cell CategoryName = row.getCell(0);
                    org.apache.poi.ss.usermodel.Cell CategoryDescription = row.getCell(1);

                    ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
//                    itemCategoryDTO.setItemCategoryCode(CategoryCode  == null ? null : CategoryCode.toString());
                    itemCategoryDTO.setItemCategoryName(CategoryName  == null ? null : CategoryName.toString());
                    itemCategoryDTO.setItemCategoryDesc(CategoryDescription  == null ? null : CategoryDescription .toString());
                    posService.SaveCategory(itemCategoryDTO);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/brandImportSave" ,method = RequestMethod.POST)
    public ResponseEntity brandImportSave(@RequestParam("myFile") MultipartFile uploadfiles) throws Exception {
        System.out.println(uploadfiles.getOriginalFilename());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(uploadfiles.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if(row==null)
                    break;
                if(row!=null&&row.getPhysicalNumberOfCells()>0) {
//

                    org.apache.poi.ss.usermodel.Cell BrandName = row.getCell(1);
                    org.apache.poi.ss.usermodel.Cell BrandDescription = row.getCell(2);

                    ItemBrandDTO itemBrandDTO = new ItemBrandDTO();
                    itemBrandDTO.setBrandName(BrandName  == null ? null : BrandName.toString());

                    itemBrandDTO.setBrandDescription(BrandDescription  == null ? null : BrandDescription .toString());
                    posService.SaveBrand(itemBrandDTO);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }




    @RequestMapping(path = "/taxExport", method = RequestMethod.GET)
    public ResponseEntity taxExport(@RequestParam(value = "type") String type,
                                             @RequestParam(value = "val") String searchText,
                                             @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadTaxPdf(outputStream,statustype, searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Tax.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadTaxExcelSheet(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Tax.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
    @RequestMapping(path = "/taxTypeExport", method = RequestMethod.GET)
    public ResponseEntity taxTypeExport(@RequestParam(value = "type") String type,
                                    @RequestParam(value = "val") String searchText,
                                    @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadTaxTypePdf(outputStream,statustype, searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "TaxType.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadTaxTypeExcelSheet(outputStream,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "TaxType.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
    @RequestMapping(path = "/emailExport", method = RequestMethod.GET)
    public ResponseEntity emailExport(@RequestParam(value = "type") String type) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadEmailPdf(outputStream);
            headers.add("Content-Disposition", "attachment; filename=\"" + "email.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadEmailExcelSheet(outputStream);
            headers.add("Content-Disposition", "attachment; filename=\"" + "email.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
    @RequestMapping(path = "/itemBrandExport", method = RequestMethod.GET)
    public ResponseEntity itemBrandExport(@RequestParam(value = "type") String type,
                                     @RequestParam(value = "val") String searchText,
                                     @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadBrandPdf(outputStream,statustype, searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "ItemBrand.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadBrandExcelSheet(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "ItemBrand.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
    @RequestMapping(path = "/uomExport", method = RequestMethod.GET)
    public ResponseEntity uomExport(@RequestParam(value = "type") String type,
                                             @RequestParam(value = "val") String searchText,
                                             @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadUOMPdf(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "UOM.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadUOMExcelSheet(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "UOM.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
    @RequestMapping(path = "/hsnExport", method = RequestMethod.GET)
    public ResponseEntity hsnExport(@RequestParam(value = "type") String type,
                                             @RequestParam(value = "val") String searchText,
                                             @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadHSNPdf(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "HSN/SAC.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadHSNExcelSheet(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "HSN/SAC.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
    @RequestMapping(path = "/stateExport", method = RequestMethod.GET)
    public ResponseEntity stateExport(@RequestParam(value = "type") String type,
                                             @RequestParam(value = "val") String searchText,
                                             @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadStatePdf(outputStream,statustype, searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "State.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadStateExcelSheet(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "State.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
    @RequestMapping(path = "/countryExport", method = RequestMethod.GET)
    public ResponseEntity countryExport(@RequestParam(value = "type") String type,
                                             @RequestParam(value = "val") String searchText,
                                             @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadCountryPdf(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Country.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadCountryExcelSheet(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Country.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }

    @RequestMapping(path = "/currencyExport", method = RequestMethod.GET)
    public ResponseEntity currencyExport(@RequestParam(value = "type") String type,
                                             @RequestParam(value = "val") String searchText,
                                             @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadCurrencyPdf(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Currency.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadCurrencyExcelSheet(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Currency.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
    @RequestMapping(path = "/supplierExport", method = RequestMethod.GET)
    public ResponseEntity supplierExport(@RequestParam(value = "type") String type,
                                             @RequestParam(value = "val") String searchText,
                                             @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadSupplierPdf(outputStream,statustype, searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Supplier.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadSupplierExcelSheet(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Supplier.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }
    @RequestMapping(path = "/customerExport", method = RequestMethod.GET)
    public ResponseEntity customerExport(@RequestParam(value = "type") String type,
                                         @RequestParam(value = "val") String searchText,
                                         @RequestParam(value = "statustype") String statustype) {
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.equalsIgnoreCase(type, "Pdf")) {
            posService.downloadCustomerPdf(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Customer.pdf" + "\"");
        } else if (StringUtils.equalsIgnoreCase(type, "Excel")) {
            posService.downloadCustomerExcelSheet(outputStream, statustype,searchText);
            headers.add("Content-Disposition", "attachment; filename=\"" + "Customer.xlsx" + "\"");
        }
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayResource);
    }

}