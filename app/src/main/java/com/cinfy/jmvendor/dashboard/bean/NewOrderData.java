package com.cinfy.jmvendor.dashboard.bean;

public class NewOrderData {

    String name,orderid,address,itemdetail,amount;


    public NewOrderData(){}

    public NewOrderData(String name,String orderid,String address,String itemdetail,String amount){
        this.name=name;
        this.orderid=orderid;
        this.address=address;
        this.itemdetail=itemdetail;
        this.amount=amount;


    }

    public String getName() {
        return name;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getAddress() {
        return address;
    }

    public String getItemdetail() {
        return itemdetail;
    }

    public String getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setItemdetail(String itemdetail) {
        this.itemdetail = itemdetail;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
