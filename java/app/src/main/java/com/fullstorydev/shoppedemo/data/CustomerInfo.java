package com.fullstorydev.shoppedemo.data;

import com.fullstorydev.shoppedemo.utilities.Constants;

import java.util.HashMap;

public class CustomerInfo {
    private HashMap<String,String> strMap;
    private HashMap<String,Integer> intMap;

    CustomerInfo(){
        // all key-value pairs available for USER_INFO_STRING_KEYS
        strMap = new HashMap<>();
        // all key-value pairs available for USER_INFO_INTEGER_KEYS
        intMap = new HashMap<>();
    }

    // all available user info keys are managed by Constants.USER_INFO_STRING_KEYS
    boolean putValue(String key, String val){
        for(String k: Constants.USER_INFO_STRING_KEYS){
            if(k.equals(key)) {
                strMap.put(key, val);
                return true;
            }
        }
        return false;
    }

    // all available user info keys are managed by Constants.USER_INFO_INTEGER_KEYS
    boolean putValue(String key, Integer val){
        for(String k: Constants.USER_INFO_INTEGER_KEYS){
            if(k.equals(key)) {
                intMap.put(key, val);
                return true;
            }
        }
        return false;
    }

    // public getters for data binding
    public String getFirstName(){ return strMap.get(Constants.FIRST_NAME); }
    public String getLastName() { return strMap.get(Constants.LAST_NAME); }
    public String getAddress1() { return strMap.get(Constants.ADDRESS_1); }
    public String getAddress2() { return strMap.get(Constants.ADDRESS_2); }
    public String getCity() { return strMap.get(Constants.CITY); }
    public Integer getState() { return intMap.get(Constants.STATE); }
    public String getZip() { return strMap.get(Constants.ZIP); }
    public String getCreditCardNumber() { return strMap.get(Constants.CREDIT_CARD_NUMBER); }
    public Integer getExpirationMonth() { return intMap.get(Constants.EXPIRATION_MONTH); }
    public Integer getExpirationYear() { return intMap.get(Constants.EXPIRATION_YEAR); }
    public String getSecurityCode() { return strMap.get(Constants.SECURITY_CODE); }
}
