package com.fullstorydev.shoppedemo.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fullstory.FS;
import com.fullstorydev.shoppedemo.utilities.Constants;

public class CustomerInfo {
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private int state;
    private String zip;
    private String creditCardNumber;
    private int expirationMonth;
    private int expirationYear;
    private String securityCode;
    private boolean isValid;

    private MutableLiveData<String> firstNameError = new MutableLiveData<>();
    private MutableLiveData<String> lastNameError = new MutableLiveData<>();
    private MutableLiveData<String> address1Error = new MutableLiveData<>();
    private MutableLiveData<String> cityError = new MutableLiveData<>();
    private MutableLiveData<String> stateError = new MutableLiveData<>();
    private MutableLiveData<String> zipError = new MutableLiveData<>();
    private MutableLiveData<String> creditCardNumberError = new MutableLiveData<>();
    private MutableLiveData<String> expirationMonthError = new MutableLiveData<>();
    private MutableLiveData<String> expirationYearError = new MutableLiveData<>();
    private MutableLiveData<String> securityCodeError = new MutableLiveData<>();

    private CustomerInfo(OrderBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.address1 = builder.address1;
        this.address2 = builder.address2;
        this.city = builder.city;
        this.state = builder.state;
        this.zip = builder.zip;
        this.creditCardNumber = builder.creditCardNumber;
        this.expirationMonth = builder.expirationMonth;
        this.expirationYear = builder.expirationYear;
        this.securityCode = builder.securityCode;
    }

    public boolean validateOrder() throws NullPointerException, IllegalArgumentException{
        this.isValid = true;

        //backdoor for printing out the fullstory URL for internal debugging
        if(this.firstName.equals("give me fullstory")) Log.i("CustomerInfo", "FullStory URL is: " + FS.getCurrentSessionURL());

        if (firstName == null || firstName.length()<1) {
            this.firstNameError.postValue("first name can not be null");
            this.isValid = false;
        } else {
            this.firstNameError.postValue(null);
        }
        if (lastName == null || lastName.length()<1) {
            this.lastNameError.postValue("last name can not be null");
            this.isValid = false;
        } else {
            this.lastNameError.postValue(null);
        }
        if (address1 == null || address1.length()<1) {
            this.address1Error.postValue("address 1 can not be null");
            this.isValid = false;
        } else {
            this.address1Error.postValue(null);
        }
        if (address2 == null) address2 = "";
        if (city == null || city.length()<1) {
            this.cityError.postValue("city can not be null");
            this.isValid = false;
        } else {
            this.cityError.postValue(null);
        }
        if (!isValidState(state)) {
            this.stateError.postValue("invalid state value");
            this.isValid = false;
        } else {
            this.stateError.postValue(null);
        }
        if (zip == null || zip.length()!= 5) {
            // only allow US zip codes
            this.zipError.postValue("invalid zip code: should be 5 digits");
            this.isValid = false;
        } else {
            this.zipError.postValue(null);
        }
        if (creditCardNumber == null || creditCardNumber.length()<1) {
            this.creditCardNumberError.postValue("credit card number can not be null");
            this.isValid = false;
        } else {
            this.creditCardNumberError.postValue(null);
        }
        if (expirationMonth < 0 || expirationMonth >= 12) {
            this.expirationMonthError.postValue("invalid expiration month");
            this.isValid = false;
        } else {
            this.expirationMonthError.postValue(null);
        }
        if (expirationYear < 0) {
            this.expirationYearError.postValue("invalid expiration year");
            this.isValid = false;
        } else {
            this.expirationYearError.postValue(null);
        }
        if (securityCode.length() != 3) {
            this.securityCodeError.postValue("invalid security code: should be 3 digits");
            this.isValid = false;
        } else {
            this.securityCodeError.postValue(null);
        }
        return this.isValid;
    }

    private boolean isValidState(int state){
        return Constants.isValidState(state);
    }

    static class OrderBuilder {
        private String firstName;
        private String lastName;
        private String address1;
        private String address2;
        private String city;
        private int state;
        private String zip;
        private String creditCardNumber;
        private int expirationMonth;
        private int expirationYear;
        private String securityCode;

        OrderBuilder withName(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            return this;
        }
        OrderBuilder withAddress(String address1, String address2, String city, int state, String zip) {
            this.address1 = address1;
            this.address2 = address2;
            this.city = city;
            this.state = state;
            this.zip = zip;
            return this;
        }
        OrderBuilder withPayment(String creditCardNumber, int expirationMonth, int expirationYear, String securityCode) {
            this.creditCardNumber = creditCardNumber;
            this.expirationMonth = expirationMonth;
            this.expirationYear = expirationYear;
            this.securityCode = securityCode;
            return this;
        }

        // user facing, call this to instantiate a CustomerOrder
        CustomerInfo buildOrder() {
            return new CustomerInfo(this);
        }
    }


    public String getFirstName(){ return firstName; }
    public LiveData<String> getFirstNameError(){ return this.firstNameError; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public LiveData<String> getLastNameError(){ return this.lastNameError; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress1() { return address1; }
    public LiveData<String> getAddress1Error(){ return this.address1Error; }
    public void setAddress1(String address1) { this.address1 = address1; }

    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getCity() { return city; }
    public LiveData<String> getCityError(){ return this.cityError; }
    public void setCity(String city) { this.city = city; }

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

    public String getZip() { return zip; }
    public LiveData<String> getZipError(){ return this.zipError; }
    public void setZip(String zip) { this.zip = zip; }

    public String getCreditCardNumber() { return creditCardNumber; }
    public LiveData<String> getCreditCardNumberError(){ return this.creditCardNumberError; }
    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }

    public int getExpirationMonth() { return expirationMonth; }
    public void setExpirationMonth(int expirationMonth) { this.expirationMonth = expirationMonth; }

    public int getExpirationYear() { return expirationYear; }
    public void setExpirationYear(int expirationYear) { this.expirationYear = expirationYear; }

    public String getSecurityCode() { return securityCode; }
    public LiveData<String> getSecurityCodeError(){ return this.securityCodeError; }
    public void setSecurityCode(String securityCode) { this.securityCode = securityCode; }

}
