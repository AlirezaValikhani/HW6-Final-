package models;

public class CreditCard {
    private Long id;
    private String accountNumber;
    private String cardNumber;
    private String cvv2;
    private String password;
    private String expireDate;
    private AccountType accountType;

    public CreditCard(Long id, String accountNumber, String cardNumber, String cvv2, String password, String expireDate, AccountType accountType) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.cvv2 = cvv2;
        this.password = password;
        this.expireDate = expireDate;
        this.accountType = accountType;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return expireDate;
    }

    public void setDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
