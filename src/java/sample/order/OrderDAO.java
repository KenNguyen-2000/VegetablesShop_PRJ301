package sample.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sample.product.ProductDAO;
import sample.product.ProductDTO;
import sample.utils.DBUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author idchi
 */
public class OrderDAO {

    private static final String ADD_ORDER = "INSERT INTO tblOrder(orderDate, total, userID, status) VALUES (?, ?, ?, 1)";
    private static final String ADD_ORDERDETAIL = "INSERT INTO tblOrderDetail(price, quantity, orderID, productID) VALUES (?,?,?, ?)";
    private static final String GET_ORDER_DETAIL = "SELECT p.image, p.productName, od.price, od.quantity FROM tblOrder o, tblProduct p, tblOrderDetail od WHERE o.orderID=? AND o.orderID = od.orderID AND od.productID = p.productID";

    public static int addOrder(OrderDTO order) throws SQLException {
        int orderID = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
                ptm.setDate(1, new java.sql.Date(order.getOrderDate().getTime()));
                ptm.setFloat(2, (float) order.getTotal());
                ptm.setString(3, order.getUserID());
                ptm.executeUpdate();
                rs = ptm.getGeneratedKeys();

                if (rs.next()) {
                    orderID = rs.getInt(1);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.toString();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return orderID;
    }

    public static void addOrderDetail(OrderDetail orderDetail) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(ADD_ORDERDETAIL);
                ptm.setFloat(1, (float) orderDetail.getPrice());
                ptm.setInt(2, orderDetail.getQuantity());
                ptm.setInt(3, orderDetail.getOrderID());
                ptm.setString(4, orderDetail.getProductID());
                ptm.executeUpdate();
                ProductDAO.updateQuantity(orderDetail.getProductID(), orderDetail.getQuantity());
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.toString();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static List<ProductDTO> getOrderDetail(int orderID) throws SQLException{
            List<ProductDTO> orderDetailList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ORDER_DETAIL);
                ptm.setInt(1, orderID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String image = rs.getString("image");
                    String productName = rs.getString("productName");
                    double price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    orderDetailList.add(new ProductDTO(productName, price, quantity, image));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.toString();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return orderDetailList;
    }
}
