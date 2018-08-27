package com.hyva.posretail.pos.posService;
import com.hyva.posretail.pos.posEntities.*;
import com.hyva.posretail.pos.posRespositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BasicDataService {

    @Autowired
    PosUserRepository posUserRepository;
    @Autowired
    PosMailRepository posMailRepository;
    @Autowired
    PosSchedulerRepository posSchedulerRepository;
    @Autowired
    PosBankRepository posBankRepository;
    @Autowired
    SchedulerService schedulerService;
    @Autowired
    PosCategoryRepository posCategoryRepository;
    @Autowired
    PosStateRepository posStateRepository;
    @Autowired
    PosCountryRepository posCountryRepository;
    @Autowired
    PosCurrencyRepository currencyRepository;
    @Autowired
    PosSupplierRepository posSupplierRepository;
    @Autowired
    PosUomRepository posUomRepository;
    @Autowired
    PosBrandRepository brandRepository;
    @Autowired
    PosTaxRepository posTaxRepository;
    @Autowired
    PosTaxTypeRepository posTaxTypeRepository;
    @Autowired
    PosPurchaseInvoiceRepository posPurchaseInvoiceRepository;
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    PosCustomerRepository posCustomerRepository;
    @Autowired
    PosHsnRepository posHsnRepository;
    @Autowired
    PosItemRepository posItemRepository;
    @Autowired
    PosItemTypeRepository posItemTypeRepository;
    @Autowired
    PosInventoryMovementRepository posInventoryMovementRepository;
    @Autowired
    PosAgentRepository posAgentRepository;
    @Autowired
    PosPaymentMethodRepository posPaymentMethodRepository;
    @Autowired
    PosFormSetupRepository posFormSetupRepository;

    public void insertBasicData() throws Exception {
        //============================================= User ======================================================================
        List<User> userList = (List<User>) posUserRepository.findAll();
        if (userList.isEmpty()) {
            User userObj = new User();
            userObj.setEmail("");
            userObj.setFull_name("admin");
            userObj.setPasswordUser("admin");
            userObj.setPhone("");
            userObj.setSecurityAnswer("");
            userObj.setSecurityQuestion("");
            userObj.setStatus("Active");
            userObj.setUserName("admin");
            userObj.setUserToken("");
            posUserRepository.save(userObj);
        }
    }

    public void pushBasicData() {
        //country basic data
        List<Country> countryList = posCountryRepository.findAll();
        if (countryList.isEmpty()) {
            getCountryObject("India", true, "Active");
            getCountryObject("Malaysia", true, "Active");
            getCountryObject("Brunei", true, "Active");
            getCountryObject("Indonesia", true, "Active");
            getCountryObject("Singapore", true, "Active");
            getCountryObject("Thailand", true, "Active");
            getCountryObject("USA", true, "Active");
            getCountryObject("Europe", true, "Active");
        }
        List<Currency> currencyList = currencyRepository.findAll();
        if (currencyList.isEmpty()) {
            getCurrencyObject("INR", "Rupee", "Indian Rupee", "Rs", "Before Number", "02");
            getCurrencyObject("MYR", "Ringgit", "Malaysian ringgit", "RM", "Before Number", "01");
            getCurrencyObject("BND", "Brunei Dollar", "Brunei Dollar", "B$", "Before Number", "03");
            getCurrencyObject("IDR", "Ruphiah", "Indonesia Ruphiah", "Rp", "Before Number", "04");
            getCurrencyObject("SGD", "Singapore Dollar", "Singapore Dollar", "S$", "Before Number", "05");
            getCurrencyObject("THB", "Thai Baht", "Thai Baht", "B", "Before Number", "06");
            getCurrencyObject("USD", "US Dollar", "US Dollar", "$", "Before Number", "07");
            getCurrencyObject("EUR", "Euro", "Euro", "€", "Before Number", "08");
            getCurrencyObject("GBP", "Pound", "Pound Sterling", "£", "Before Number", "09");
        }
        List<Tax> taxList=posTaxRepository.findAll();
        if(taxList.isEmpty()){
            saveTaxType("STANDARD RATED","STANDARD RATED",true);
            saveTaxType("SPECIAL SCHEME SUPPLIES","SPECIAL SCHEME SUPPLIES",true);
            saveTaxType("ZERO RATED SUPPLIES", "ZERO RATED SUPPLIES",true);
            saveTaxType("EXPORTED GOOD SUPPLIES", "EXPORTED GOOD SUPPLIES",true);
            saveTaxType("EXEMPT SUPPLIES", "EXEMPT SUPPLIES",true);
            saveTaxType("OUT OF SCOPE", "OUT OF SCOPE",true);
            saveTaxType("RELIEF SUPPLIES", "RELIEF SUPPLIES",true);
            saveTaxType("DISREGARDED SUPPLIES", "DISREGARDED SUPPLIES",true);
            saveTaxType("OUTPUT TAX ADJUSTMENT", "OUTPUT TAX ADJUSTMENT",true);
            saveTaxType("SALES TAX", "SALES TAX",true);
            saveTaxType("SERVICE TAX", "SERVICE TAX",true);
            saveTaxType("NIL RATED SUPPLIES", "NIL RATED SUPPLIES",true);
            saveTaxType("SEZ SUPPLIES", "SEZ SUPPLIES",true);
            saveTaxType("DEEMED SUPPLIES", "DEEMED SUPPLIES",true);
            saveTaxType("STANDARD RATED ACQUISITION", "STANDARD RATED ACQUISITION",true);
            saveTaxType("SPECIAL SCHEME ACQUISITION", "SPECIAL SCHEME ACQUISITION",true);
            saveTaxType("IMPORT OF GOODS", "IMPORT OF GOODS",true);
            saveTaxType("IMPORT OF SERVICES", "IMPORT OF SERVICES",true);
            saveTaxType("BLOCK INPUT TAX", "BLOCK INPUT TAX",true);
            saveTaxType("NON GST REGISTERED", "NON GST REGISTERED",true);
            saveTaxType("ZERO RATED ACQUISITION", "ZERO RATED ACQUISITION",true);
            saveTaxType("EXEMPT ACQUISITION", "EXEMPT ACQUISITION",true);
            saveTaxType("INPUT TAX ADJUSTMENT", "INPUT TAX ADJUSTMENT",true);
            saveTaxType("NOT APPLICABLE", "NOT APPLICABLE",true);
            saveTaxType("IMPORT DUTY", "IMPORT DUTY",true);
            saveTaxType("EXCISE DUTY", "EXCISE DUTY",true);
            saveTaxType("CAPITAL GOODS ACQUIRED", "CAPITAL GOODS ACQUIRED",true);
            saveTaxType("NIL RATED ACQUISITION", "NIL RATED ACQUISITION",true);
            saveTaxType("OUT OF SCOPE PURCHASE", "OUT OF SCOPE PURCHASE",true);
            saveTaxType("COMMON CLAIMABLE CREDITS", "COMMON CLAIMABLE CREDITS",true);
            saveTaxType("NON BUSSINESS BLOCKED", "NON BUSSINESS BLOCKED",true);
            saveTaxType("NON BUSSINESS CAPLITAL GOODS", "NON BUSSINESS CAPLITAL GOODS",true);
            saveTaxType("EXEMPT CAPITAL GOODS", "EXEMPT CAPITAL GOODS",true);
            saveTaxType("COMMON CREDIT CAPITAL GOODS", "COMMON CREDIT CAPITAL GOODS",true);

            insertTax("RC-0", "RC-0%", "SALES/OUTPUT", "REVERSE CHARGE SUPPLIES WITH GST 0% CHARGED", "Active", 0, true, "STANDARD RATED");
            insertTax("N/A", "N/A", "SALES/OUTPUT", "NOT APPLICABLE", "Active", 0, true, "STANDARD RATED");
            insertTax("GST-0", "GST-0%", "SALES/OUTPUT", "STANDARD RATED SUPPLIES WITH GST 0% CHARGED", "Active", 0, true,"STANDARD RATED");
            insertTax("GST-0.25", "GST-0.25%", "SALES/OUTPUT", "STANDARD RATED SUPPLIES WITH GST 0.25% CHARGED", "Active", 0.25, true, "STANDARD RATED");
            insertTax("GST-3", "GST-3%", "SALES/OUTPUT", "STANDARD RATED SUPPLIES WITH GST 3% CHARGED", "Active", 3, true,  "STANDARD RATED");
            insertTax("GST-5", "GST-5%", "SALES/OUTPUT", "STANDARD RATED SUPPLIES WITH GST 5% CHARGED", "Active", 5, true, "STANDARD RATED");
            insertTax("GST-12", "GST-12%", "SALES/OUTPUT", "STANDARD RATED SUPPLIES WITH GST 12% CHARGED", "Active", 12, true, "STANDARD RATED");
            insertTax("ES", "ES", "SALES/OUTPUT", "EXEMPT SUPPLIES UNDER GST", "InActive", 0, true, "STANDARD RATED");
            insertTax("GST-18", "GST-18%", "SALES/OUTPUT", "STANDARD RATED SUPPLIES WITH GST 18% CHARGED", "Active", 18, true, "STANDARD RATED");
            insertTax("GST-22", "GST-22%", "SALES/OUTPUT", "STANDARD RATED SUPPLIES WITH GST 22% CHARGED", "Active",22, true, "STANDARD RATED");
            insertTax("GST-28", "GST-28%", "SALES/OUTPUT", "STANDARD RATED SUPPLIES WITH GST 28% CHARGED", "Active", 28, true, "STANDARD RATED");
            //purchase/input Tax
            insertTax("N/A", "N/A", "PURCHASE/INPUT", "NOT APPLICABLE", "Active", 0, true,"STANDARD RATED ACQUISITION");
            insertTax("GST-0", "GST-0%", "PURCHASE/INPUT", "PURCHASE WITH GST INCURRED AT 0% AND DIRECTLY ATTRIBUTABLE TO TAXABLE SUPPLIES", "Active", 0, true,"STANDARD RATED ACQUISITION");
            insertTax("GST-0.25", "GST-0.25%", "PURCHASE/INPUT", "PURCHASE WITH GST INCURRED AT 0.25% AND DIRECTLY ATTRIBUTABLE TO TAXABLE SUPPLIES", "Active", 0.25, true, "STANDARD RATED ACQUISITION");
            insertTax("GST-3", "GST-3%", "PURCHASE/INPUT", "PURCHASE WITH GST INCURRED AT 3% AND DIRECTLY ATTRIBUTABLE TO TAXABLE SUPPLIES", "Active", 3, true, "STANDARD RATED ACQUISITION");
            insertTax("GST-5", "GST-5%", "PURCHASE/INPUT", "PURCHASE WITH GST INCURRED AT 5% AND DIRECTLY ATTRIBUTABLE TO TAXABLE SUPPLIES", "Active", 5, true,"STANDARD RATED ACQUISITION");
            insertTax("GST-12", "GST-12%", "PURCHASE/INPUT", "PURCHASE WITH GST INCURRED AT 12% AND DIRECTLY ATTRIBUTABLE TO TAXABLE SUPPLIES", "Active", 12, true, "STANDARD RATED ACQUISITION");
            insertTax("GST-18", "GST-18%", "PURCHASE/INPUT", "PURCHASE WITH GST INCURRED AT 18% AND DIRECTLY ATTRIBUTABLE TO TAXABLE SUPPLIES", "Active", 18, true, "STANDARD RATED ACQUISITION");
            insertTax("GST-22", "GST-22%", "PURCHASE/INPUT", "PURCHASE WITH GST INCURRED AT 22% AND DIRECTLY ATTRIBUTABLE TO TAXABLE SUPPLIES", "Active", 22, true,"STANDARD RATED ACQUISITION");
            insertTax("GST-28", "GST-28%", "PURCHASE/INPUT", "PURCHASE WITH GST INCURRED AT 28% AND DIRECTLY ATTRIBUTABLE TO TAXABLE SUPPLIES", "Active", 28, true, "STANDARD RATED ACQUISITION");
        }
        List<State> stateList = posStateRepository.findAll();
        if (stateList.isEmpty()) {
            // Indian States
            getStateObject("AN", "35", "Active", "Andaman and Nicobar Islands ", "India");
            getStateObject("AP", "28", "Active", "Andhra Pradesh", "India");
            getStateObject("AD", "37", "Active", "Andhra Pradesh (New) ", "India");
            getStateObject("AR", "12", "Active", "Arunachal Pradesh ", "India");
            getStateObject("AS", "18", "Active", "Assam ", "India");
            getStateObject("BH", "10", "Active", "Bihar", "India");
            getStateObject("CH", "04", "Active", "Chandigarh", "India");
            getStateObject("CT", "22", "Active", "Chattisgarh", "India");
            getStateObject("DN", "26", "Active", "Dadra and Nagar Haveli ", "India");
            getStateObject("DD", "25", "Active", "Daman and Diu ", "India");
            getStateObject("DL", "07", "Active", "Delhi", "India");
            getStateObject("GA", "30", "Active", "Goa", "India");
            getStateObject("GJ", "24", "Active", "Gujarat", "India");
            getStateObject("HR", "06", "Active", "Haryana", "India");
            getStateObject("HP", "02", "Active", "Himachal Pradesh", "India");
            getStateObject("JK", "01", "Active", "Jammu and Kashmir", "India");
            getStateObject("JH", "20", "Active", "Jharkhand", "India");
            getStateObject("KA", "29", "Active", "Karnataka", "India");
            getStateObject("KL", "32", "Active", "Kerala", "India");
            getStateObject("LD", "31", "Active", "Lakshadweep Islands", "India");
            getStateObject("MP", "23", "Active", "Madhya Pradesh ", "India");
            getStateObject("MP", "27", "Active", "Maharastra ", "India");
            getStateObject("MN", "14", "Active", "Manipur", "India");
            getStateObject("ME", "17", "Active", "Meghalaya", "India");
            getStateObject("MI", "15", "Active", "Mizoram", "India");
            getStateObject("NL", "13", "Active", "Nagaland", "India");
            getStateObject("OR", "21", "Active", "Odisha", "India");
            getStateObject("PY", "34", "Active", "Pondicherry", "India");
            getStateObject("PB", "03", "Active", "Punjab", "India");
            getStateObject("RJ", "08", "Active", "Rajasthan", "India");
            getStateObject("SK", "11", "Active", "Sikkim", "India");
            getStateObject("TN", "33", "Active", "Tamil Nadu ", "India");
            getStateObject("TS", "36", "Active", "Telangana", "India");
            getStateObject("TR", "16", "Active", "Tripura", "India");
            getStateObject("UP", "09", "Active", "Uttar Pradesh", "India");
            getStateObject("UT", "05", "Active", "Uttarakhand", "India");
            getStateObject("WB", "19", "Active", "West Bengal", "India");
        }
        List<UnitOfMeasurement> list = posUomRepository.findAll();
        if (list.isEmpty()) {
            addUnitOfMeasurement("KG", "Kilogram", "Active");
            addUnitOfMeasurement("G", "Gram", "Active");
            addUnitOfMeasurement("Ton", "Ton", "Active");
            addUnitOfMeasurement("Unit", "Unit", "Active");
            addUnitOfMeasurement("lb", "Pound", "Active");
            addUnitOfMeasurement("l", "Liter", "Active");
            addUnitOfMeasurement("ml", "Milliliter", "Active");
            addUnitOfMeasurement("m", "Meter", "Active");
            addUnitOfMeasurement("sqm", "square meter", "Active");
        }
        List<FormSetUp> formSetUpList = posFormSetupRepository.findAll();
        if (formSetUpList.isEmpty()) {
            insertFormSetUp("PurchaseDebitNote", "PDN", "00000001", true, "AR");
            insertFormSetUp("DirectSalesInvoice", "DSINV", "00000001", true, "AR");
            insertFormSetUp("DirectPurchaseInvoice", "DPINV", "00000001", true, "AP");
            insertFormSetUp("SalesCreditNote", "SCN", "00000001", true, "AR");
        }
        List<ItemBrandName> brandNameList = brandRepository.findAll();
        if (brandNameList.isEmpty()) {
            insertBrand("No Brand", "No Brand");
        }
        List<ItemCategory> categories = posCategoryRepository.findAll();
        if (categories.isEmpty()) {
            insertCategory("Manufacturing", "001", "Manufacturing", "Active", "true");
        }
        List<PaymentMethod> paymentMethods = posPaymentMethodRepository.findAll();
        if (paymentMethods.isEmpty()) {
            paymentMethodadd("Cash", "Cash", "Cash");
            paymentMethodadd("Bank", "Bank", "Bank");
            paymentMethodadd("Card", "Card", "Card");
            paymentMethodadd("Discount Voucher", "Voucher", "Voucher");
        }
        List<Customer> customers = posCustomerRepository.findAll();
        if (customers.isEmpty()) {
            insertCustomer("Cash Customer", "India", "Karnataka", "001", "Indian Rupee");
        }
        List<Supplier> suppliers = posSupplierRepository.findAll();
        if (suppliers.isEmpty()) {
            insertSupplier("Cash Supplier", "India", "Karnataka", "001", "Indian Rupee");
        }
        List<Msiccode> msiccodes = posHsnRepository.findAll();
        if (msiccodes.isEmpty()) {
            saveHsnCode("No Hsn Code", "");
        }
    }
    private void insertTax(String code, String desc, String group, String name, String status, double taxPer, boolean flag,String taxTypeName) {
        Tax obj = new Tax();
        obj.setFlag(flag);
        obj.setTaxDescription(desc);
        obj.setTax_Per(taxPer);
        obj.setTaxCode(code);
        obj.setTaxStatus(status);
        obj.setTaxName(name);
        obj.setTaxGroup(group);
        obj.setTaxTypeName(taxTypeName);
        posTaxRepository.save(obj);
    }
    public void saveTaxType(String taxTypeName,String taxTypeDesc,boolean flag){
        TaxType taxType=new TaxType();
        taxType.setTaxTypeDesc(taxTypeDesc);
        taxType.setTaxTypeName(taxTypeName);
        taxType.setFlag(flag);
        posTaxTypeRepository.save(taxType);
    }
    public void insertCustomer(String customerName, String countryName, String stateName, String customerNumber, String currencyname) {
        Customer c = new Customer();
        c.setCustomerName(customerName);
        c.setCurrencyName(currencyname);
        c.setStateName(stateName);
        c.setCountryName(countryName);
        c.setCustomerNumber(customerNumber);
        c.setStatus("Active");
        posCustomerRepository.save(c);
    }

    public void insertSupplier(String supplierName, String countryName, String stateName, String supplierNumber, String currencyname) {
        Supplier supplier = new Supplier();
        supplier.setSupplierName(supplierName);
        supplier.setCurrencyName(currencyname);
        supplier.setStateName(stateName);
        supplier.setCountryName(countryName);
        supplier.setSupplierNumber(supplierNumber);
        supplier.setStatus("Active");
        posSupplierRepository.save(supplier);
    }

    public void insertFormSetUp(String typename, String typeprefix, String nextref, boolean include_date, String transactionType) {
        FormSetUp c = new FormSetUp();
        c.setTypename(typename);
        c.setTypeprefix(typeprefix);
        c.setNextref(nextref);
        c.setTransactionType(transactionType);
        c.setInclude_date(include_date);
        posFormSetupRepository.save(c);
    }

    private void paymentMethodadd(String name, String description, String type) {
        PaymentMethod p = new PaymentMethod();
        p.setPaymentmethodName(name);
        p.setPaymentmethodDescription(description);
        p.setPaymentmethodType(type);
        p.setStatus("Active");
        p.setDefaultType("false");
        posPaymentMethodRepository.save(p);
    }

    private void saveHsnCode(String hsnCode, String description) {
        Msiccode msiccode = new Msiccode();
        msiccode.setCode(hsnCode);
        msiccode.setDescription(description);
        msiccode.setStatus("Active");
        posHsnRepository.save(msiccode);
    }

    public void insertBrand(String itembrandName, String desc) {
        ItemBrandName brandName = new ItemBrandName();
        brandName.setBrandDescription(desc);
        brandName.setBrandName(itembrandName);
        brandName.setStatus("Active");
        brandRepository.save(brandName);
    }

    public void insertCategory(String categoryName, String categoryCode, String categoryDesc, String status, String defaultType) {
        ItemCategory itemCategory = new ItemCategory();
//        itemCategory.setItemCategoryCode(categoryCode);
        itemCategory.setItemCategoryName(categoryName);
        itemCategory.setItemCategoryDesc(categoryDesc);
        itemCategory.setDefaultType(defaultType);
        itemCategory.setStatus("Active");
        posCategoryRepository.save(itemCategory);
    }

    public void getCurrencyObject(String currencyCode, String currencyDescription, String currencyName, String currencySymbol, String currencySymbolPlace, String accountId) {
        Currency curObj = new Currency();
        curObj.setCurrencyCode(currencyCode);
        curObj.setCurrencyDescription(currencyDescription);
        curObj.setCurrencyName(currencyName);
        curObj.setCurrencySymbol(currencySymbol);
        curObj.setCurrencySymbolPlace(currencySymbolPlace);
        curObj.setAccountCode(accountId);
        curObj.setStatus("Active");
        currencyRepository.save(curObj);
    }

    public void getStateObject(String stateCode, String vehicleSeries, String status, String stateName, String countryName) {
        State state = new State();
        state.setVehicleSeries(vehicleSeries);
        state.setStateCode(stateCode);
        state.setCountryName(countryName);
        state.setStateName(stateName);
        state.setStatus("Active");
        posStateRepository.save(state);
    }

    public void getCountryObject(String countryName, Boolean flag, String status) {
        Country country = new Country();
        country.setFlag(flag);
        country.setCountryName(countryName);
        country.setStatus("Active");
        posCountryRepository.save(country);
    }

    private void addUnitOfMeasurement(String kg, String kilogram, String active) {
        UnitOfMeasurement um = new UnitOfMeasurement();
        um.setUnitOfMeasurementName(kg);
        um.setUnitOfMeasurementDescription(kilogram);
        um.setUnitOfMeasurementStatus(active);
        posUomRepository.save(um);
    }


}