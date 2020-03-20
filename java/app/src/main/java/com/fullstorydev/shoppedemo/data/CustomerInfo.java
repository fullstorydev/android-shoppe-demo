package com.fullstorydev.shoppedemo.data;

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
    private int  expirationMonth;
    private int  expirationYear;
    private String  securityCode;

    CustomerInfo(OrderBuilder builder) {
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
        if (firstName == null || firstName.length()<1) throw new NullPointerException("firstName");
        if (lastName == null || lastName.length()<1) throw new NullPointerException("lastName");
        if (address1 == null || address1.length()<1) throw new NullPointerException("address1");
        if (address2 == null) address2 = "";
        if (city == null || city.length()<1) throw new NullPointerException("city");
        if (!isValidState(state)) throw new NullPointerException("state");
        if (zip == null || zip.length()!= 5) throw new IllegalArgumentException("zip"); // only allow US zip codes
        if (creditCardNumber == null) throw new NullPointerException("creditCardNumber");
        if (expirationMonth < 0) throw new IllegalArgumentException("expirationMonth");
        if (expirationYear < 0) throw new IllegalArgumentException("expirationYear");
        if (securityCode.length() != 3) throw new IllegalArgumentException("securityCode"); // 3 digit security code
        return true;
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

        public OrderBuilder withName(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            return this;
        }
        public OrderBuilder withAddress(String address1, String address2, String city, int state, String zip) {
            this.address1 = address1;
            this.address2 = address2;
            this.city = city;
            this.state = state;
            this.zip = zip;
            return this;
        }
        public OrderBuilder withPayment(String creditCardNumber, int expirationMonth, int expirationYear, String securityCode) {
            this.creditCardNumber = creditCardNumber;
            this.expirationMonth = expirationMonth;
            this.expirationYear = expirationYear;
            this.securityCode = securityCode;
            return this;
        }

        // user facing, call this to instantiate a CustomerOrder
        public CustomerInfo buildOrder() {
            return new CustomerInfo(this);
        }
    }


    public String getFirstName(){ return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }

    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    public String getCreditCardNumber() { return creditCardNumber; }
    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }

    public int getExpirationMonth() { return expirationMonth; }
    public void setExpirationMonth(int expirationMonth) { this.expirationMonth = expirationMonth; }

    public int getExpirationYear() { return expirationYear; }
    public void setExpirationYear(int expirationYear) { this.expirationYear = expirationYear; }

    public String getSecurityCode() { return securityCode; }
    public void setSecurityCode(String securityCode) { this.securityCode = securityCode; }

}
