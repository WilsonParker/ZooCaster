package com.graction.developer.zoocaster.Model;

public class AddressItem {
    public AddressItem() {
    }

    public AddressItem(String address, String address_do, String address_si, String address_gu) {
        this.address = address;
        this.address_do = address_do;
        this.address_si = address_si;
        this.address_gu = address_gu;
    }

    private String address    // Address obtained with lat & lng
            , address_do, address_si, address_gu;

    public String getAddress() {
        return address;
    }

    public String getAddress_do() {
        return address_do;
    }

    public void setAddress_do(String address_do) {
        this.address_do = address_do;
    }

    public String getAddress_si() {
        return address_si;
    }

    public void setAddress_si(String address_si) {
        this.address_si = address_si;
    }

    public String getAddress_gu() {
        return address_gu;
    }

    public void setAddress_gu(String address_gu) {
        this.address_gu = address_gu;
    }

    @Override
    public String toString() {
        return "AddressItem{" +
                "address='" + address + '\'' +
                ", address_do='" + address_do + '\'' +
                ", address_si='" + address_si + '\'' +
                ", address_gu='" + address_gu + '\'' +
                '}';
    }
}
