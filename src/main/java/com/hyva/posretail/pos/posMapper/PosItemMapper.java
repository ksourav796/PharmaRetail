package com.hyva.posretail.pos.posMapper;

import com.hyva.posretail.pos.posEntities.*;
import com.hyva.posretail.pos.posPojo.*;

import java.util.ArrayList;
import java.util.List;

public class PosItemMapper {
    public static List<ItemCategoryDTO> mapEntityToPojo(List<ItemCategory> itemCategoryList){
        List<ItemCategoryDTO> list=new ArrayList<>();
        for(ItemCategory itemCategory:itemCategoryList) {
            ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
            itemCategoryDTO.setDefaultType(itemCategory.getDefaultType());
            itemCategoryDTO.setItemCategoryId(itemCategory.getItemCategoryId());
            itemCategoryDTO.setStatus(itemCategory.getStatus());
            itemCategoryDTO.setItemCategoryName(itemCategory.getItemCategoryName());
            itemCategoryDTO.setItemCategoryDesc(itemCategory.getItemCategoryDesc());
            list.add(itemCategoryDTO);
        }
        return list;
    }
    public static ItemCategory mapItemCategoryPojoToEntity(ItemCategoryDTO itemCategoryDTO){
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setDefaultType(itemCategoryDTO.getDefaultType());

        itemCategory.setItemCategoryId(itemCategoryDTO.getItemCategoryId());
        itemCategory.setStatus(itemCategoryDTO.getStatus());
        itemCategory.setItemCategoryName(itemCategoryDTO.getItemCategoryName());
        itemCategory.setItemCategoryDesc(itemCategoryDTO.getItemCategoryDesc());
        return itemCategory;
    }

    public static List<ItemCategoryDTO> mapItemCategoryEntityToPojo(List<ItemCategory> itemCategoryList){
        List<ItemCategoryDTO> list=new ArrayList<>();
        for(ItemCategory itemCategory:itemCategoryList) {
            ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();

            itemCategoryDTO.setItemCategoryName(itemCategory.getItemCategoryName());
            itemCategoryDTO.setItemCategoryDesc(itemCategory.getItemCategoryDesc());

            list.add(itemCategoryDTO);
        }
        return list;
    }

    public static ItemBrandName mapItemBrandPojoToEntity(ItemBrandDTO itemBrandDTO){
        ItemBrandName itemBrandName = new ItemBrandName();
        itemBrandName.setDefaultType(itemBrandDTO.getDefaultType());
        itemBrandName.setBrandName(itemBrandDTO.getBrandName());
        itemBrandName.setBrandId(itemBrandDTO.getBrandId());
        itemBrandName.setStatus(itemBrandDTO.getStatus());

        itemBrandName.setBrandDescription(itemBrandDTO.getBrandDescription());
        return itemBrandName;
    }

    public static List<ItemBrandDTO> mapItemBrandEntityToPojo(List<ItemBrandName> itemBrandNameList){
        List<ItemBrandDTO> list=new ArrayList<>();
        for(ItemBrandName itemBrandName:itemBrandNameList) {
            ItemBrandDTO itemBrandDTO = new ItemBrandDTO();
            itemBrandDTO.setBrandId(itemBrandName.getBrandId());
            itemBrandDTO.setBrandName(itemBrandName.getBrandName());
            itemBrandDTO.setBrandDescription(itemBrandName.getBrandDescription());

            list.add(itemBrandDTO);
        }
        return list;
    }


