package com.hyva.posretail.pos.posService;

import com.hyva.posretail.pos.posEntities.*;
import com.hyva.posretail.pos.posEntities.Currency;
import com.hyva.posretail.pos.posMapper.*;
import com.hyva.posretail.pos.posPojo.*;
import com.hyva.posretail.pos.posRespositories.*;
import com.hyva.posretail.pos.posUtil.ObjectMapperUtils;
import com.hyva.posretail.pusher.PusherService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


@Component
public class PosService {

    @Autowired
    PosUserRepository posUserRepository;
    @Autowired
    PosMailRepository posMailRepository;
    @Autowired
    PosSchedulerRepository posSchedulerRepository;
    @Autowired
    PusherService pusherService;
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
    @Autowired
    SalesService salesService;
    @Autowired
    PurchaseService purchaseService;
    int paginatedConstants=5;

    public User userValidate(PosUserPojo posUserPojo) {
        User user = posUserRepository.findByUserNameAndAndPasswordUser(
                posUserPojo.getUserName(), posUserPojo.getPasswordUser());
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
    public List<ItemCategoryDTO> getItemCategoryList(String status,String searchText) {
        List<ItemCategory> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status="Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = posCategoryRepository.findAllByStatus(status);
        } else {
            list = posCategoryRepository.findByStatusAndItemCategoryName(status,searchText);
        }
        List<ItemCategoryDTO> itemCategoryDTOList = PosItemMapper.mapEntityToPojo(list);
        return itemCategoryDTOList;
    }
//    public BasePojo getPaginatedItemCategoryList(String status,String searchText,BasePojo basePojo) {
//        List<ItemCategory> list = new ArrayList<>();
//        basePojo.setPageSize(paginatedConstants);
//        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"itemCategoryId"));
//        if(basePojo.isLastPage()==true){
//            List<ItemCategory> list1=new ArrayList<>();
//            if (!StringUtils.isEmpty(searchText)) {
//                list1 = posCategoryRepository.findAllByItemCategoryCodeContainingOrItemCategoryNameContainingAndStatus(searchText,searchText,status);
//            }else {
//                list1=posCategoryRepository.findAllByStatus(status);
//            }
//            int size=list1.size()%paginatedConstants;
//            if(size!=0){
//                basePojo.setPageSize(size);
//            }
//            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "itemCategoryId"));
//        }
//        if(StringUtils.isEmpty(status)){
//            status="Active";
//        }
//        ItemCategory itemCategory=new ItemCategory();
//        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
//        if(basePojo.isNextPage()==true|| basePojo.isFirstPage()==true){
//            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"itemCategoryId"));
//        }
//        if (!StringUtils.isEmpty(searchText)) {
//            itemCategory=posCategoryRepository.findFirstByItemCategoryCodeContainingOrItemCategoryNameContainingAndStatus(searchText,searchText,status,sort);
//            list = posCategoryRepository.findAllByItemCategoryCodeContainingOrItemCategoryNameContainingAndStatus(searchText,searchText,status,pageable);
//        } else {
//            itemCategory=posCategoryRepository.findFirstByStatus(status,sort);
//            list = posCategoryRepository.findAllByStatus(status,pageable);
//        }
//        if(list.contains(itemCategory)){
//            basePojo.setStatus(true);
//        }else {
//            basePojo.setStatus(false);
//        }
//        List<ItemCategoryDTO> itemCategoryDTOList = PosItemMapper.mapEntityToPojo(list);
//        basePojo=calculatePagination(basePojo,itemCategoryDTOList.size());
//        basePojo.setList(itemCategoryDTOList);
//        return basePojo;
//    }
//


    public void deleteCategory(Long itemCategoryId) {
        posCategoryRepository.delete(itemCategoryId);
    }

    public ItemCategoryDTO editCategory(String categoryName) {
        ItemCategory itemCategory = posCategoryRepository.findByItemCategoryName(categoryName);
        List<ItemCategory> itemCategories = new ArrayList<>();
        itemCategories.add(itemCategory);
        ItemCategoryDTO itemCategoryDTO = PosItemMapper.mapEntityToPojo(itemCategories).get(0);
        return itemCategoryDTO;
    }

    public User saveUserDetails(PosUserPojo posUserPojo) {
        User user = null;
        user = posUserRepository.findByEmail(posUserPojo.getEmail());
        if (user != null) {
            user = null;
        } else {
            user = PosUserMapper.mapPojoToEntity(posUserPojo);
            posUserRepository.save(user);
        }
        return user;
    }


//    public ItemCategory SaveCategory(ItemCategoryDTO itemCategoryDTO) {
//        ItemCategory itemCategory = new ItemCategory();
//        if(itemCategoryDTO.getItemCategoryId()!=null){
//            itemCategory=posCategoryRepository.findByItemCategoryNameAndItemCategoryIdNotIn(itemCategoryDTO.getItemCategoryName(),itemCategoryDTO.getItemCategoryId());
//        }
//        else{
//            itemCategory=posCategoryRepository.findByItemCategoryName(itemCategoryDTO.getItemCategoryName());
//        }
//        if(itemCategory==null){
//            ItemCategory itemCategory1 = PosService.mapItemCategoryPojoToEntity(itemCategoryDTO);
//            PosCategoryRepository.save(itemCategory1);
//            return itemCategory1;
//        }else{
//            return null;
//        }
//
//    }


       public ItemBrandName SaveBrand(ItemBrandDTO itemBrandDTO) {
        ItemBrandName itemBrandName=new ItemBrandName();
        if(itemBrandDTO.getBrandId()!=null){
            itemBrandName=brandRepository.findByBrandNameAndBrandIdNotIn(itemBrandDTO.getBrandName(),itemBrandDTO.getBrandId());
        }else {
            itemBrandName=brandRepository.findByBrandName(itemBrandDTO.getBrandName());
        }
        if(itemBrandName==null) {
            ItemBrandName itemBrandName1 = PosItemMapper.mapItemBrandPojoToEntity(itemBrandDTO);
//           ItemCategory itemCategory1 = posCategoryRepository.findByItemCategoryName(itemCategoryDTO.getItemCategoryName());
            itemBrandName1.setBrandName(itemBrandName1.getBrandName());
            brandRepository.save(itemBrandName1);
            return itemBrandName1;
        }else {
            return null;
        }
    }


    public ItemCategory SaveCategory(ItemCategoryDTO itemCategoryDTO) {
        ItemCategory itemCategory=new ItemCategory();
        if(itemCategoryDTO.getItemCategoryId()!=null){
            itemCategory=posCategoryRepository.findByItemCategoryNameAndItemCategoryIdNotIn(itemCategoryDTO.getItemCategoryName(),itemCategoryDTO.getItemCategoryId());
        }else {
            itemCategory=posCategoryRepository.findByItemCategoryName(itemCategoryDTO.getItemCategoryName());
        }
        if(itemCategory==null) {
            ItemCategory itemCategory1 = PosItemMapper.mapItemCategoryPojoToEntity(itemCategoryDTO);
//          ItemCategory itemCategory1 = posCategoryRepository.findByItemCategoryName(itemCategoryDTO.getItemCategoryName());
            itemCategory1.setItemCategoryName(itemCategory1.getItemCategoryName());
            posCategoryRepository.save(itemCategory1);
            return itemCategory1;
        }else {
            return null;
        }
    }



    public List<PosUserPojo> sassUserList() {
        List<User> users = (List<User>) posUserRepository.findAll();
        List<PosUserPojo> posUserPojoList = ObjectMapperUtils.mapAll(users, PosUserPojo.class);
        return posUserPojoList;
    }

