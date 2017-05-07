package Beans;

import java.io.Serializable;

/**
 * Created by kriss on 03-May-17.
 */
public class UserBean implements Serializable {
    private String firstname;
    private String lastname;
    private int birthYear;
    private String username;
    private String nickname;
    private String email;
    private String address;
    private int id;
    private boolean is_active;
    private boolean is_admin;

    @Override
    public String toString() {
        return "UserBean{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthYear=" + birthYear +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", creditCard='" + creditCard + '\'' +
                ", is_active='" + is_active + '\'' +
                '}';
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    private String password;
    private String creditCard;

    public UserBean(){

    }


    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }
    public boolean Is_active() { return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean is_admin() {return is_admin; }

    public void setIs_admin(boolean is_admin) { this.is_admin = is_admin;
    }
}
