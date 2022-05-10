/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sample.utils.DBUtils;

/**
 *
 * @author idchi
 */
public class ProductDAO {

    private static final String US = "US";
    private static final String US_SEARCH = "SELECT productID, productName, image, price, quantity, importDate, usingDate, p.catagoryID\n"
            + "FROM tblProduct p, tblCatagory c\n"
            + "WHERE (p.productName like ? AND p.usingDate >= (select GETDATE())\n"
            + "	OR catagoryName like ? AND p.usingDate >= (select GETDATE())) AND p.catagoryID = c.catagoryID AND quantity>0 AND status=1";
    private static final String AD_SEARCH = "SELECT productID, productName, image, price, quantity, importDate, usingDate, p.catagoryID\n"
            + "From tblCatagory c, tblProduct p\n"
            + "WHERE (p.productName like ? or catagoryName like ?) AND p.catagoryID = c.catagoryID AND status=1";
    private static final String DELETE = "UPDATE tblProduct SET status=0 WHERE productID=?";
    private static final String UPDATE = "UPDATE tblProduct SET productName=?, price=?, quantity=?, catagoryID=?, importDate=?, usingDate=? WHERE productID=?";
    private static final String CHECK_DUPPLICATE = "SELECT productName FROM tblProduct WHERE productID=?";
    private static final String INSERT = "INSERT INTO tblProduct(productID, productName, image, price, quantity, catagoryID, importDate, usingDate, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
    private static final String GET_MAX_QUANTITY = "SELECT quantity FROM tblProduct WHERE productID=?";
    private static final String GET_CATAGORY_NAME = "SELECT c.catagoryName FROM tblCatagory c, tblProduct p WHERE p.catagoryID=? AND p.catagoryID = c.catagoryID";
    private static final String GET_ALL_CATAGORY = "SELECT catagoryID, catagoryName FROM tblCatagory";
    private static final String UPDATE_QUANTITY = "UPDATE tblProduct SET quantity=? WHERE productID=?";

    public static List<ProductDTO> getListProduct(String search, String role) throws SQLException {
        List<ProductDTO> listProduct = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                if (US.equals(role)) {
                    ptm = conn.prepareStatement(US_SEARCH);
                } else {
                    ptm = conn.prepareStatement(AD_SEARCH);
                }
                ptm.setString(1, "%" + search + "%");
                ptm.setString(2, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    String image = rs.getString("image");
                    String catagoryID = rs.getString("catagoryID");
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    double price = Double.parseDouble(rs.getString("price"));
                    Date importDate = rs.getDate("importDate");
                    Date usingDate = rs.getDate("usingDate");
                    listProduct.add(new ProductDTO(productID, productName, image, price, quantity, catagoryID, importDate, usingDate));
                }
            }
        } catch (ClassNotFoundException | NumberFormatException | SQLException e) {
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
        return listProduct;
    }

    public static boolean deleteProduct(String productID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE);
                ptm.setString(1, productID);
                check = ptm.executeUpdate() > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public static boolean updateProduct(ProductDTO newProduct) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {

                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, newProduct.getProductName());
                ptm.setFloat(2, (float) newProduct.getPrice());
                ptm.setInt(3, newProduct.getQuantity());
                ptm.setString(4, newProduct.getCatagoryID());
                ptm.setDate(5, new java.sql.Date(newProduct.getImportDate().getTime()));
                ptm.setDate(6, new java.sql.Date(newProduct.getUsingDate().getTime()));
                ptm.setString(7, newProduct.getProductID());
                check = ptm.executeUpdate() > 0;
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
        return check;
    }

    public static boolean insertProduct(ProductDTO newProduct) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT);
                ptm.setString(1, newProduct.getProductID());
                ptm.setString(2, newProduct.getProductName());
                ptm.setString(3, newProduct.getImage());
                ptm.setFloat(4, (float) newProduct.getPrice());
                ptm.setInt(5, newProduct.getQuantity());
                ptm.setString(6, newProduct.getCatagoryID());
                ptm.setDate(7, new java.sql.Date(newProduct.getImportDate().getTime()));
                ptm.setDate(8, new java.sql.Date(newProduct.getUsingDate().getTime()));
                check = ptm.executeUpdate() > 0;
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
        return check;
    }

    public static int getMaxQuantity(String productID) throws SQLException {
        int maxQuantity = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_MAX_QUANTITY);
                ptm.setString(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    maxQuantity = Integer.parseInt(rs.getString("quantity"));
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
        return maxQuantity;
    }

    public static List<Catagory> getAllCatagory() throws SQLException {
        List<Catagory> catagoryList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALL_CATAGORY);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String catagoryName = rs.getString("catagoryName");
                    String catagoryID = rs.getString("catagoryID");
                    catagoryList.add(new Catagory(catagoryID, catagoryName));
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
        return catagoryList;
    }

    public static boolean checkDupplicated(String productID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {

                ptm = conn.prepareStatement(CHECK_DUPPLICATE);
                ptm.setString(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    check = true;
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
        return check;
    }

    public static String getCatagoryName(String catagoryID) throws SQLException {
        String catagoryName = "";
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {

                ptm = conn.prepareStatement(GET_CATAGORY_NAME);
                ptm.setString(1, catagoryID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    catagoryName = rs.getString("catagoryName");
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
        return catagoryName;
    }

    public static void updateQuantity(String productID, int quantity) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        int remainQuantity = getMaxQuantity(productID);
        int newQuantity;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                if (remainQuantity == quantity) {
                    newQuantity = 0;
                    ptm = conn.prepareStatement(UPDATE_QUANTITY);
                    ptm.setInt(1, newQuantity);
                    ptm.setString(2, productID);
                    ptm.executeUpdate();
                } else {
                    newQuantity = remainQuantity - quantity;
                    ptm = conn.prepareStatement(UPDATE_QUANTITY);
                    ptm.setInt(1, newQuantity);
                    ptm.setString(2, productID);
                    ptm.executeUpdate();
                }
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
}
