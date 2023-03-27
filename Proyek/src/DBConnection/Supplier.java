package DBConnection;

import java.util.function.Consumer;

public class Supplier{
    Integer idSup;
    String nameSup;
    Integer priceSup;
    String emailSup;
    String  phoneNumSup;

    public Supplier(Integer idSup, String nameSup, Integer priceSup, String emailSup, String phoneNumSup) {
        this.idSup = idSup;
        this.nameSup = nameSup;
        this.priceSup = priceSup;
        this.phoneNumSup = phoneNumSup;
        this.emailSup = emailSup;
    }

    public int getIdSup() {
        return idSup;
    }

    public void setIdSup(Integer idSup) {
        this.idSup = idSup;
    }

    public String getNameSup() {
        return nameSup;
    }

    public void setNameSup(String nameSup) {
        this.nameSup = nameSup;
    }

    public Integer getPriceSup() {
        return priceSup;
    }

    public void setPriceSup(Integer priceSup) {
        this.priceSup = priceSup;
    }

    public String getPhoneNumSup() {
        return phoneNumSup;
    }

    public void setPhoneNumSup(String phoneNumSup) {
        this.phoneNumSup = phoneNumSup;
    }

    public String getEmailSup() {
        return emailSup;
    }

    public void setEmailSup(String emailSup) {
        this.emailSup = emailSup;
    }

}