    public BasePojo getPaginatedStateList(String status,BasePojo basePojo,String searchText) {
        List<State> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        if(basePojo.isLastPage()==true){
            List<State> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posStateRepository.findAllByStatusAndCountryNameContainingOrStateNameContaining(searchText,searchText,status);
            }else {
                list1=posStateRepository.findAllByStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageNo(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        }
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        State state=new State();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"id"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            state=posStateRepository.findFirstByStatusAndCountryNameContainingOrStateNameContaining(status,searchText,searchText,sort);
            list = posStateRepository.findAllByStatusAndCountryNameContainingOrStateNameContaining(status,searchText,searchText,pageable);
        } else {
            state=posStateRepository.findFirstByStatus(status,sort);
            list = posStateRepository.findAllByStatus(status,pageable);
        }
        if(list.contains(state)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<StateDTO> stateList = PosItemMapper.mapStateEntityToPojo(list);
        basePojo=calculatePagination(basePojo,stateList.size());
        basePojo.setList(stateList);
        return basePojo;
    }
    public List<StateDTO> getStateList(String status,String searchText) {
        List<State> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status="Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = posStateRepository.findAllByStatus(status);
        } else {
            list = posStateRepository.findAllByStatusAndStateNameContaining(status,searchText);
        }
        List<StateDTO> stateList = PosItemMapper.mapStateEntityToPojo(list);
        return stateList;
    }
    public BasePojo calculatePagination(BasePojo basePojo,int size){
        if(basePojo.isLastPage()==true){
            basePojo.setFirstPage(false);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(true);
        }else if(basePojo.isFirstPage()==true){
            basePojo.setLastPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(true);
            if(basePojo.isStatus()==true){
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        }else if(basePojo.isNextPage()==true){
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setPrevPage(false);
            basePojo.setNextPage(false);
            if(basePojo.isStatus()==true){
                basePojo.setLastPage(true);
                basePojo.setNextPage(true);
            }
        }else if(basePojo.isPrevPage()==true){
            basePojo.setLastPage(false);
            basePojo.setFirstPage(false);
            basePojo.setNextPage(false);
            basePojo.setPrevPage(false);
            if(basePojo.isStatus()==true){
                basePojo.setPrevPage(true);
                basePojo.setFirstPage(true);
            }
        }
        if(size==0){
            basePojo.setLastPage(true);
            basePojo.setFirstPage(true);
            basePojo.setNextPage(true);
            basePojo.setPrevPage(true);
        }
        return basePojo;
    }


    public State saveState(StateDTO stateDTO) {
        State state1=new State();
        if(stateDTO.getId()!=null){
            state1=posStateRepository.findByStateNameAndIdNotIn(stateDTO.getStateName(),stateDTO.getId());
        }else {
            state1=posStateRepository.findByStateName(stateDTO.getStateName());
        }
        if(state1==null) {
            State state = PosItemMapper.mapStatePojoToEntity(stateDTO);
            Country country = posCountryRepository.findByCountryName(stateDTO.getCountry());
            state.setCountryName(country.getCountryName());
            posStateRepository.save(state);
            return state;
        }else {
            return null;
        }
    }

    public void deleteState(String stateName) {
        posStateRepository.delete(posStateRepository.findByStateName(stateName));
    }

    public StateDTO editState(String stateName) {
        State state = posStateRepository.findByStateName(stateName);
        List<State> states = new ArrayList<>();
        states.add(state);
        StateDTO stateDTO = PosItemMapper.mapStateEntityToPojo(states).get(0);
        return stateDTO;
    }

    public List<CountryDTO> getCountryList(String status,String searchText) {
        List<Country> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status = "Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = posCountryRepository.findAllByStatus(status);
        } else {
            list = posCountryRepository.findAllByStatusAndCountryNameContaining(status,searchText);
        }
        List<CountryDTO> country = PosItemMapper.mapCountryEntityToPojo(list);
        return country;
    }
    public BasePojo getPaginatedCountryList(String status,BasePojo basePojo,String searchText) {
        List<Country> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"countryId"));
        if(basePojo.isLastPage()==true){
            List<Country> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posCountryRepository.findAllByCountryNameContainingAndStatus(searchText,status);
            }else {
                list1=posCountryRepository.findAllByStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "countryId"));
        }
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        Country country=new Country();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"countryId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            country=posCountryRepository.findFirstByCountryNameContainingAndStatus(searchText,status,sort);
            list = posCountryRepository.findAllByCountryNameContainingAndStatus(searchText,status,pageable);
        } else {
            country=posCountryRepository.findFirstByStatus(status,sort);
            list = posCountryRepository.findAllByStatus(status,pageable);
        }
        if(list.contains(country)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<CountryDTO> countryDtoList = PosItemMapper.mapCountryEntityToPojo(list);
        basePojo=calculatePagination(basePojo,countryDtoList.size());
        basePojo.setList(countryDtoList);
        return basePojo;
    }
    public Country saveCountry(CountryDTO countryDTO) {
        Country countries = new Country();
        if(countryDTO.getCountryId()!=null){
            countries=posCountryRepository.findByCountryNameAndCountryIdNotIn(countryDTO.getCountryName(),countryDTO.getCountryId());
        }
        else{
            countries=posCountryRepository.findByCountryName(countryDTO.getCountryName());
        }
        if(countries==null){
            Country country = PosItemMapper.mapCountryPojoToEntity(countryDTO);
            posCountryRepository.save(country);
            return country;
        }else{
            return null;
        }

    }

    public void deleteCountry(String  countryName) {
        posCountryRepository.delete(posCountryRepository.findByCountryName(countryName));
    }

    public CountryDTO editCountry(String countryName) {
        Country country = posCountryRepository.findByCountryName(countryName);
        List<Country> countries = new ArrayList<>();
        countries.add(country);
        CountryDTO countryDTO = PosItemMapper.mapCountryEntityToPojo(countries).get(0);
        return countryDTO;
    }

    public List<CurrencyDTO> getCurrencyList(String status,String searchText) {
        List<Currency> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status="Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = currencyRepository.findAllByStatus(status);
        } else {
            list = currencyRepository.findAllByStatusAndCurrencyNameContaining(status,searchText);
        }

        List<CurrencyDTO> currency = PosItemMapper.mapCurrencyEntityToPojo(list);
        return currency;
    }
    public BasePojo getPaginatedCurrencyList(String status,BasePojo basePojo,String searchText) {
        List<Currency> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"currencyId"));
        if(basePojo.isLastPage()==true){
            List<Currency> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = currencyRepository.findAllByCurrencyNameContainingAndStatus(searchText,status);
            }else {
                list1=currencyRepository.findAllByStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "currencyId"));
        }
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        Currency currency=new Currency();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"currencyId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            currency=currencyRepository.findFirstByCurrencyNameContainingAndStatus(searchText,status,sort);
            list = currencyRepository.findAllByCurrencyNameContainingAndStatus(searchText,status,pageable);
        } else {
            currency=currencyRepository.findFirstByStatus(status,sort);
            list = currencyRepository.findAllByStatus(status,pageable);
        }
        if(list.contains(currency)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<CurrencyDTO> currencyDTOList = PosItemMapper.mapCurrencyEntityToPojo(list);
        basePojo=calculatePagination(basePojo,currencyDTOList.size());
        basePojo.setList(currencyDTOList);
        return basePojo;
    }
    public Currency saveCurrency(CurrencyDTO currencyDTO) {
        Currency currencies = new Currency();
        if(currencyDTO.getCurrencyId()!=null){
            currencies=currencyRepository.findByCurrencyNameAndCurrencyIdNotIn(currencyDTO.getCurrencyName(),currencyDTO.getCurrencyId());

        }else{
            currencies=currencyRepository.findByCurrencyName(currencyDTO.getCurrencyName());
        }
        if(currencies==null){
            Currency currency = PosItemMapper.mapCurrencyPojoToEntity(currencyDTO);
            currencyRepository.save(currency);
            return currency;
        }else{
            return  null;
        }

    }

    public void deleteCurrency(String  currencyName) {
        currencyRepository.delete(currencyRepository.findByCurrencyName(currencyName));
    }

    public CurrencyDTO editCurrency(String currencyName) {
        Currency currency = currencyRepository.findByCurrencyName(currencyName);
        List<Currency> currencies = new ArrayList<>();
        currencies.add(currency);
        CurrencyDTO countryDTO = PosItemMapper.mapCurrencyEntityToPojo(currencies).get(0);
        return countryDTO;
    }

    public List<SupplierDTO> getSupplierList(String status,String searchText) {
        List<Supplier> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
           status="Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = posSupplierRepository.findAllByStatus(status);
        } else {
            list = posSupplierRepository.findByStatusAndSupplierNameContaining(status,searchText);
        }
        List<SupplierDTO> supplier = PosItemMapper.mapSupplierEntityToPojo(list);
        return supplier;
    }
    public BasePojo getPaginatedSupplierList(String status,BasePojo basePojo,String searchText) {
        List<Supplier> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"supplierId"));
        if(basePojo.isLastPage()==true){
            List<Supplier> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posSupplierRepository.findAllBySupplierNameContainingAndStatus(searchText,status);
            }else {
                list1=posSupplierRepository.findAllByStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "supplierId"));
        }
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        Supplier supplier=new Supplier();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"supplierId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            supplier=posSupplierRepository.findFirstBySupplierNameContainingAndStatus(searchText,status,sort);
            list = posSupplierRepository.findAllBySupplierNameContainingAndStatus(searchText,status,pageable);
        } else {
            supplier=posSupplierRepository.findFirstByStatus(status,sort);
            list = posSupplierRepository.findAllByStatus(status,pageable);
        }
        if(list.contains(supplier)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<SupplierDTO> supplierDTOList = PosItemMapper.mapSupplierEntityToPojo(list);
        basePojo=calculatePagination(basePojo,supplierDTOList.size());
        basePojo.setList(supplierDTOList);
        return basePojo;
    }
    public Supplier saveSupplier(SupplierDTO supplierDTO) {
        Supplier suppliers=new Supplier();
        if(supplierDTO.getSupplierId()!=null){
            suppliers=posSupplierRepository.findBySupplierNameAndSupplierIdNotIn(supplierDTO.getSupplierName(),supplierDTO.getSupplierId());
        }else{
            suppliers=posSupplierRepository.findBySupplierName(supplierDTO.getSupplierName());
        }
        if(suppliers==null){
            Supplier supplier = PosItemMapper.mapSupplierPojoToEntity(supplierDTO);
            Country country = posCountryRepository.findByCountryName(supplierDTO.getCountry());
            supplier.setCountryName(country.getCountryName());
            State state = posStateRepository.findByStateName(supplierDTO.getState());
            supplier.setStateName(state.getStateName());
            Currency currency = currencyRepository.findByCurrencyName(supplierDTO.getCurrency());
            supplier.setCurrencyName(currency.getCurrencyName());
            posSupplierRepository.save(supplier);
            return supplier;
        }else{
            return null;
        }

    }

    public static Date parseDate(String strDate) {
        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        f.setTimeZone(utc);
        GregorianCalendar cal = new GregorianCalendar(utc);
        try {
            cal.setTime(f.parse(strDate));
        } catch (Exception e) {
            e.getMessage();
        }
        return cal.getTime();
    }

    public void deleteSupplier(Long supplierId) {
        posSupplierRepository.delete(supplierId);
    }

    public SupplierDTO editSupplier(String supplierName) {
        Supplier supplier = posSupplierRepository.findBySupplierName(supplierName);
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(supplier);
        SupplierDTO supplierDTO = PosItemMapper.mapSupplierEntityToPojo(suppliers).get(0);
        List<Country> country = posCountryRepository.findAll();
        supplierDTO.setCountryDTOList(PosItemMapper.mapCountryEntityToPojo(country));
        List<State> state = posStateRepository.findAll();
        supplierDTO.setStateDTOList(PosItemMapper.mapStateEntityToPojo(state));
        List<Currency> currency = currencyRepository.findAll();
        supplierDTO.setCurrencyDTOList(PosItemMapper.mapCurrencyEntityToPojo(currency));
        return supplierDTO;
    }

    public List<CustomerDTO> getCustomerList(String status,String searchText) {
        List<Customer> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status="Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = posCustomerRepository.findAllByStatus(status);
        } else {
            list = posCustomerRepository.findByStatusAndCustomerNameContaining(status,searchText);
        }
        List<CustomerDTO> customer = PosItemMapper.mapCustomerEntityToPojo(list);
        return customer;
    }
    public BasePojo getPaginatedCustomerList(String status,BasePojo basePojo,String searchText) {
        List<Customer> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"customerId"));
        if(basePojo.isLastPage()==true){
            List<Customer> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posCustomerRepository.findAllByCustomerNameContainingAndStatus(searchText,status);
            }else {
                list1=posCustomerRepository.findAllByStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "customerId"));
        }
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        Customer customer=new Customer();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"customerId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            customer=posCustomerRepository.findFirstByCustomerNameContainingAndStatus(searchText,status,sort);
            list = posCustomerRepository.findAllByCustomerNameContainingAndStatus(searchText,status,pageable);
        } else {
            customer=posCustomerRepository.findFirstByStatus(status,sort);
            list = posCustomerRepository.findAllByStatus(status,pageable);
        }
        if(list.contains(customer)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<CustomerDTO> customerDTOList = PosItemMapper.mapCustomerEntityToPojo(list);
        basePojo=calculatePagination(basePojo,customerDTOList.size());
        basePojo.setList(customerDTOList);
        return basePojo;
    }
    public Customer saveCustomer(CustomerDTO customerDTO) {
        Customer customers=new Customer();
        if(customerDTO.getCustomerId()!=null){
            customers=posCustomerRepository.findAllByCustomerNameAndCustomerIdNotIn(customerDTO.getCustomerName(),customerDTO.getCustomerId());
        }else{
            customers=posCustomerRepository.findAllByCustomerName(customerDTO.getCustomerName());
        }
        if(customers==null){
            Customer customer = PosItemMapper.mapCustomerPojoToEntity(customerDTO);
            Country country = posCountryRepository.findByCountryName(customerDTO.getCountry());
            customer.setCountryName(country.getCountryName());
            State state = posStateRepository.findByStateName(customerDTO.getState());
            customer.setStateName(state.getStateName());
            Currency currency = currencyRepository.findByCurrencyName(customerDTO.getCurrency());
            customer.setCurrencyName(currency.getCurrencyName());
            posCustomerRepository.save(customer);
            return customer;
        }else{
            return null;
        }

    }

    public void deleteCustomer(Long customerId) {
        posCustomerRepository.delete(customerId);
    }

    public CustomerDTO editCustomer(String customerName) {
        Customer customer = posCustomerRepository.findAllByCustomerName(customerName);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        CustomerDTO customerDTO = PosItemMapper.mapCustomerEntityToPojo(customers).get(0);
        List<Country> country = posCountryRepository.findAll();
        customerDTO.setCountryDTOList(PosItemMapper.mapCountryEntityToPojo(country));
        List<State> state = posStateRepository.findAll();
        customerDTO.setStateDTOList(PosItemMapper.mapStateEntityToPojo(state));
        List<Currency> currency = currencyRepository.findAll();
        customerDTO.setCurrencyDTOList(PosItemMapper.mapCurrencyEntityToPojo(currency));
        return customerDTO;
    }

    public SupplierDTO getAllLists() {
        SupplierDTO supplierDTO = new SupplierDTO();
        List<Country> countryList = new ArrayList<>();
        countryList = posCountryRepository.findAll();
        List<CountryDTO> country = PosItemMapper.mapCountryEntityToPojo(countryList);
        supplierDTO.setCountryDTOList(country);
        List<State> stateList = new ArrayList<>();
        stateList = posStateRepository.findAll();
        List<StateDTO> state = PosItemMapper.mapStateEntityToPojo(stateList);
        supplierDTO.setStateDTOList(state);
        List<Currency> currencyList = new ArrayList<>();
        currencyList = currencyRepository.findAll();
        List<CurrencyDTO> currency = PosItemMapper.mapCurrencyEntityToPojo(currencyList);
        supplierDTO.setCurrencyDTOList(currency);
        return supplierDTO;
    }

    public List<StateDTO> getCountryState(String countryName) {
        List<State> states = posStateRepository.findAllByCountryName(countryName);
        List<StateDTO> stateList = PosItemMapper.mapStateEntityToPojo(states);
        return stateList;
    }

    public List<ItemUOMTypeDTO> getUOMList(String status,String searchText) {
        List<UnitOfMeasurement> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status="Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = posUomRepository.findAllByUnitOfMeasurementStatus(status);
        } else {
            list = posUomRepository.findAllByUnitOfMeasurementStatusAndUnitOfMeasurementNameContaining(status,searchText);
        }

        List<ItemUOMTypeDTO> uomList = PosItemMapper.mapUomEntityToPojo(list);
        return uomList;
    }
    public BasePojo getPaginatedUOMList(String status,String searchText,BasePojo basePojo) {
        List<UnitOfMeasurement> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"unitOfMeasurementId"));
        if(basePojo.isLastPage()==true){
            List<UnitOfMeasurement> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posUomRepository.findAllByUnitOfMeasurementNameContainingAndUnitOfMeasurementStatus(searchText,status);
            }else {
                list1=posUomRepository.findAllByUnitOfMeasurementStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "unitOfMeasurementId"));
        }
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        UnitOfMeasurement unitOfMeasurement=new UnitOfMeasurement();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true|| basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"unitOfMeasurementId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            unitOfMeasurement=posUomRepository.findFirstByUnitOfMeasurementNameContainingAndUnitOfMeasurementStatus(searchText,status,sort);
            list = posUomRepository.findAllByUnitOfMeasurementNameContainingAndUnitOfMeasurementStatus(searchText,status,pageable);
        } else {
            unitOfMeasurement=posUomRepository.findFirstByUnitOfMeasurementStatus(status,sort);
            list = posUomRepository.findAllByUnitOfMeasurementStatus(status,pageable);
        }
        if(list.contains(unitOfMeasurement)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<ItemUOMTypeDTO> uomTypeDTOList = PosItemMapper.mapUomEntityToPojo(list);
        basePojo=calculatePagination(basePojo,uomTypeDTOList.size());
        basePojo.setList(uomTypeDTOList);
        return basePojo;
    }


    public BasePojo getPaginatedCategoryList(String status,String searchText,BasePojo basePojo) {
        List<ItemCategory> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"itemCategoryId"));
        if(basePojo.isLastPage()==true){
            List<ItemCategory> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = (List<ItemCategory>) posCategoryRepository.findFirstByItemCategoryNameContainingAndStatus(searchText,status,sort);
            }else {
                list1=posCategoryRepository.findAllByStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "itemCategoryId"));
        }
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        ItemCategory itemCategory=new ItemCategory();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true|| basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"itemCategoryId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            itemCategory=posCategoryRepository.findFirstByItemCategoryNameContainingAndStatus(searchText,status,sort);
            list = posCategoryRepository.findAllByItemCategoryNameContainingAndStatus(searchText, status,pageable);
        } else {
            itemCategory=posCategoryRepository.findFirstByStatus(status,sort);
            list = posCategoryRepository.findAllByStatus(status,pageable);
        }
        if(list.contains(itemCategory)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<ItemCategoryDTO> itemCategoryDTOS = PosItemMapper.mapItemCategoryEntityToPojo(list);
        basePojo=calculatePagination(basePojo,itemCategoryDTOS.size());
        basePojo.setList(itemCategoryDTOS);
        return basePojo;
    }


    public UnitOfMeasurement saveUom(UnitOfMeasurementDTO unitOfMeasurementDTO) {
        UnitOfMeasurement unitOfMeasurement =new UnitOfMeasurement();
        if(unitOfMeasurementDTO.getUnitOfMeasurementId()!=null){
            unitOfMeasurement=posUomRepository.findByUnitOfMeasurementNameAndUnitOfMeasurementIdNotIn(unitOfMeasurementDTO.getUnitOfMeasurementName(),unitOfMeasurementDTO.getUnitOfMeasurementId());
        }else{
            unitOfMeasurement=posUomRepository.findByUnitOfMeasurementName(unitOfMeasurementDTO.getUnitOfMeasurementName());
        }
        if(unitOfMeasurement==null){
            UnitOfMeasurement uom = PosItemMapper.mapUomPojoToEntity(unitOfMeasurementDTO);
            posUomRepository.save(uom);
            return uom;
        }else{
            return null;
        }

    }

    public void deleteUom(String uomName) {
        posUomRepository.delete(posUomRepository.findByUnitOfMeasurementName(uomName));
    }

    public ItemUOMTypeDTO editUom(String unitOfMeasurementName) {
        UnitOfMeasurement unit = posUomRepository.findByUnitOfMeasurementName(unitOfMeasurementName);
        List<UnitOfMeasurement> uom = new ArrayList<>();
        uom.add(unit);
        ItemUOMTypeDTO uomDTO = PosItemMapper.mapUomEntityToPojo(uom).get(0);
        return uomDTO;
    }
    public List<ItemBrandDTO> getBrandList(String status,String searchText) {
        List<ItemBrandName> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status="Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = brandRepository.findByStatus(status);
        } else {
            list = brandRepository.findAllByBrandNameContainingAndStatus(searchText,status);
        }

        List<ItemBrandDTO> brandDTOList = PosItemMapper.mapBrandEntityToPojo(list);
        return brandDTOList;
    }

    public List<MailDTO> getMailList() {
        List<Mail> list = new ArrayList<>();
        list = posMailRepository.findAll();
        List<MailDTO> mailDTOList = PosItemMapper.mapMailEntityToPojo(list);
        return mailDTOList;
    }
    public BasePojo getPaginatedBrandList(String status,String searchText,BasePojo basePojo) {
        List<ItemBrandName> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"brandId"));
        if(basePojo.isLastPage()==true){
            List<ItemBrandName> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = brandRepository.findAllByBrandNameContainingAndStatus(searchText,status);
            }else {
                list1=brandRepository.findAllByStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageNo(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.DESC, "brandId"));
        }
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        ItemBrandName itemBrandName=new ItemBrandName();
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"brandId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            //fetching first record
            itemBrandName=brandRepository.findFirstByBrandNameContainingAndStatus(searchText,status,sort);
            list = brandRepository.findAllByBrandNameContainingAndStatus(searchText,status,pageable);
        } else {
            itemBrandName=brandRepository.findFirstByStatus(status,sort);
            list = brandRepository.findAllByStatus(status,pageable);
        }
        if(list.contains(itemBrandName)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<ItemBrandDTO> brandDTOList = PosItemMapper.mapBrandEntityToPojo(list);
        basePojo=calculatePagination(basePojo,brandDTOList.size());
        basePojo.setList(brandDTOList);
        return basePojo;
    }

    public ItemBrandName saveBrand(ItemBrandDTO itemBrandDTO) {
        ItemBrandName itemBrandName =new ItemBrandName();
        if(itemBrandDTO.getBrandId()!=null){
            itemBrandName=brandRepository.findByBrandNameAndBrandIdNotIn(itemBrandDTO.getBrandName(),itemBrandDTO.getBrandId());
        }
        else{
            itemBrandName=brandRepository.findByBrandName(itemBrandDTO.getBrandName());
        }
        if(itemBrandName==null){
            ItemBrandName brandName = PosItemMapper.mapBrandPojoToEntity(itemBrandDTO);
            brandRepository.save(brandName);
            return brandName;
        }
        else{
            return null;
        }

    }
    public MailDTO createSaveMailDetails(MailDTO saveMailDetails) {
        Mail mail =posMailRepository.findOne(1L);
        if(mail==null)
        {
            mail=new Mail();
        }
        mail.setUserName(saveMailDetails.getUserName());
        mail.setPassword(saveMailDetails.getPassword());
        mail.setPort(saveMailDetails.getPort());
        mail.setSmtpHost(saveMailDetails.getSmtpHost());
        mail.setForMail(saveMailDetails.getForMail());
        mail.setStatus("Active");
        posMailRepository.save(mail);
        return saveMailDetails;
    }

    public MailDTO editMail(String name) {
        Mail mail = posMailRepository.findByUserName(name);
        List<Mail> mails = new ArrayList<>();
        mails.add(mail);
        MailDTO mailDTO = PosItemMapper.mapMailEntityToPojo(mails).get(0);
        return mailDTO;
    }

    public void deleteMail(String  userName) {
        posMailRepository.delete(posMailRepository.findByUserName(userName));
    }

    public void deleteBrand(String  brandName) {
        brandRepository.delete(brandRepository.findByBrandName(brandName));
    }

    public ItemBrandDTO editBrand(String name) {
        ItemBrandName brandName = brandRepository.findByBrandName(name);
        List<ItemBrandName> itemBrandNames = new ArrayList<>();
        itemBrandNames.add(brandName);
        ItemBrandDTO brandDTO = PosItemMapper.mapBrandEntityToPojo(itemBrandNames).get(0);
        return brandDTO;
    }

    public List<ItemTaxDTO> getTaxList(String status,String searchText) {
        List<Tax> list = new ArrayList<>();
        if(StringUtils.isEmpty(status)){
           status="Active";
        }
        if(StringUtils.isEmpty(searchText)){
            list = posTaxRepository.findByTaxStatus(status);
        }else{
            list = posTaxRepository.findByTaxStatusAndTaxNameContaining(status,searchText);

        }

        List<ItemTaxDTO> taxDTOS = PosItemMapper.mapTaxEntityToPojo(list);
        return taxDTOS;
    }
    public BasePojo getPaginatedTaxList(String status,BasePojo basePojo,String searchText) {
        List<Tax> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"taxId"));
        if(basePojo.isLastPage()==true){
            List<Tax> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posTaxRepository.findAllByTaxNameContainingAndTaxStatus(searchText,status);
            }else {
                list1=posTaxRepository.findByTaxStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "taxId"));
        }
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        Tax tax=new Tax();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"taxId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            tax=posTaxRepository.findFirstByTaxNameContainingAndTaxStatus(searchText,status,sort);
            list = posTaxRepository.findAllByTaxNameContainingAndTaxStatus(searchText,status,pageable);
        } else {
            tax=posTaxRepository.findFirstByTaxStatus(status,sort);
            list = posTaxRepository.findAllByTaxStatus(status,pageable);
        }
        if(list.contains(tax)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<ItemTaxDTO> taxDTOList = PosItemMapper.mapTaxEntityToPojo(list);
        basePojo=calculatePagination(basePojo,taxDTOList.size());
        basePojo.setList(taxDTOList);
        return basePojo;
    }
    public ItemDTO getPageLoadData() {
        ItemDTO itemDTO = new ItemDTO();
        List<Tax> list = new ArrayList<>();
        list = posTaxRepository.findAll();
        List<TaxDTO> taxDTOS = PosItemMapper.mapTaxEntityToTaxDTO(list);
        itemDTO.setTaxList(taxDTOS);
        itemDTO.setItemUOMTypeDTOList(getUOMList("Active",""));
        return itemDTO;
    }

    public Tax saveTax(ItemTaxDTO itemTaxDTO) {
        Tax tax=new Tax();
        if(itemTaxDTO.getTaxId()!=null){
            tax=posTaxRepository.findByTaxNameAndTaxIdNotIn(itemTaxDTO.getTaxName(),itemTaxDTO.getTaxId());
        }else{
            tax=posTaxRepository.findByTaxName(itemTaxDTO.getTaxName());
        }
        if(tax==null){
            Tax taxes = PosItemMapper.mapTaxPojoToEntity(itemTaxDTO);
            TaxType taxTypes = posTaxTypeRepository.findByTaxTypeName(itemTaxDTO.getTaxTypeName());
            taxes.setTaxTypeName(taxTypes.getTaxTypeName());
            posTaxRepository.save(taxes);
            return taxes;
        }
        else{
            return null;
        }

    }

    public void deleteTax(String taxName,String groupName) {
        posTaxRepository.delete(posTaxRepository.findByTaxGroupAndTaxName(groupName,taxName));
    }

    public ItemTaxDTO editTax(String taxName,String groupName) {
        Tax tax = posTaxRepository.findByTaxGroupAndTaxName(groupName,taxName);
        List<Tax> taxes = new ArrayList<>();
        taxes.add(tax);
        ItemTaxDTO taxDTO = PosItemMapper.mapTaxEntityToPojo(taxes).get(0);
        TaxType taxTypes = posTaxTypeRepository.findByTaxTypeName(tax.getTaxTypeName());
        taxDTO.setTaxTypeName(taxTypes.getTaxTypeName());
//        taxDTO.setLinkedId(taxTypes.getTaxTypeId().toString());
        return taxDTO;
    }

    public List<TaxDTO> getTaxTypeList(String searchText) {
        List<TaxType> list = new ArrayList<>();
        if(StringUtils.isEmpty(searchText)){
            list = posTaxTypeRepository.findAll();
        }
        else{
            list = posTaxTypeRepository.findAllByTaxTypeNameContaining(searchText);
        }
        List<TaxDTO> taxDTOS = PosItemMapper.mapTaxTypeEntityToPojo(list);
        return taxDTOS;
    }
    public BasePojo getPaginatedTaxTypeList(BasePojo basePojo,String searchText) {
        List<TaxType> list = new ArrayList<>();
        basePojo.setPageSize(5);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"taxTypeId"));
        if(basePojo.isLastPage()==true){
            List<TaxType> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posTaxTypeRepository.findAllByTaxTypeNameContaining(searchText);
            }else {
                list1=posTaxTypeRepository.findAll();
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "taxTypeId"));
        }
        TaxType taxType=new TaxType();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),5,sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"taxTypeId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            taxType=posTaxTypeRepository.findFirstByTaxTypeNameContaining(searchText,sort);
            list = posTaxTypeRepository.findAllByTaxTypeNameContaining(searchText,pageable);
        } else {
            taxType=posTaxTypeRepository.findFirstBy(sort);
            list = posTaxTypeRepository.findAllBy(pageable);
        }
        if(list.contains(taxType)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<TaxDTO> taxTypeDtoList = PosItemMapper.mapTaxTypeEntityToPojo(list);
        basePojo=calculatePagination(basePojo,taxTypeDtoList.size());
        basePojo.setList(taxTypeDtoList);
        return basePojo;
    }
    public TaxType saveTaxType(TaxDTO taxDTO) {
        TaxType taxType =new TaxType();
        if(taxDTO.getTaxTypeId()!=null){
            taxType=posTaxTypeRepository.findByTaxTypeNameAndTaxTypeIdNotIn(taxDTO.getTaxName(),taxDTO.getTaxTypeId());

        }else{
            taxType=posTaxTypeRepository.findByTaxTypeName(taxDTO.getTaxName());
        }
        if(taxType==null){
            TaxType taxes = PosItemMapper.mapTaxTypePojoToEntity(taxDTO);
            posTaxTypeRepository.save(taxes);
            return taxes;
        }
        else{
            return null;
        }

    }

    public void deleteTaxType(String taxTypeName) {
        posTaxTypeRepository.delete(posTaxTypeRepository.findByTaxTypeName(taxTypeName));
    }

    public TaxDTO editTaxType(String taxTypeName) {
        TaxType taxType = posTaxTypeRepository.findByTaxTypeName(taxTypeName);
        List<TaxType> taxes = new ArrayList<>();
        taxes.add(taxType);
        TaxDTO taxDTO = PosItemMapper.mapTaxTypeEntityToPojo(taxes).get(0);
        return taxDTO;
    }

    public List<HsnDTO> getHsnList(String status,String searchText) {
        List<Msiccode> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            status="Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = posHsnRepository.findByStatus(status);
        } else {
            list = posHsnRepository.findByStatusAndCodeContaining(status,searchText);
        }
        List<HsnDTO> hsnCodes = PosItemMapper.mapHSNEntityToPojo(list);
        return hsnCodes;

    }
    public BasePojo getPaginatedHSNList(String status,BasePojo basePojo,String searchText) {
        List<Msiccode> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"mscid"));
        if(basePojo.isLastPage()==true){
            List<Msiccode> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posHsnRepository.findAllByCodeContainingAndStatus(searchText,status);
            }else {
                list1=posHsnRepository.findByStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "mscid"));
        }
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        Msiccode msiccode=new Msiccode();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"mscid"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            msiccode=posHsnRepository.findFirstByCodeContainingAndStatus(searchText,status,sort);
            list = posHsnRepository.findAllByCodeContainingAndStatus(searchText,status,pageable);
        } else {
            msiccode=posHsnRepository.findFirstByStatus(status,sort);
            list = posHsnRepository.findAllByStatus(status,pageable);
        }
        if(list.contains(msiccode)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<HsnDTO> hsnDTOList = PosItemMapper.mapHSNEntityToPojo(list);
        basePojo=calculatePagination(basePojo,hsnDTOList.size());
        basePojo.setList(hsnDTOList);
        return basePojo;
    }
    public Msiccode saveHsn(HsnDTO hsnDto) {
        Msiccode msiccode=new Msiccode();
        if(hsnDto.getMscid()!=null){
            msiccode=posHsnRepository.findByCodeAndMscidNotIn(hsnDto.getMsiccode(),hsnDto.getMscid());
        }else{
            msiccode=posHsnRepository.findByCode(hsnDto.getMsiccode());
        }
        if(msiccode==null){
            Msiccode msic = PosItemMapper.mapHSNPojoToEntity(hsnDto);
            posHsnRepository.save(msic);
            return msic;
        }
        else{
            return null;
        }

    }

    public void deleteHsn(String msiccode) {
        posHsnRepository.delete(posHsnRepository.findByCode(msiccode));
    }

    public HsnDTO editHsn(String msiccode) {
        Msiccode code = posHsnRepository.findByCode(msiccode);
        List<Msiccode> msic = new ArrayList<>();
        msic.add(code);
        HsnDTO hsnDto = PosItemMapper.mapHSNEntityToPojo(msic).get(0);
        return hsnDto;
    }

    public List<addNewItemDTO> getItemList(String status,String searchText) {
        List<Item> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
          status="Active";
        }
        if (StringUtils.isEmpty(searchText)) {
            list = posItemRepository.findByItemStatus(status);
        } else {
            list = posItemRepository.findByItemStatusAndItemNameContaining(status,searchText);
        }
        List<addNewItemDTO> itemDTOS = PosItemMapper.mapItemEntityToPojo(list);
        return itemDTOS;

    }
    public BasePojo getPaginatedItemList(String status,BasePojo basePojo,String searchText) {
        List<Item> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"itemId"));
        if(basePojo.isLastPage()==true){
            List<Item> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posItemRepository.findAllByItemCodeContainingAndItemStatus(searchText,status);
            }else {
                list1=posItemRepository.findByItemStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "itemId"));
        }
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        Item item=new Item();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"itemId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            item=posItemRepository.findFirstByItemCodeContainingAndItemStatus(searchText,status,sort);
            list = posItemRepository.findAllByItemCodeContainingAndItemStatus(searchText,status,pageable);
        } else {
            item=posItemRepository.findFirstByItemStatus(status,sort);
            list = posItemRepository.findAllByItemStatus(status,pageable);
        }
        if(list.contains(item)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<addNewItemDTO> itemDTOList = PosItemMapper.mapItemEntityToPojo(list);
        basePojo=calculatePagination(basePojo,itemDTOList.size());
        basePojo.setList(itemDTOList);
        return basePojo;
    }
    public Item saveItem(addNewItemDTO itemDTO) {
        Item iteminv=new Item();
        if(itemDTO.getItemId()!=null){
            iteminv=posItemRepository.findByItemNameAndItemIdNotIn(itemDTO.getItemName(),itemDTO.getItemId());

        }
        else{
            iteminv=posItemRepository.findByItemName(itemDTO.getItemName());
        }
        if(iteminv==null){
            Item item = posItemRepository.findByItemName(itemDTO.getItemName());
            if (item != null) {
                itemDTO.setItemId(item.getItemId());
            }
            Item items = PosItemMapper.mapItemPojoToEntity(itemDTO);
            if (StringUtils.isNotEmpty(itemDTO.getItemCatName())) {
                ItemCategory categories = posCategoryRepository.findByItemCategoryName(itemDTO.getItemCatName());
                items.setCategoryName(categories.getItemCategoryName());
            }
            if (StringUtils.isNotEmpty(itemDTO.getItemOPTaxName())) {
                Tax outputTax = posTaxRepository.findByTaxGroupAndTaxName("SALES/OUTPUT", itemDTO.getItemOPTaxName());
                items.setOpTaxName(outputTax.getTaxName());
            }
            if (StringUtils.isNotEmpty(itemDTO.getItemIPTaxName())) {
                Tax inputTax = posTaxRepository.findByTaxGroupAndTaxName("PURCHASE/INPUT", itemDTO.getItemIPTaxName());
                items.setIpTaxName(inputTax.getTaxName());
            }
            if (StringUtils.isNotEmpty(itemDTO.getItemUOMTyName())) {
                UnitOfMeasurement uom = posUomRepository.findByUnitOfMeasurementName(itemDTO.getItemUOMTyName());
                items.setUomName(uom.getUnitOfMeasurementName());
            }
            if (StringUtils.isNotEmpty(itemDTO.getItemMSICName())) {
                Msiccode msiCcode = posHsnRepository.findByCode(itemDTO.getItemMSICName());
                items.setMsicName(msiCcode.getCode());
            }
            if (StringUtils.isNotEmpty(itemDTO.getItemBrandName())) {
                ItemBrandName brand = brandRepository.findByBrandName(itemDTO.getItemBrandName());
                items.setBrandName(brand.getBrandName());
            }
            posItemRepository.save(items);
            return items;
        }else{
            return null;
        }

    }

    public void deleteItem(String itemName) {
        posItemRepository.delete(posItemRepository.findByItemName(itemName));
    }

    public addNewItemDTO editItem(String itemName) {
        Item item = posItemRepository.findByItemName(itemName);
        if (item != null) {
            List<Item> items = new ArrayList<>();
            items.add(item);
            addNewItemDTO itemDTO = PosItemMapper.mapItemEntityToPojo(items).get(0);
            return itemDTO;
        }
        return null;
    }

    public List<addNewItemDTO> getItem(String itemName) {
        List<addNewItemDTO> list = new ArrayList<>();
        Item item = posItemRepository.findByItemName(itemName);
        List<Item> items = new ArrayList<>();
        items.add(item);
        list = PosItemMapper.mapItemEntityToPojo(items);
        return list;
    }

    public ItemDTO getAllItemsLists() {
        ItemDTO addNewItemDTO = new ItemDTO();
        List<ItemCategory> categoryList = new ArrayList<>();
        categoryList = posCategoryRepository.findAll();
        List<ItemCategoryDTO> category = PosItemMapper.mapEntityToPojo(categoryList);
        addNewItemDTO.setItemCategoryDTOList(category);
        List<Tax> outputTaxList = new ArrayList<>();
        outputTaxList = posTaxRepository.findByTaxGroup("SALES/OUTPUT");
        List<ItemOPTaxDTO> outputTax = PosItemMapper.mapOPTaxEntityToPojo(outputTaxList);
        addNewItemDTO.setItemOPTaxDTOList(outputTax);
        List<Tax> inputTaxList = new ArrayList<>();
        inputTaxList = posTaxRepository.findByTaxGroup("PURCHASE/INPUT");
        List<ItemIPTaxDTO> inputTax = PosItemMapper.mapIPTaxEntityToPojo(inputTaxList);
        addNewItemDTO.setItemIPTaxDTOList(inputTax);
        List<UnitOfMeasurement> uom = new ArrayList<>();
        uom = posUomRepository.findAll();
        List<ItemUOMTypeDTO> uomDto = PosItemMapper.mapUomEntityToPojo(uom);
        addNewItemDTO.setItemUOMTypeDTOList(uomDto);
        List<ItemBrandName> brandNameList = new ArrayList<>();
        brandNameList = brandRepository.findAll();
        List<ItemBrandDTO> brand = PosItemMapper.mapBrandEntityToPojo(brandNameList);
        addNewItemDTO.setItemBrandDTOList(brand);
        return addNewItemDTO;
    }

    public List<BankDTO> getBankList(String status) {
        List<Bank> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            list = posBankRepository.findAll();
        } else {
            list = posBankRepository.findAllByStatus(status);
        }
        List<BankDTO> bankDTOList = PosItemMapper.mapBankEntityToPojo(list);
        return bankDTOList;
    }

    public void deleteBank(String bankName) {
        posBankRepository.delete(posBankRepository.findAllByBankName(bankName));
    }

    public BankDTO editBank(String bankName) {
        Bank bank = posBankRepository.findAllByBankName(bankName);
        List<Bank> bankList = new ArrayList<>();
        bankList.add(bank);
        BankDTO bankDTO = PosItemMapper.mapBankEntityToPojo(bankList).get(0);
        return bankDTO;
    }

    public Bank saveBank(BankDTO bankDTO) {
        Bank banks=new Bank();
        if(bankDTO.getBankId()!=null){
            banks=posBankRepository.findByBankNameAndBankIdNotIn(bankDTO.getBankName(),bankDTO.getBankId());
        }else{
            banks=posBankRepository.findAllByBankName(bankDTO.getBankName());
        }
        if(banks==null){
            Bank bank = PosItemMapper.mapBankPojoToEntity(bankDTO);
            posBankRepository.save(bank);
            return bank;
        }else{
            return null;
        }

    }

    public static String getNextRefInvoice(String prefix, String nextRef) {
        StringBuilder sb = new StringBuilder();
        return sb.append(prefix).append(nextRef).toString();
    }

    public Agent saveAgent(AgentDTO agentDTO) {
        Agent agents=new Agent();
        if(agentDTO.getAgentId()!=null){
            agents=posAgentRepository.findAllByAgentNameAndAgentIdNotIn(agentDTO.getAgentName(),agentDTO.getAgentId());
        }else{
            agents=posAgentRepository.findAllByAgentName(agentDTO.getAgentName());
        }
        if(agents==null){
            Agent agent = PosItemMapper.mapAgentPojoToEntity(agentDTO);
            posAgentRepository.save(agent);
            return agent;
        }else{
            return null;
        }

    }

    public List<AgentDTO> getAgentList(String status) {
        List<Agent> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            list = posAgentRepository.findAll();
        } else {
            list = posAgentRepository.findAllByStatus(status);
        }
        List<AgentDTO> agentDTOList = PosItemMapper.mapAgentEntityToPojo(list);
        return agentDTOList;
    }

    public void deleteAgent(String agentName) {
        posAgentRepository.delete(posAgentRepository.findAllByAgentName(agentName));
    }

    public AgentDTO editAgent(String agentName) {
        Agent agent = posAgentRepository.findAllByAgentName(agentName);
        List<Agent> agentList = new ArrayList<>();
        agentList.add(agent);
        AgentDTO agentDTO = PosItemMapper.mapAgentEntityToPojo(agentList).get(0);
        return agentDTO;
    }

    public PaymentMethodDTO editPaymentMethod(Long agentId) {
        PaymentMethod paymentMethod = posPaymentMethodRepository.findOne(agentId);
        List<PaymentMethod> agentList = new ArrayList<>();
        agentList.add(paymentMethod);
        PaymentMethodDTO agentDTO = PosItemMapper.mapPaymentMethodEntityToPojo(agentList).get(0);
        return agentDTO;
    }

    public PaymentMethod savePaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethods =new PaymentMethod();
        if(paymentMethodDTO.getPaymentmethodId()!=null){
            paymentMethods=posPaymentMethodRepository.findByPaymentmethodNameAndPaymentmethodIdNotIn(paymentMethodDTO.getPaymentmethodName(),paymentMethodDTO.getPaymentmethodId());
        }else{
            paymentMethods=posPaymentMethodRepository.findByPaymentmethodName(paymentMethodDTO.getPaymentmethodName());
        }
        if(paymentMethods==null){
            PaymentMethod paymentMethod = PosItemMapper.mapPaymentMethodPojoToEntity(paymentMethodDTO);
            posPaymentMethodRepository.save(paymentMethod);
            return paymentMethod;
        }else{
            return null;
        }

    }

    public List<PaymentMethodDTO> getPaymentMethodList(String status) {
        List<PaymentMethod> list = new ArrayList<>();
        if (StringUtils.isEmpty(status)) {
            list = posPaymentMethodRepository.findAll();
        } else {
            list = posPaymentMethodRepository.findAllByStatus(status);
        }
        List<PaymentMethodDTO> paymentMethodDTOList = PosItemMapper.mapPaymentMethodEntityToPojo(list);
        return paymentMethodDTOList;
    }
    public BasePojo getPaginatedPayMethodList(String status,BasePojo basePojo,String searchText) {
        List<PaymentMethod> list = new ArrayList<>();
        basePojo.setPageSize(paginatedConstants);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.DESC,"paymentmethodId"));
        if(basePojo.isLastPage()==true){
            List<PaymentMethod> list1=new ArrayList<>();
            if (!StringUtils.isEmpty(searchText)) {
                list1 = posPaymentMethodRepository.findAllByPaymentmethodNameContainingAndStatus(searchText,status);
            }else {
                list1=posPaymentMethodRepository.findAllByStatus(status);
            }
            int size=list1.size()%paginatedConstants;
            if(size!=0){
                basePojo.setPageSize(size);
            }
            sort = new Sort(new Sort.Order(Sort.Direction.ASC, "paymentmethodId"));
        }
        if(StringUtils.isEmpty(status)){
            status="Active";
        }
        PaymentMethod paymentMethod=new PaymentMethod();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),basePojo.getPageSize(),sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.ASC,"paymentmethodId"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            paymentMethod=posPaymentMethodRepository.findFirstByPaymentmethodNameContainingAndStatus(searchText,status,sort);
            list = posPaymentMethodRepository.findAllByPaymentmethodNameContainingAndStatus(searchText,status,pageable);
        } else {
            paymentMethod=posPaymentMethodRepository.findFirstByStatus(status,sort);
            list = posPaymentMethodRepository.findAllByStatus(status,pageable);
        }
        if(list.contains(paymentMethod)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<PaymentMethodDTO> payMethodDTOList = PosItemMapper.mapPaymentMethodEntityToPojo(list);
        basePojo=calculatePagination(basePojo,payMethodDTOList.size());
        basePojo.setList(payMethodDTOList);
        return basePojo;
    }
    public void deletePaymentMethod(Long paymentmethodId) {
        posPaymentMethodRepository.delete(paymentmethodId);
    }

    public FormSetUp saveFormSetup(FormsetupDTO formsetupDTO) {
        FormSetUp formSetUps =new FormSetUp();
        if(formsetupDTO.getFormsetupId()!=null){
            formSetUps=posFormSetupRepository.findAllByTypenameAndFormsetupIdNotIn(formsetupDTO.getTypename(),formsetupDTO.getFormsetupId());

        }else{
            formSetUps=posFormSetupRepository.findAllByTypename(formsetupDTO.getTypename());
        }
        if(formSetUps==null){
            FormSetUp formSetUp = PosItemMapper.mapFormSetupPojoToEntity(formsetupDTO);
            posFormSetupRepository.save(formSetUp);
            return formSetUp;
        }else{
            return null;
        }

    }

    public FormsetupDTO editFormsetupMethod(String formsetupName) {
        FormSetUp formSetUp = posFormSetupRepository.findAllByTypename(formsetupName);
        List<FormSetUp> formSetUpList = new ArrayList<>();
        formSetUpList.add(formSetUp);
        FormsetupDTO formsetupDTO = PosItemMapper.mapFormSetupEntityToPojo(formSetUpList).get(0);
        return formsetupDTO;
    }

    public FormsetupDTO getFormSetup(String type) {
        FormSetUp formSetUp = posFormSetupRepository.findAllByTypename(type);
        if (formSetUp != null) {
            List<FormSetUp> formSetUpList = new ArrayList<>();
            formSetUpList.add(formSetUp);
            FormsetupDTO formsetupDTO = PosItemMapper.mapFormSetupEntityToPojo(formSetUpList).get(0);
            int incValue = Integer.parseInt(formSetUp.getNextref());
            formsetupDTO.setFormNo(getNextRefInvoice(formSetUp.getTypeprefix(), String.format("%08d", incValue)));
            return formsetupDTO;
        }
        return null;
    }

    public List<FormsetupDTO> getFormSetupList() {
        List<FormSetUp> list = new ArrayList<>();
        list = posFormSetupRepository.findAll();
        List<FormsetupDTO> formsetupDTOS = PosItemMapper.mapFormSetupEntityToPojo(list);
        return formsetupDTOS;
    }
    public BasePojo getPaginatedFormSetUpList(BasePojo basePojo,String searchText) {
        List<FormSetUp> list = new ArrayList<>();
        basePojo.setPageSize(5);
        Sort sort=new Sort(new Sort.Order(Sort.Direction.ASC,"typename"));
        if(basePojo.isLastPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"typename"));
        }
        FormSetUp formSetUp=new FormSetUp();
        Pageable pageable=new PageRequest(basePojo.getPageNo(),5,sort);
        if(basePojo.isNextPage()==true || basePojo.isFirstPage()==true){
            sort=new Sort(new Sort.Order(Sort.Direction.DESC,"typename"));
        }
        if (!StringUtils.isEmpty(searchText)) {
            formSetUp=posFormSetupRepository.findFirstByTypenameContaining(searchText,sort);
            list = posFormSetupRepository.findAllByTypenameContaining(searchText,pageable);
        } else {
            formSetUp=posFormSetupRepository.findFirstBy(sort);
            list = posFormSetupRepository.findAllBy(pageable);
        }
        if(list.contains(formSetUp)){
            basePojo.setStatus(true);
        }else {
            basePojo.setStatus(false);
        }
        List<FormsetupDTO> formsetupDTOList = PosItemMapper.mapFormSetupEntityToPojo(list);
        basePojo=calculatePagination(basePojo,formsetupDTOList.size());
        basePojo.setList(formsetupDTOList);
        return basePojo;
    }

    public List<SchedulerData> getSchedulerList() {
        return posSchedulerRepository.findAll();
    }

    public void saveMailSchedule(MailSchedulerData mailSchedulerData) throws Exception {
        Mail mailServerPops = posMailRepository.findOne(1L);
        mailSchedulerData.setFromMail(mailServerPops);
        String filename = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (StringUtils.isNotEmpty(mailSchedulerData.getReportName()))
            switch (mailSchedulerData.getReportName()) {
                case "posSales":
                    if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(mailSchedulerData.getReportType(), "application/pdf")) {
                        downloadSalesReportPdf(outputStream);
                        filename = "PosSalesReport.pdf";
                    } else {
                        downloadSalesReportExcel(outputStream);
                        filename = "PosSalesReport.xls";
                    }
                    break;
                case "posPurchase":
                    if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(mailSchedulerData.getReportType(), "application/pdf")) {
                        downloadPurchaseReportPdf(outputStream);
                        filename = "PosPurchaseReport.pdf";
                    } else {
                        downloadPurchaseReportExcel(outputStream);
                        filename = "PosPurchaseReport.xls";
                    }
                    break;
            }
        if (StringUtils.isEmpty(mailSchedulerData.getBody())) {
            mailSchedulerData.setBody("");
        }
        MailService.sendMailWithAttachment(mailSchedulerData.getFromMail(),
                mailSchedulerData.getToEmail(), "", mailSchedulerData.getSubject(),
                mailSchedulerData.getBody(), mailSchedulerData.getReportType(),
                outputStream.toByteArray(), filename);
    }


    public void deleteMailSchedulerDetails(String schedulerid) {
        posSchedulerRepository.delete(Long.parseLong(schedulerid));
    }

