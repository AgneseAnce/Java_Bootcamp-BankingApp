package bankingApplication;

import bankingApplication.Country;
import bankingApplication.Gender;

import java.time.LocalDate;

public class Users {
    private String name, address, IDNo;
    private LocalDate dateofBirth;
    private String phone;
    private Gender sex;
    private Country country;
    private String password;
    private double balance = 0.00;
    private Currency currency;
    private String accountNo;
    private String countryCode;

    public Users(){}

    public Users(String name, String IDNo, LocalDate dateofBirth,
                 Gender sex, String address, Country country,
                 String phone, String password, double balance,
                 Currency currency, String accountNo, String countryCode){
        this.name = name;
        this.IDNo = IDNo;
        this.dateofBirth = dateofBirth;
        this.sex = sex;
        this.address = address;
        this.country = country;
        this.phone = phone;
        this.password = password;
        this.balance = balance;
        this.currency = currency;
        this.accountNo = accountNo;
        this.countryCode = countryCode;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Users(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIDNo() {
        return IDNo;
    }

    public void setIDNo(String IDNo) {
        this.IDNo = IDNo;
    }

    public LocalDate getDateofBirth() {
        return dateofBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateofBirth(LocalDate dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String toString(){
        return "\nName: " + name +
                "\nPersonal ID No.: " + IDNo +
                "\nDate of birth: " + dateofBirth +
                "\nGender: " + sex +
                "\nAddress: " + address +
                "\nCountry: " + country +
                "\nPhone: " + phone +
                "\nAccount No.: " + accountNo +
                "\nMain currency: " + currency;
    }
}