    public static List<StateDTO> mapStateEntityToPojo(List<State> stateList){
        List<StateDTO> list=new ArrayList<>();
        for(State state:stateList) {
            StateDTO stateDTO = new StateDTO();
            stateDTO.setId(state.getId());
            stateDTO.setStateCode(state.getStateCode());
            stateDTO.setStateName(state.getStateName());
            stateDTO.setVehicleSeries(state.getVehicleSeries());
            stateDTO.setStatus(state.getStatus());
            stateDTO.setCountry(state.getCountryName());
            list.add(stateDTO);
        }
        return list;
    }
    public static State mapStatePojoToEntity(StateDTO stateDTO){
        State state = new State();
        state.setStateCode(stateDTO.getStateCode());
        state.setId(stateDTO.getId());
        state.setStateName(stateDTO.getStateName());
        state.setVehicleSeries(stateDTO.getVehicleSeries());
        state.setStatus(stateDTO.getStatus());
        return state;
    }
    public static List<MailDTO> mapMailEntityToPojo(List<Mail> mailList){
        List<MailDTO> list=new ArrayList<>();
        for(Mail mail:mailList) {
            MailDTO mailDTO = new MailDTO();
            mailDTO.setId(mail.getId());
            mailDTO.setUserName(mail.getUserName());
            mailDTO.setPassword(mail.getPassword());
            mailDTO.setSmtpHost(mail.getSmtpHost());
            mailDTO.setPort(mail.getPort());
            list.add(mailDTO);
        }
        return list;
    }
    public static List<SalesInvoiceDTO> mapSalesInvEntityToPojo(List<DirectSalesInvoiceTemplate> list){
        List<SalesInvoiceDTO> saleslist=new ArrayList<>();
        for(DirectSalesInvoiceTemplate sales:list){
            SalesInvoiceDTO salesInvoiceDTO= new SalesInvoiceDTO();
            salesInvoiceDTO.setDate(sales.getSiDate());
            salesInvoiceDTO.setSINo(sales.getSiNo());
            salesInvoiceDTO.setCustomerName(sales.getCustomerName());
            salesInvoiceDTO.setTotalAmount(sales.getTotalAmount());
            salesInvoiceDTO.setTotalReceivable(sales.getTotalReceivable());
            saleslist.add(salesInvoiceDTO);
        }
        return saleslist;
    }

    public static List<PurchaseDTO> mapPurInvEntityToPojo(List<PurchaseInvoice> list){
        List<PurchaseDTO> purlist=new ArrayList<>();
        for(PurchaseInvoice pur:list){
            PurchaseDTO purchaseDTO= new PurchaseDTO();
            purchaseDTO.setDate(pur.getPidate());
            purchaseDTO.setPiNo(pur.getPino());
            purchaseDTO.setSupplierName(pur.getSupplierName());
            purchaseDTO.setTotalAmt(String.valueOf(pur.getTotalAmount()));
            purchaseDTO.setTotalPayable(String.valueOf(pur.getTotalPayable()));
            purlist.add(purchaseDTO);
        }
        return purlist;
    }

