package io.zillion.universalcopypaste.utils;

/**
 * Created by jafarnaqvi on 17/05/16.
 */
public class User {


    private String token;
    private String name;
    private String address;
    private String mobile;
    private String add1;
    private String add2;

    public String getAdd3() {
        return add3;
    }

    public void setAdd3(String add3) {
        this.add3 = add3;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    private String add3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    private static User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }

}