//
//    public List<ItemCategoryDTO> getcategory1() {
//        List<ItemCategory> itemCategories = new ArrayList<>();
//        itemCategories = (List<ItemCategory>) posCategoryRepository.findAll();
//        List<ItemCategoryDTO> categoryDTOList = ObjectMapperUtils.mapAll(itemCategories, ItemCategoryDTO.class);
//        return categoryDTOList;
//    }


    @Transactional
    public void downloadSalesReportPdf(OutputStream outputStream) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstSalesReport();
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstSalesReport() {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable tab = new PdfPTable(1);
        Font f = new Font(getcustomfont(), 15, Font.BOLD, Color.WHITE);
        PdfPTable table = new PdfPTable(a + 5);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("PosSales Report", f));
        p.setBackgroundColor(myColor);
        tab.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Inv Date", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Invoice No", font));
        pc3.setBackgroundColor(myColor);
        PdfPCell pc4 = new PdfPCell(new Phrase("Customer Name", font));
        pc4.setBackgroundColor(myColor);
        PdfPCell pc5 = new PdfPCell(new Phrase("Invoice Amount", font));
        pc5.setBackgroundColor(myColor);
        PdfPCell pc6 = new PdfPCell(new Phrase("Total Paid", font));
        pc6.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        table.addCell(pc5);
        table.addCell(pc6);
        List<SalesInvoiceDTO> salesList = salesService.getSalesList();
        for (SalesInvoiceDTO list : salesList) {
            table.addCell(new Phrase(list.getDate() + "", font1));
            table.addCell(new Phrase(list.getSINo() + "", font1));
            table.addCell(new Phrase(list.getCustomerName() + "", font1));
            table.addCell(new Phrase(list.getTotalAmount() + "", font1));
            table.addCell(new Phrase(list.getTotalReceivable() + "", font1));
        }
        tab.addCell(table);
        return tab;
    }

    @Transactional
    public void downloadSalesReportExcel(OutputStream out) {
        try {
            List<SalesInvoiceDTO> salesList = salesService.getSalesList();
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            headerRow.createCell(0).setCellValue("PosSales Report ");
            HSSFRow headerRow1 = sheet.createRow(1);
            headerRow1.createCell(0).setCellValue("Inv Date");
            headerRow1.createCell(1).setCellValue("Invoice No");
            headerRow1.createCell(2).setCellValue("Customer Name");
            headerRow1.createCell(3).setCellValue("Invoice Amount");
            headerRow1.createCell(4).setCellValue("Total Paid");
            int i = 0;
            for (SalesInvoiceDTO list : salesList) {
                HSSFRow row = sheet.createRow(++i);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                row.createCell(0).setCellValue(dateFormat.format(list.getDate()));
                row.createCell(1).setCellValue(list.getSINo());
                row.createCell(2).setCellValue(list.getCustomerName());
                row.createCell(3).setCellValue(list.getTotalAmount());
                row.createCell(4).setCellValue(list.getTotalReceivable());
                }
            hwb.write(out);
            out.close();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }

    @Transactional
    public void downloadPurchaseReportPdf(OutputStream outputStream) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new com.lowagie.text.pdf.draw.LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstPurchaseReport();
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public PdfPTable createFirstPurchaseReport() {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable tab = new PdfPTable(1);
        Font f = new Font(getcustomfont(), 15, Font.BOLD, Color.WHITE);
        PdfPTable table = new PdfPTable(a + 5);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("PosPurchase Report", f));
        p.setBackgroundColor(myColor);
        tab.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Date", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Invoice No", font));
        pc3.setBackgroundColor(myColor);
        PdfPCell pc4 = new PdfPCell(new Phrase("Supplier name", font));
        pc4.setBackgroundColor(myColor);
        PdfPCell pc5 = new PdfPCell(new Phrase("Invoice Amount", font));
        pc5.setBackgroundColor(myColor);
        PdfPCell pc6 = new PdfPCell(new Phrase("Amount Paid", font));
        pc6.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        table.addCell(pc5);
        table.addCell(pc6);
        List<PurchaseDTO> purList = purchaseService.getPurchaseList();
        for (PurchaseDTO list : purList) {
            table.addCell(new Phrase(list.getDate() + "", font1));
            table.addCell(new Phrase(list.getPiNo() + "", font1));
            table.addCell(new Phrase(list.getSupplierName() + "", font1));
            table.addCell(new Phrase(list.getTotalAmt() + "", font1));
            table.addCell(new Phrase(list.getTotalPayable() + "", font1));
        }
        tab.addCell(table);
        return tab;
    }

    @Transactional
    public void downloadPurchaseReportExcel(OutputStream out) {
        try {
            List<PurchaseDTO> purList = purchaseService.getPurchaseList();
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            headerRow.createCell(0).setCellValue("PosPurchase Report ");
            HSSFRow headerRow1 = sheet.createRow(1);
            headerRow1.createCell(0).setCellValue("Date");
            headerRow1.createCell(1).setCellValue("Invoice No");
            headerRow1.createCell(2).setCellValue("Supplier Name");
            headerRow1.createCell(3).setCellValue("Invoice Amount");
            headerRow1.createCell(4).setCellValue("Amount Paid");
            int i = 0;
            for (PurchaseDTO list : purList) {
                HSSFRow row = sheet.createRow(++i);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                row.createCell(0).setCellValue(dateFormat.format(list.getDate()));
                row.createCell(1).setCellValue(list.getPiNo());
                row.createCell(2).setCellValue(list.getSupplierName());
                row.createCell(3).setCellValue(list.getTotalAmt());
                row.createCell(4).setCellValue(list.getTotalPayable());
            }
            hwb.write(out);
            out.close();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }

    public static BaseFont getcustomfont() {
        String relativeWebPath = "fonts/arial.ttf";
        return FontFactory.getFont("arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, Color.BLACK).getBaseFont();
    }
    public void downloadItemExcelSheet(OutputStream out, String statustype,String searchText) {
        try {
            List<addNewItemDTO> itemList = getItemList(statustype,searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Item Code");
            headerRow1.createCell(1).setCellValue("Item Name");
            headerRow1.createCell(2).setCellValue("status");
            headerRow1.createCell(3).setCellValue("Available Qty");
            headerRow1.createCell(4).setCellValue("Item Category Name");
            int i = 0;
            for (addNewItemDTO item : itemList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(item.getItemCode());
                row.createCell(1).setCellValue(item.getItemName());
                row.createCell(2).setCellValue(item.getItemStatus());
                row.createCell(3).setCellValue(item.getStock());
                row.createCell(4).setCellValue(item.getItemCatName());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadItemPdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableItem(statustype,searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableItem(String searchText,String statusType) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 5);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Item", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Item Code", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Item Name", font));
        pc3.setBackgroundColor(myColor);
        PdfPCell pc4 = new PdfPCell(new Phrase("status", font));
        pc4.setBackgroundColor(myColor);
        PdfPCell pc5 = new PdfPCell(new Phrase("Available Qty", font));
        pc5.setBackgroundColor(myColor);
        PdfPCell pc6 = new PdfPCell(new Phrase("Item Category Name", font));
        pc6.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        table.addCell(pc5);
        table.addCell(pc6);
        List<addNewItemDTO> itemList = getItemList(searchText, statusType);
        table.setWidthPercentage(100);
        for (addNewItemDTO item : itemList) {
            table.addCell(new Phrase(item.getItemCode() + "", font1));
            table.addCell(new Phrase(item.getItemName() + "", font1));
            table.addCell(new Phrase(item.getItemStatus() + "", font1));
            table.addCell(new Phrase(item.getStock() + "", font1));
            table.addCell(new Phrase(item.getItemCatName() + "", font1));

        }
        table1.addCell(table);
        return table1;
    }
    public void downloadItemCategoryExcelSheet(OutputStream out, String statusType,String searchText) {
        try {
            List<ItemCategoryDTO> itemCatList = getItemCategoryList(statusType,searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
//            headerRow1.createCell(0).setCellValue("Item Category Code");
            headerRow1.createCell(0).setCellValue("Item Category Name");
            headerRow1.createCell(1).setCellValue("Item Category Description");
            int i = 0;
            for (ItemCategoryDTO itemCat : itemCatList) {
                HSSFRow row = sheet.createRow(++i);
//                row.createCell(0).setCellValue(itemCat.getItemCategoryCode());
                row.createCell(0).setCellValue(itemCat.getItemCategoryName());
                row.createCell(1).setCellValue(itemCat.getItemCategoryDesc());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadItemCatPdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableItemCat(statustype,searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableItemCat(String statusType,String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 3);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Item", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
//        PdfPCell pc2 = new PdfPCell(new Phrase("Item Category Code", font));
//        pc2.setBackgroundColor(myColor);
        PdfPCell pc2 = new PdfPCell(new Phrase("Item Category Name", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Item Category Description", font));
        pc3.setBackgroundColor(myColor);
        table.addCell(p);
        table.addCell(pc2);
        table.addCell(pc3);
        List<ItemCategoryDTO> itemCatList = getItemCategoryList(statusType, searchText);
        table.setWidthPercentage(100);
        for (ItemCategoryDTO itemCat : itemCatList) {
//            table.addCell(new Phrase(itemCat.getItemCategoryCode() + "", font1));
            table.addCell(new Phrase(itemCat.getItemCategoryName() + "", font1));
            table.addCell(new Phrase(itemCat.getItemCategoryDesc() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadStateExcelSheet(OutputStream out,  String statustype,String searchText) {
        try {
            List<StateDTO> stateList = getStateList(statustype,searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Country Name");
            headerRow1.createCell(1).setCellValue("State Name");
            headerRow1.createCell(2).setCellValue("Status");
            int i = 0;
            for (StateDTO state : stateList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(state.getCountry());
                row.createCell(1).setCellValue(state.getStateName());
                row.createCell(2).setCellValue(state.getStatus());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadTaxPdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableTax(statustype,searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableTax(String statusType,String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 5);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Tax", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Tax Code", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Tax Name", font));
        pc3.setBackgroundColor(myColor);
        PdfPCell pc4 = new PdfPCell(new Phrase("TaxType Name", font));
        pc4.setBackgroundColor(myColor);
        PdfPCell pc5 = new PdfPCell(new Phrase("Tax Description", font));
        pc5.setBackgroundColor(myColor);
        PdfPCell pc6 = new PdfPCell(new Phrase("Tax Status", font));
        pc6.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        table.addCell(pc5);
        table.addCell(pc6);
        List<ItemTaxDTO> taxDTOList = getTaxList(statusType, searchText);
        table.setWidthPercentage(100);
        for (ItemTaxDTO tax : taxDTOList) {
            table.addCell(new Phrase(tax.getTaxCode() + "", font1));
            table.addCell(new Phrase(tax.getTaxName() + "", font1));
            table.addCell(new Phrase(tax.getTaxTypeName() + "", font1));
            table.addCell(new Phrase(tax.getTaxDescription() + "", font1));
            table.addCell(new Phrase(tax.getTaxStatus() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadTaxExcelSheet(OutputStream out,  String statustype,String searchText) {
        try {
            List<ItemTaxDTO> taxDTOList = getTaxList(statustype, searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Tax Code");
            headerRow1.createCell(1).setCellValue("Tax Name");
            headerRow1.createCell(2).setCellValue("TaxType Name");
            headerRow1.createCell(3).setCellValue("Tax Description");
            headerRow1.createCell(4).setCellValue("Tax Status");
            int i = 0;
            for (ItemTaxDTO tax : taxDTOList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(tax.getTaxCode());
                row.createCell(1).setCellValue(tax.getTaxName());
                row.createCell(2).setCellValue(tax.getTaxTypeName());
                row.createCell(3).setCellValue(tax.getTaxDescription());
                row.createCell(4).setCellValue(tax.getTaxStatus());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadTaxTypePdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableTaxType(searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableTaxType(String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 2);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("TaxType", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("TaxType", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Description", font));
        pc3.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        List<TaxDTO> taxDTOList = getTaxTypeList(searchText);
        table.setWidthPercentage(100);
        for (TaxDTO tax : taxDTOList) {
            table.addCell(new Phrase(tax.getTaxName() + "", font1));
            table.addCell(new Phrase(tax.getTaxDescription() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadTaxTypeExcelSheet(OutputStream out,String searchText) {
        try {
            List<TaxDTO> taxDTOList = getTaxTypeList(searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("TaxType");
            headerRow1.createCell(1).setCellValue("Description");
            int i = 0;
            for (TaxDTO tax : taxDTOList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(tax.getTaxName());
                row.createCell(1).setCellValue(tax.getTaxDescription());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadEmailPdf(OutputStream outputStream) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableEmail();
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableEmail() {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 3);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Email", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("EmailId", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("SMTPServer", font));
        pc3.setBackgroundColor(myColor);
        PdfPCell pc4 = new PdfPCell(new Phrase("SMTPPort", font));
        pc4.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        List<MailDTO> mailDTOList = getMailList();
        table.setWidthPercentage(100);
        for (MailDTO mail : mailDTOList) {
            table.addCell(new Phrase(mail.getUserName() + "", font1));
            table.addCell(new Phrase(mail.getSmtpHost() + "", font1));
            table.addCell(new Phrase(mail.getPort() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadEmailExcelSheet(OutputStream out) {
        try {
            List<MailDTO> mailDTOList = getMailList();
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("EmailId");
            headerRow1.createCell(1).setCellValue("SMTPServer");
            headerRow1.createCell(2).setCellValue("SMTPPort");
            int i = 0;
            for (MailDTO mail : mailDTOList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(mail.getUserName());
                row.createCell(1).setCellValue(mail.getSmtpHost());
                row.createCell(2).setCellValue(mail.getPort());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadStatePdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableState(statustype,searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableState(String statusType,String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 3);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("State", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Country Name", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("State Name", font));
        pc3.setBackgroundColor(myColor);
        PdfPCell pc4 = new PdfPCell(new Phrase("Status", font));
        pc4.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        table.addCell(pc4);
        List<StateDTO> stateList = getStateList(statusType,searchText);
        table.setWidthPercentage(100);
        for (StateDTO stateDTO : stateList) {
            table.addCell(new Phrase(stateDTO.getCountry() + "", font1));
            table.addCell(new Phrase(stateDTO.getStateName() + "", font1));
            table.addCell(new Phrase(stateDTO.getStatus() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadBrandExcelSheet(OutputStream out,  String statustype,String searchText) {
        try {
            List<ItemBrandDTO> brandList = getBrandList(statustype,searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Brand Name");
            headerRow1.createCell(1).setCellValue("Brand Description");
            int i = 0;
            for (ItemBrandDTO brand : brandList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(brand.getBrandName());
                row.createCell(1).setCellValue(brand.getBrandDescription());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadBrandPdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableBrand(statustype,searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableBrand(String statusType,String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 2);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Brand", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Brand Name", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Brand Description", font));
        pc3.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        List<ItemBrandDTO> brandList = getBrandList(statusType,searchText);
        table.setWidthPercentage(100);
        for (ItemBrandDTO brandDTO : brandList) {
            table.addCell(new Phrase(brandDTO.getBrandName() + "", font1));
            table.addCell(new Phrase(brandDTO.getBrandDescription() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadUOMExcelSheet(OutputStream out,  String statustype,String searchText) {
        try {
            List<ItemUOMTypeDTO> uomList = getUOMList(statustype,searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("UOM Name");
            headerRow1.createCell(1).setCellValue("Description");
            int i = 0;
            for (ItemUOMTypeDTO uom : uomList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(uom.getUnitOfMeasurementName());
                row.createCell(1).setCellValue(uom.getUnitOfMeasurementDescription());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadUOMPdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableUOM(statustype,searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableUOM(String statusType,String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 2);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("UOM", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("UOM Name", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("Description", font));
        pc3.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        List<ItemUOMTypeDTO> uomList = getUOMList(statusType,searchText);
        table.setWidthPercentage(100);
        for (ItemUOMTypeDTO uomTypeDTO : uomList) {
            table.addCell(new Phrase(uomTypeDTO.getUnitOfMeasurementName() + "", font1));
            table.addCell(new Phrase(uomTypeDTO.getUnitOfMeasurementDescription() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadHSNExcelSheet(OutputStream out,  String statustype,String searchText) {
        try {
            List<HsnDTO> hsnList = getHsnList(statustype,searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("HSN/SAC Code");
            headerRow1.createCell(1).setCellValue("HSN/SAC Description");
            int i = 0;
            for (HsnDTO hsn : hsnList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(hsn.getMsiccode());
                row.createCell(1).setCellValue(hsn.getDescrip());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadHSNPdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableHSN(statustype,searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableHSN(String statusType,String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 2);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("HSN/SAC", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("HSN/SAC Code", font));
        pc2.setBackgroundColor(myColor);
        PdfPCell pc3 = new PdfPCell(new Phrase("HSN/SAC Description", font));
        pc3.setBackgroundColor(myColor);
        table.addCell(pc2);
        table.addCell(pc3);
        List<HsnDTO> hsnList = getHsnList(statusType,searchText);
        table.setWidthPercentage(100);
        for (HsnDTO hsnDTO : hsnList) {
            table.addCell(new Phrase(hsnDTO.getMsiccode() + "", font1));
            table.addCell(new Phrase(hsnDTO.getDescrip() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadCountryExcelSheet(OutputStream out,  String statustype,String searchText) {
        try {
            List<CountryDTO> countryList = getCountryList(statustype,searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Country Name");
            int i = 0;
            for (CountryDTO country : countryList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(country.getCountryName());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadCountryPdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableCountry(statustype,searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableCountry(String statusType,String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 1);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Country", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Country Name", font));
        pc2.setBackgroundColor(myColor);
        table.addCell(pc2);
        List<CountryDTO> countryList = getCountryList(statusType,searchText);
        table.setWidthPercentage(100);
        for (CountryDTO countryDTO : countryList) {
            table.addCell(new Phrase(countryDTO.getCountryName() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadCurrencyExcelSheet(OutputStream out,  String statustype,String searchText) {
        try {
            List<CurrencyDTO> currencyList = getCurrencyList(statustype,searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Currency Code");
            headerRow1.createCell(1).setCellValue("Currency word");
            headerRow1.createCell(2).setCellValue("Currency symbol");
            int i = 0;
            for (CurrencyDTO currency : currencyList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(currency.getCurrencyCode());
                row.createCell(1).setCellValue(currency.getCurrencyName());
                row.createCell(2).setCellValue(currency.getCurrencySymbol());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadCurrencyPdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableCurrency(statustype,searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableCurrency(String statusType,String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 3);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Currency", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Currency code", font));
        PdfPCell pc3 = new PdfPCell(new Phrase("Currency word", font));
        PdfPCell pc4 = new PdfPCell(new Phrase("Currency symbol", font));
        pc2.setBackgroundColor(myColor);
        table.addCell(pc2);
        pc3.setBackgroundColor(myColor);
        table.addCell(pc3);
        pc4.setBackgroundColor(myColor);
        table.addCell(pc4);
        List<CurrencyDTO> currencyDTOList = getCurrencyList(statusType,searchText);
        table.setWidthPercentage(100);
        for (CurrencyDTO currencyDTO : currencyDTOList) {
            table.addCell(new Phrase(currencyDTO.getCurrencyCode() + "", font1));
            table.addCell(new Phrase(currencyDTO.getCurrencyName() + "", font1));
            table.addCell(new Phrase(currencyDTO.getCurrencySymbol() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadSupplierExcelSheet(OutputStream out,  String statustype,String searchText) {
        try {
            List<SupplierDTO> supplierList = getSupplierList(statustype,searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Supplier Name");
            headerRow1.createCell(1).setCellValue("GSTN No");
            headerRow1.createCell(2).setCellValue("State Name");
            headerRow1.createCell(3).setCellValue("Email");
            headerRow1.createCell(4).setCellValue("Phone no");
            int i = 0;
            for (SupplierDTO supplier : supplierList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(supplier.getSupplierName());
                row.createCell(1).setCellValue(supplier.getGstIn());
                row.createCell(2).setCellValue(supplier.getStateName());
                row.createCell(3).setCellValue(supplier.getSupplierEmail());
                row.createCell(4).setCellValue(supplier.getSupplierNumber());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadSupplierPdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableSupplier(statustype,searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableSupplier(String statusType,String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 5);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Supplier", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Supplier Name", font));
        PdfPCell pc3 = new PdfPCell(new Phrase("GSTN No", font));
        PdfPCell pc4 = new PdfPCell(new Phrase("State Name", font));
        PdfPCell pc5 = new PdfPCell(new Phrase("Email", font));
        PdfPCell pc6 = new PdfPCell(new Phrase("Phone no", font));
        pc2.setBackgroundColor(myColor);
        table.addCell(pc2);
        pc3.setBackgroundColor(myColor);
        table.addCell(pc3);
        pc4.setBackgroundColor(myColor);
        table.addCell(pc4);
        pc5.setBackgroundColor(myColor);
        table.addCell(pc5);
        pc6.setBackgroundColor(myColor);
        table.addCell(pc6);
        List<SupplierDTO> supplierDTOList = getSupplierList(statusType,searchText);
        table.setWidthPercentage(100);
        for (SupplierDTO supplierDTO : supplierDTOList) {
            table.addCell(new Phrase(supplierDTO.getSupplierName() + "", font1));
            table.addCell(new Phrase(supplierDTO.getGstIn() + "", font1));
            table.addCell(new Phrase(supplierDTO.getStateName() + "", font1));
            table.addCell(new Phrase(supplierDTO.getSupplierEmail() + "", font1));
            table.addCell(new Phrase(supplierDTO.getSupplierNumber() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }
    public void downloadCustomerExcelSheet(OutputStream out,  String statustype,String searchText) {
        try {
            List<CustomerDTO> customerList = getCustomerList(statustype,searchText);
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("First Sheet");
            HSSFRow headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
            HSSFRow headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Customer Name");
            headerRow1.createCell(1).setCellValue("GSTN No");
            headerRow1.createCell(2).setCellValue("State Name");
            headerRow1.createCell(3).setCellValue("Email");
            headerRow1.createCell(4).setCellValue("Phone no");
            int i = 0;
            for (CustomerDTO customer : customerList) {
                HSSFRow row = sheet.createRow(++i);
                row.createCell(0).setCellValue(customer.getCustomerName());
                row.createCell(1).setCellValue(customer.getGstIn());
                row.createCell(2).setCellValue(customer.getStateName());
                row.createCell(3).setCellValue(customer.getCustomerEmail());
                row.createCell(4).setCellValue(customer.getCustomerNumber());
            }

            hwb.write(out);
            out.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception gex) {
            gex.printStackTrace();
        }
    }
    @Transactional
    public void downloadCustomerPdf(OutputStream outputStream,String statustype,String searchText) {
        try {
            Font font1 = new Font(getcustomfont(), 12F, Font.BOLD);
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Chunk CONNECT = new Chunk(new LineSeparator(1, 100, Color.BLACK, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT);
            document.add(new Paragraph("", font1));
            Chunk CONNECT1 = new Chunk(new LineSeparator(1, 100, Color.WHITE, Element.ALIGN_JUSTIFIED, 3f));
            document.add(CONNECT1);
            PdfPTable table = createFirstTableCustomer(statustype,searchText);
            table.setHeaderRows(1);
            document.add(table);
            document.add(CONNECT1);
            Paragraph foot = new Paragraph();
            Phrase ph5 = new Phrase("Terms And Condition:Terms                    Prepared By                                        Approved By   ", font1);
            foot.add(ph5);
            document.add(foot);
            document.add(CONNECT);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public PdfPTable createFirstTableCustomer(String statusType,String searchText) {
        Font font1 = new Font(getcustomfont(), 8, Font.NORMAL, Color.BLACK);
        int a = 0;
        PdfPTable table = new PdfPTable(a + 5);
        PdfPTable table1 = new PdfPTable(a +1);
        Font font = new Font(getcustomfont(), 10, Font.NORMAL, Color.WHITE);
        Color myColor = WebColors.getRGBColor("#326397");
        PdfPCell p = new PdfPCell(new Phrase("Customer", font));
        p.setBackgroundColor(myColor);
        table1.addCell(p);
        PdfPCell pc2 = new PdfPCell(new Phrase("Customer Name", font));
        PdfPCell pc3 = new PdfPCell(new Phrase("GSTN No", font));
        PdfPCell pc4 = new PdfPCell(new Phrase("State Name", font));
        PdfPCell pc5 = new PdfPCell(new Phrase("Email", font));
        PdfPCell pc6 = new PdfPCell(new Phrase("Phone no", font));
        pc2.setBackgroundColor(myColor);
        table.addCell(pc2);
        pc3.setBackgroundColor(myColor);
        table.addCell(pc3);
        pc4.setBackgroundColor(myColor);
        table.addCell(pc4);
        pc5.setBackgroundColor(myColor);
        table.addCell(pc5);
        pc6.setBackgroundColor(myColor);
        table.addCell(pc6);
        List<CustomerDTO> customerDTOList = getCustomerList(statusType,searchText);
        table.setWidthPercentage(100);
        for (CustomerDTO customerDTO : customerDTOList) {
            table.addCell(new Phrase(customerDTO.getCustomerName() + "", font1));
            table.addCell(new Phrase(customerDTO.getGstIn() + "", font1));
            table.addCell(new Phrase(customerDTO.getStateName() + "", font1));
            table.addCell(new Phrase(customerDTO.getCustomerEmail() + "", font1));
            table.addCell(new Phrase(customerDTO.getCustomerNumber() + "", font1));
        }
        table1.addCell(table);
        return table1;
    }




}
