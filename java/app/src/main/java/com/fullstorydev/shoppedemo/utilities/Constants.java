package com.fullstorydev.shoppedemo.utilities;

import java.util.Calendar;

public class Constants {
    private static final String[] States={
        "AL",
        "AK",
        "AZ",
        "AR",
        "CA",
        "CO",
        "CT",
        "DE",
        "FL",
        "GA",
        "HI",
        "ID",
        "IL",
        "IN",
        "IA",
        "KS",
        "KY",
        "LA",
        "ME",
        "MD",
        "MA",
        "MI",
        "MN",
        "MS",
        "MO",
        "MT",
        "NE",
        "NV",
        "NH",
        "NJ",
        "NM",
        "NY",
        "NC",
        "ND",
        "OH",
        "OK",
        "OR",
        "PA",
        "RI",
        "SC",
        "SD",
        "TN",
        "TX",
        "UT",
        "VT",
        "VA",
        "WA",
        "WV",
        "WI",
        "WY"
    };

    static public String[] getStates(){ return States; }

    static public boolean isValidState(int state) {
        return state >= 0 && state <= States.length;
    }

    // generate all valid year selection for credit card expiration year. range: now ~ +10 years
    static public Integer[] getYears() {
        int numYears = 10;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        Integer[] years = new Integer[numYears];
        for(int i=0;i<numYears;i++){
            years[i] = currentYear + i;
        }
        return years;
    }

    // generate all valid month selection for credit card expiration year. range: 1~12
    static public Integer[] getMonths() {
        int numMonth = 12;
        Integer[] months = new Integer[12];
        for(int i=0;i<numMonth;i++){
            months[i] = i+1;
        }
        return months;
    }

    // all available user info fields
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS_1 = "address1";
    public static final String ADDRESS_2 = "address2";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String ZIP = "zip";
    public static final String CREDIT_CARD_NUMBER = "creditCardNumber";
    public static final String EXPIRATION_MONTH = "expirationMonth";
    public static final String EXPIRATION_YEAR = "expirationYear";
    public static final String SECURITY_CODE = "securityCode";

    public static final String PREFERENCE_FILE_KEY = "com.fullstorydev.shoppedemo.PREFERENCE_FILE_KEY";

    public enum Status { SUCCESS, LOADING, ERROR };
}