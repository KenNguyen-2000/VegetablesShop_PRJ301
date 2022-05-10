package sample.order;

import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author idchi
 */
public class OrderDTO {
    private int orderID;
    private Date orderDate;
    private double total;
    private String userID;
    private int status;

    public OrderDTO() {
        this.orderID = 0;
        this.orderDate = null;
        this.total = 0.0;
        this.userID = "";
        this.status = 0;
    }

    public OrderDTO(Date orderDate, double total, String userID) {
        this.orderDate = orderDate;
        this.total = total;
        this.userID = userID;
    }
    
    

    public OrderDTO(int orderID, Date orderDate, double total, String userID, int status) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.total = total;
        this.userID = userID;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