    public static List<CountryDTO> mapCountryEntityToPojo(List<Country> countryList){
        List<CountryDTO> list=new ArrayList<>();
        for(Country country:countryList) {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setCountryId(country.getCountryId());
            countryDTO.setCountryName(country.getCountryName());
            countryDTO.setStatus(country.getStatus());
            list.add(countryDTO);
        }
        return list;
    }
    public static Country mapCountryPojoToEntity(CountryDTO countryDTO){
        Country country = new Country();
        country.setCountryId(countryDTO.getCountryId());
        country.setCountryName(countryDTO.getCountryName());
        country.setStatus(countryDTO.getStatus());
        return country;
    }
    public static List<CurrencyDTO> mapCurrencyEntityToPojo(List<Currency> currencyList){
        List<CurrencyDTO> list=new ArrayList<>();
        for(Currency currency:currencyList) {
            CurrencyDTO currencyDTO = new CurrencyDTO();
            currencyDTO.setCurrencyId(currency.getCurrencyId());
            currencyDTO.setCurrencyCode(currency.getCurrencyCode());
            currencyDTO.setCurrencyDescription(currency.getCurrencyDescription());
            currencyDTO.setCurrencyName(currency.getCurrencyName());
            currencyDTO.setCurrencySymbol(currency.getCurrencySymbol());
            currencyDTO.setStatus(currency.getStatus());
            list.add(currencyDTO);
        }
        return list;
    }
    public static Currency mapCurrencyPojoToEntity(CurrencyDTO currencyDTO){
        Currency currency = new Currency();
        currency.setCurrencyId(currencyDTO.getCurrencyId());
        currency.setCurrencyCode(currencyDTO.getCurrencyCode());
        currency.setCurrencyDescription(currencyDTO.getCurrencyDescription());
        currency.setCurrencyName(currencyDTO.getCurrencyName());
        currency.setCurrencySymbol(currencyDTO.getCurrencySymbol());
        currency.setStatus(currencyDTO.getStatus());
        return currency;
    }
    public static List<SupplierDTO> mapSupplierEntityToPojo(List<Supplier> supplierList){
        List<SupplierDTO> list=new ArrayList<>();
        for(Supplier supplier:supplierList) {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setSupplierId(supplier.getSupplierId());
            supplierDTO.setStatus(supplier.getStatus());
            supplierDTO.setCountry(supplier.getCountryName());
            supplierDTO.setCurrency(supplier.getCurrencyName());
            supplierDTO.setState(supplier.getStateName());
            supplierDTO.setStateName(supplier.getStateName());
            supplierDTO.setSupplierName(supplier.getSupplierName());
            supplierDTO.setGstIn(supplier.getGstCode());
            supplierDTO.setSupplierEmail(supplier.getEmail());
            supplierDTO.setSupplierNumber(supplier.getSupplierNumber());
            supplierDTO.setBillingAddress(supplier.getAddress());
            supplierDTO.setPersonIncharge(supplier.getPersonIncharge());
            supplierDTO.setBankName(supplier.getBankName());
            supplierDTO.setAccountNo(supplier.getAccountNo());
            supplierDTO.setPanNO(supplier.getPanNO());
            supplierDTO.setBranchName(supplier.getBranchName());
            supplierDTO.setiFSCCode(supplier.getiFSCCode());
            supplierDTO.setUin(supplier.getAttention());
            supplierDTO.setWebsite(supplier.getWebsite());
            supplierDTO.setCreditLimit(supplier.getCreditLimit());
            supplierDTO.setCreditDesc(supplier.getPeriodOfVerification());
            list.add(supplierDTO);
        }
        return list;
    }
    public static List<TaxDTO>  mapTaxEntityToTaxDTO(List<Tax> taxList) {
        List<TaxDTO> taxDTOList = new ArrayList<>();
        for (Tax tax : taxList) {
            TaxDTO dto = new TaxDTO();
            dto.setTaxString(tax.getTax_Per() + " | " + tax.getTaxCode() + " | " + tax.getTaxName());
            dto.setTaxid(tax.getTaxId());
            dto.setTaxPercentage(tax.getTax_Per());
            dto.setTaxName(tax.getTaxName());
            taxDTOList.add(dto);
        }
        return taxDTOList;
    }
    public static Supplier mapSupplierPojoToEntity(SupplierDTO supplierDTO){
        Supplier supplier = new Supplier();
        supplier.setSupplierId(supplierDTO.getSupplierId());
        supplier.setStatus(supplierDTO.getStatus());
        supplier.setSupplierName(supplierDTO.getSupplierName());
        supplier.setGstCode(supplierDTO.getGstIn());
        supplier.setEmail(supplierDTO.getSupplierEmail());
        supplier.setSupplierNumber(supplierDTO.getSupplierNumber());
        supplier.setAddress(supplierDTO.getBillingAddress());
        supplier.setPersonIncharge(supplierDTO.getPersonIncharge());
        supplier.setBankName(supplierDTO.getBankName());
        supplier.setAccountNo(supplierDTO.getAccountNo());
        supplier.setPanNO(supplierDTO.getPanNO());
        supplier.setBranchName(supplierDTO.getBranchName());
        supplier.setiFSCCode(supplierDTO.getiFSCCode());
        supplier.setAttention(supplierDTO.getUin());
        supplier.setWebsite(supplierDTO.getWebsite());
        supplier.setCreditLimit(supplierDTO.getCreditLimit());
        supplier.setPeriodOfVerification(supplierDTO.getCreditDesc());
        return supplier;
    }
    public static List<CustomerDTO> mapCustomerEntityToPojo(List<Customer> customerList){
        List<CustomerDTO> list=new ArrayList<>();
        for(Customer cust:customerList) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerId(cust.getCustomerId());
            customerDTO.setStatus(cust.getStatus());
            customerDTO.setCountry(cust.getCountryName());
            customerDTO.setStateName(cust.getStateName());
            customerDTO.setState(cust.getStateName());
            customerDTO.setCurrency(cust.getCurrencyName());
            customerDTO.setCustomerName(cust.getCustomerName());
            customerDTO.setGstIn(cust.getGstCode());
            customerDTO.setCustomerEmail(cust.getEmail());
            customerDTO.setCustomerContact(cust.getCustomerNumber());
            customerDTO.setBillingAddress(cust.getAddress());
            customerDTO.setPersonIncharge(cust.getPersonIncharge());
            customerDTO.setBankName(cust.getBankName());
            customerDTO.setAccountNo(cust.getAccountNo());
            customerDTO.setPanNO(cust.getPanNo());
            customerDTO.setBranchName(cust.getBranchName());
            customerDTO.setiFSCCode(cust.getiFSCCode());
            customerDTO.setUin(cust.getAttention());
            customerDTO.setWebsite(cust.getWebsite());
            customerDTO.setCreditTerms(cust.getCreditTerms());
            customerDTO.setCreditedLimit(cust.getCreditLimit());
            list.add(customerDTO);
        }
        return list;
    }
    public static Customer mapCustomerPojoToEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setGstCode(customerDTO.getGstIn());
        customer.setEmail(customerDTO.getCustomerEmail());
        customer.setCustomerNumber(customerDTO.getCustomerContact());
        customer.setShipToAddress(customerDTO.getBillingAddress());
        customer.setPersonIncharge(customerDTO.getPersonIncharge());
        customer.setStatus(customerDTO.getStatus());
        customer.setBankName(customerDTO.getBankName());
        customer.setAccountNo(customerDTO.getAccountNo());
        customer.setPanNo(customerDTO.getPanNO());
        customer.setBranchName(customerDTO.getBranchName());
        customer.setiFSCCode(customerDTO.getiFSCCode());
        customer.setAttention(customerDTO.getUin());
        customer.setWebsite(customerDTO.getWebsite());
        customer.setCreditLimit(customerDTO.getCreditedLimit());
        customer.setCreditTerms(customerDTO.getCreditTerms());
        return customer;
    }
    public static List<ItemUOMTypeDTO> mapUomEntityToPojo(List<UnitOfMeasurement> uomList){
        List<ItemUOMTypeDTO> list=new ArrayList<>();
        for(UnitOfMeasurement uom:uomList) {
            ItemUOMTypeDTO unitOfMeasurementDTO = new ItemUOMTypeDTO();
            unitOfMeasurementDTO.setUnitOfMeasurementId(uom.getUnitOfMeasurementId());
            unitOfMeasurementDTO.setUnitOfMeasurementName(uom.getUnitOfMeasurementName());
            unitOfMeasurementDTO.setUnitOfMeasurementDescription(uom.getUnitOfMeasurementDescription());
            unitOfMeasurementDTO.setUnitOfMeasurementStatus(uom.getUnitOfMeasurementStatus());
            list.add(unitOfMeasurementDTO);
        }
        return list;
    }
    public static UnitOfMeasurement mapUomPojoToEntity(UnitOfMeasurementDTO uomDTO){
        UnitOfMeasurement uom = new UnitOfMeasurement();
        uom.setUnitOfMeasurementId(uomDTO.getUnitOfMeasurementId());
        uom.setUnitOfMeasurementName(uomDTO.getUnitOfMeasurementName());
        uom.setUnitOfMeasurementDescription(uomDTO.getUnitOfMeasurementDescription());
        uom.setUnitOfMeasurementStatus(uomDTO.getUnitOfMeasurementStatus());
        return uom;
    }
    public static List<ItemBrandDTO> mapBrandEntityToPojo(List<ItemBrandName> brandList){
        List<ItemBrandDTO> brand=new ArrayList<>();
        for(ItemBrandName itemBrandName:brandList) {
            ItemBrandDTO brandDTO = new ItemBrandDTO();
            brandDTO.setBrandId(itemBrandName.getBrandId());
            brandDTO.setBrandName(itemBrandName.getBrandName());
            brandDTO.setBrandDescription(itemBrandName.getBrandDescription());
            brandDTO.setStatus(itemBrandName.getStatus());
            brand.add(brandDTO);
        }
        return brand;
    }
    public static ItemBrandName mapBrandPojoToEntity(ItemBrandDTO brandDTO){
        ItemBrandName brand = new ItemBrandName();
        brand.setBrandId(brandDTO.getBrandId());
        brand.setBrandName(brandDTO.getBrandName());
        brand.setBrandDescription(brandDTO.getBrandDescription());
        brand.setStatus(brandDTO.getStatus());
        return brand;
    }
    public static List<ItemTaxDTO> mapTaxEntityToPojo(List<Tax> taxList){
        List<ItemTaxDTO> tax=new ArrayList<>();
        for(Tax taxes:taxList) {
            ItemTaxDTO taxDTO = new ItemTaxDTO();
            taxDTO.setTaxId(taxes.getTaxId());
            taxDTO.setTaxName(taxes.getTaxName());
            taxDTO.setTaxCode(taxes.getTaxCode());
            taxDTO.setTaxDescription(taxes.getTaxDescription());
            taxDTO.setTaxStatus(taxes.getTaxStatus());
            taxDTO.setTaxTypeName(taxes.getTaxTypeName());
            taxDTO.setTax_Per(taxes.getTax_Per());
            taxDTO.setTaxGroup(taxes.getTaxGroup());
            tax.add(taxDTO);
        }
        return tax;
    }
    public static Tax mapTaxPojoToEntity(ItemTaxDTO taxDTO){
        Tax tax = new Tax();
        tax.setTaxId(taxDTO.getTaxId());
        tax.setTaxName(taxDTO.getTaxName());
        tax.setTaxCode(taxDTO.getTaxCode());
        tax.setTaxDescription(taxDTO.getTaxDescription());
        tax.setTaxStatus(taxDTO.getTaxStatus());
        tax.setTax_Per(taxDTO.getTax_Per());
        tax.setTaxGroup(taxDTO.getTaxGroup());
        return tax;
    }
    public static List<TaxDTO> mapTaxTypeEntityToPojo(List<TaxType> taxTypes){
        List<TaxDTO> tax=new ArrayList<>();
        for(TaxType taxType:taxTypes) {
            TaxDTO taxTypeDTO = new TaxDTO();
            taxTypeDTO.setTaxTypeId(taxType.getTaxTypeId());
            taxTypeDTO.setTaxName(taxType.getTaxTypeName());
            taxTypeDTO.setTaxDescription(taxType.getTaxTypeDesc());
            tax.add(taxTypeDTO);
        }
        return tax;
    }
    public static TaxType mapTaxTypePojoToEntity(TaxDTO taxDTO){
        TaxType taxType = new TaxType();
        taxType.setTaxTypeId(taxDTO.getTaxTypeId());
        taxType.setTaxTypeName(taxDTO.getTaxName());
        taxType.setTaxTypeDesc(taxDTO.getTaxDescription());
        return taxType;
    }
    public static List<HsnDTO> mapHSNEntityToPojo(List<Msiccode> msiccodes){
        List<HsnDTO> msic=new ArrayList<>();
        for(Msiccode msiccode:msiccodes) {
            HsnDTO hsnDTO = new HsnDTO();
            hsnDTO.setMscid(msiccode.getMscid());
            hsnDTO.setDescrip(msiccode.getDescription());
            hsnDTO.setMsiccode(msiccode.getCode());
            hsnDTO.setStatus(msiccode.getStatus());
            msic.add(hsnDTO);
        }
        return msic;
    }
    public static Msiccode mapHSNPojoToEntity(HsnDTO hsnDTO){
        Msiccode msic = new Msiccode();
        msic.setDescription(hsnDTO.getDescrip());
        msic.setMscid(hsnDTO.getMscid());
        msic.setCode(hsnDTO.getMsiccode());
        msic.setStatus(hsnDTO.getStatus());
        return msic;
    }
    public static List<addNewItemDTO> mapItemEntityToPojo(List<Item> items){
        List<addNewItemDTO> itemDTOS=new ArrayList<>();
        for(Item itemList:items) {
            addNewItemDTO itemDTO = new addNewItemDTO();
            itemDTO.setItemId(itemList.getItemId());
            itemDTO.setItemCode(itemList.getItemCode());
            itemDTO.setItemName(itemList.getItemName());
            itemDTO.setItemDesc(itemList.getItemDesc());
            itemDTO.setStock(itemList.getStock());
            itemDTO.setCess(itemList.getCESS());
            itemDTO.setSalesPrice(itemList.getSalesPrice());
            itemDTO.setUom(itemList.getUomName());
            itemDTO.setPurchasePrice(itemList.getPurchasePrice());
            itemDTO.setCertificateno(itemList.getCertificateno());
            itemDTO.setItemStatus(itemList.getItemStatus());
            itemDTO.setInclusiveJSON(itemList.getInclusiveJSON());
            itemDTO.setItemImage(itemList.getImageFile());
            itemDTO.setItemCategoryName(itemList.getCategoryName());
            itemDTO.setItemCatName(itemList.getCategoryName());
            itemDTO.setItemTyName(itemList.getTypeName());
            itemDTO.setItemIPTaxName(itemList.getIpTaxName());
            itemDTO.setItemOPTaxName(itemList.getOpTaxName());
            itemDTO.setInvmovementTypeName(itemList.getCountTypeName());
            itemDTO.setItemMSICName(itemList.getMsicName());
            itemDTO.setItemUOMTyName(itemList.getUomName());
            itemDTO.setItemBrandName(itemList.getBrandName());
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }
    public static Item mapItemPojoToEntity(addNewItemDTO itemDTO){
        Item item = new Item();
        if(itemDTO.getItemId()!=null)
            item.setItemId(itemDTO.getItemId());
        item.setItemCode(itemDTO.getItemCode());
        item.setItemName(itemDTO.getItemName());
        item.setItemDesc(itemDTO.getItemDesc());
        item.setCESS(itemDTO.getCess());
        item.setSalesPrice(itemDTO.getSalesPrice());
        item.setCountTypeName(itemDTO.getInvmovementTypeName());
        item.setTypeName(itemDTO.getItemTyName());
        item.setPurchasePrice(itemDTO.getPurchasePrice());
        item.setItemStatus(itemDTO.getItemStatus());
        item.setCertificateno(itemDTO.getCertificateno());
        item.setInclusiveJSON(itemDTO.getInclusiveJSON());
        item.setImageFile(itemDTO.getItemImage());
        return item;
    }
    public static List<ItemTypeDTO> mapItemTypeEntityToPojo(List<ItemType> items){
        List<ItemTypeDTO> itemTypeDTOS=new ArrayList<>();
        for(ItemType itemList:items) {
            ItemTypeDTO itemDTO = new ItemTypeDTO();
            itemDTO.setItemTypeId(itemList.getItemTypeId());
            itemDTO.setItemTypeName(itemList.getItemTypeName());
            itemDTO.setItemTypeDesc(itemList.getItemTypeDesc());
            itemTypeDTOS.add(itemDTO);
        }
        return itemTypeDTOS;
    }
    public static List<ItemOPTaxDTO> mapOPTaxEntityToPojo(List<Tax> taxList){
        List<ItemOPTaxDTO> tax=new ArrayList<>();
        for(Tax taxes:taxList) {
            ItemOPTaxDTO taxDTO = new ItemOPTaxDTO();
            taxDTO.setTaxOPId(taxes.getTaxId());
            taxDTO.setTaxName(taxes.getTaxName());
            taxDTO.setTaxCode(taxes.getTaxCode());
            taxDTO.setTaxDescription(taxes.getTaxDescription());
            taxDTO.setTaxStatus(taxes.getTaxStatus());
            taxDTO.setTax_Per(taxes.getTax_Per());
            tax.add(taxDTO);
        }
        return tax;
    }
    public static List<ItemIPTaxDTO> mapIPTaxEntityToPojo(List<Tax> taxList){
        List<ItemIPTaxDTO> tax=new ArrayList<>();
        for(Tax taxIP:taxList) {
            ItemIPTaxDTO taxDTO = new ItemIPTaxDTO();
            taxDTO.setTaxIPId(taxIP.getTaxId());
            taxDTO.setTaxName(taxIP.getTaxName());
            taxDTO.setTaxCode(taxIP.getTaxCode());
            taxDTO.setTaxDescription(taxIP.getTaxDescription());
            taxDTO.setTaxStatus(taxIP.getTaxStatus());
            taxDTO.setTax_Per(taxIP.getTax_Per());
            tax.add(taxDTO);
        }
        return tax;
    }
    public static List<BankDTO> mapBankEntityToPojo(List<Bank> bankList){
        List<BankDTO> list=new ArrayList<>();
        for(Bank bank:bankList) {
            BankDTO bankDTO = new BankDTO();
            bankDTO.setAccountNo(bank.getAccountNo());
            bankDTO.setBankId(  bank.getBankId());
            bankDTO.setAddress(bank.getAddress());
            bankDTO.setBankAccountNo(bank.getBankAccountNo());
            bankDTO.setBankEmail(bank.getBankEmail());
            bankDTO.setBankName(bank.getBankName());
            bankDTO.setiFSCCode(bank.getiFSCCode());
            bankDTO.setBranchName(bank.getBranchName());
            bankDTO.setDescription(bank.getDescription());
            bankDTO.setBankPhoneNo(bank.getBankPhoneNo());
            bankDTO.setStatus(bank.getStatus());
            list.add(bankDTO);
        }
        return list;
    }


    public static Agent mapAgentPojoToEntity(AgentDTO agentDTO){
        Agent agent = new Agent();
        agent.setAccountNo(agentDTO.getAccountNo());
        agent.setAgentId(agentDTO.getAgentId());
        agent.setAgentAccountCode(agentDTO.getAgentAccountCode());
        agent.setAgentName(agentDTO.getAgentName());
        agent.setCommission(agentDTO.getCommission());
        agent.setEmail(agentDTO.getEmail());
        agent.setAddress(agentDTO.getAddress());
        agent.setMobile(agentDTO.getMobile());
        agent.setGstinNo(agentDTO.getGstinNo());
        agent.setEcommerce(agentDTO.getEcommerce());
        agent.setEffectiveDate(agentDTO.getEffectiveDate());
        agent.setStatus(agentDTO.getStatus());
        return agent;
    }
    public static List<AgentDTO> mapAgentEntityToPojo(List<Agent> agentList){
        List<AgentDTO> list=new ArrayList<>();
        for(Agent agent:agentList) {
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setAgentId(  agent.getAgentId());
            agentDTO.setAccountNo(agent.getAccountNo());
            agentDTO.setAgentAccountCode(agent.getAgentAccountCode());
            agentDTO.setAgentName(agent.getAgentName());
            agentDTO.setCommission(agent.getCommission());
            agentDTO.setEmail(agent.getEmail());
            agentDTO.setAddress(agent.getAddress());
            agentDTO.setMobile(agent.getMobile());
            agentDTO.setGstinNo(agent.getGstinNo());
            agentDTO.setEcommerce(agent.getEcommerce());
            agentDTO.setEffectiveDate(agent.getEffectiveDate());
            agentDTO.setStatus(agent.getStatus());
            list.add(agentDTO);
        }
        return list;
    }
    public static PaymentMethod mapPaymentMethodPojoToEntity(PaymentMethodDTO paymentMethodDTO){
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setPaymentmethodName(paymentMethodDTO.getPaymentmethodName());
        paymentMethod.setPaymentmethodDescription(paymentMethodDTO.getPaymentmethodDescription());
        paymentMethod.setPaymentmethodType(paymentMethodDTO.getPaymentmethodType());
        paymentMethod.setStatus(paymentMethodDTO.getStatus());
        return paymentMethod;
    }
    public static List<PaymentMethodDTO> mapPaymentMethodEntityToPojo(List<PaymentMethod> paymentMethodList){
        List<PaymentMethodDTO> list=new ArrayList<>();
        for(PaymentMethod paymentMethod:paymentMethodList) {
            PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();
            paymentMethodDTO.setPaymentmethodId(paymentMethod.getPaymentmethodId());
            paymentMethodDTO.setPaymentmethodName(paymentMethod.getPaymentmethodName());
            paymentMethodDTO.setPaymentmethodDescription(paymentMethod.getPaymentmethodDescription());
            paymentMethodDTO.setPaymentmethodType(paymentMethod.getPaymentmethodType());
            paymentMethodDTO.setStatus(paymentMethod.getStatus());
            list.add(paymentMethodDTO);
        }
        return list;
    }
    public static List<FormsetupDTO> mapFormSetupEntityToPojo(List<FormSetUp> formSetUpList){
        List<FormsetupDTO> list=new ArrayList<>();
        for(FormSetUp formSetUp:formSetUpList) {
            FormsetupDTO formsetupDTO = new FormsetupDTO();
            formsetupDTO.setNextref(formSetUp.getNextref());
            formsetupDTO.setFormsetupId(formSetUp.getFormsetupId());
            formsetupDTO.setTypename(formSetUp.getTypename());
            formsetupDTO.setTransactionType(formSetUp.getTransactionType());
            formsetupDTO.setTypeprefix(formSetUp.getTypeprefix());
            list.add(formsetupDTO);
        }
        return list;
    }
    public  static FormSetUp mapFormSetupPojoToEntity(FormsetupDTO formsetupDTO){
        FormSetUp formSetUp = new FormSetUp();
        formSetUp.setFormsetupId(formsetupDTO.getFormsetupId());
        formSetUp.setTypeprefix(formsetupDTO.getTypeprefix());
        formSetUp.setNextref(formsetupDTO.getNextref());
        formSetUp.setTypename(formsetupDTO.getTypename());
        formSetUp.setTransactionType(formsetupDTO.getTransactionType());
        return formSetUp;
    }
    public static Bank mapBankPojoToEntity(BankDTO bankDTO){
        Bank bank = new Bank();
        bank.setAccountNo(bankDTO.getAccountNo());
        bank.setAddress(bankDTO.getAddress());
        bank.setBankAccountNo(bankDTO.getBankAccountNo());
        bank.setBankEmail(bankDTO.getBankEmail());
        bank.setBankName(bankDTO.getBankName());
        bank.setBankId(bankDTO.getBankId());
        bank.setiFSCCode(bankDTO.getiFSCCode());
        bank.setBranchName(bankDTO.getBranchName());
        bank.setDescription(bankDTO.getDescription());
        bank.setBankPhoneNo(bankDTO.getBankPhoneNo());
        bank.setStatus(bankDTO.getStatus());
        return bank;
    }
    public static List<ItemCountTypeDTO> mapInvMovEntityToPojo(List<InventoryMovement> movementList){
        List<ItemCountTypeDTO> countTypeDTOS=new ArrayList<>();
        for(InventoryMovement movement:movementList) {
            ItemCountTypeDTO countTypeDTO = new ItemCountTypeDTO();
            countTypeDTO.setInventoryMovementId(movement.getInventoryMovementId());
            countTypeDTO.setInventoryMovementName(movement.getInventoryMovementName());
            countTypeDTO.setInventoryMovementDesc(movement.getInventoryMovementDesc());
            countTypeDTO.setInventoryMovementStatus(movement.getInventoryMovementStatus());
            countTypeDTOS.add(countTypeDTO);
        }
        return countTypeDTOS;
    }
}
