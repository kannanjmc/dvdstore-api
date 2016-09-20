package com.scottwseo.dvdstore.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by seos on 9/19/16.
 */
public class Customer {

    // "" indicates use the field name as json name
    @JsonProperty("customer_id")
    private Long customerid;

    @JsonProperty("")
    private String firstname;

    @JsonProperty("")
    private String lastname;

    @JsonProperty("")
    private String address1;

    @JsonProperty("")
    private String address2;

    @JsonProperty("")
    private String city;

    @JsonProperty("")
    private String state;

    @JsonProperty("")
    private int zip;

    @JsonProperty("")
    private String country;

    @JsonProperty("")
    private int region;

    @JsonProperty("")
    private String email;

    @JsonProperty("")
    private String phone;

    @JsonProperty("credit_card_type")
    private int creditcardtype;

    @JsonProperty("credit_card")
    private String creditcard;

    @JsonProperty("credit_card_expiration")
    private String creditcardexpiration;

    @JsonProperty("")
    private String username;

    @JsonProperty("")
    private String password;

    @JsonProperty("")
    private int age;

    @JsonProperty("")
    private int income;

    private GenderEnum gender = null;

    /**
     * Gets or Sets gender
     */
    public enum GenderEnum {
        M("M"),

        F("F");

        private String value;

        GenderEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public Customer gender(GenderEnum gender) {
        this.gender = gender;
        return this;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    private Map error = null;

    public <T> T error(Map error) {
        this.error = error;
        return (T) this;
    }

    public Map error() {
        return this.error;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public Customer customerid(Long customerid) {
        this.customerid = customerid;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Customer firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Customer lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public Customer address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Customer address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Customer city(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Customer state(String state) {
        this.state = state;
        return this;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public Customer zip(int zip) {
        this.zip = zip;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Customer country(String country) {
        this.country = country;
        return this;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public Customer region(int region) {
        this.region = region;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Customer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public int getCreditcardtype() {
        return creditcardtype;
    }

    public void setCreditcardtype(int creditcardtype) {
        this.creditcardtype = creditcardtype;
    }

    public Customer creditcardtype(int creditcardtype) {
        this.creditcardtype = creditcardtype;
        return this;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    public Customer creditcard(String creditcard) {
        this.creditcard = creditcard;
        return this;
    }

    public String getCreditcardexpiration() {
        return creditcardexpiration;
    }

    public void setCreditcardexpiration(String creditcardexpiration) {
        this.creditcardexpiration = creditcardexpiration;
    }

    public Customer creditcardexpiration(String creditcardexpiration) {
        this.creditcardexpiration = creditcardexpiration;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Customer username(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer password(String password) {
        this.password = password;
        return this;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Customer age(int age) {
        this.age = age;
        return this;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public Customer income(int income) {
        this.income = income;
        return this;
    }

}
