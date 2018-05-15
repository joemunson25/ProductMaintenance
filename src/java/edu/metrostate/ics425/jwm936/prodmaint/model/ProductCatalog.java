/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.metrostate.ics425.jwm936.prodmaint.model;

import edu.metrostate.ics425.prodmaint.model.Product;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author josephmunson
 */
public class ProductCatalog {

    DataSource db;

    private ProductCatalog() {
        try {
            db = getProdMaint();
        } catch (NamingException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ProductCatalog getInstance() {
        return ProductCatalogHolder.INSTANCE;
    }

    private static class ProductCatalogHolder {

        private static final ProductCatalog INSTANCE = new ProductCatalog();
    }

    private DataSource getProdMaint() throws NamingException {
        Context c = new InitialContext();
        return (DataSource) c.lookup("java:comp/env/ProdMaint");
    }

    /**
     * Gets all the current products in the database
     * @return collection of Products
     */
    public java.util.Collection<Product> selectAllProducts() {
        List<Product> products = new LinkedList<>();
        String sql = "SELECT ProductID, ProductCode, ProductDescription, ProductPrice, ProductReleaseDate FROM ProdMaint.Product;";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = db.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ProductBean product = new ProductBean();
                product.setProductId(rs.getInt("ProductID"));
                product.setCode(rs.getString("ProductCode"));
                product.setDescription(rs.getString("ProductDescription"));
                product.setPrice(rs.getDouble("ProductPrice"));
                Date date = rs.getDate("ProductReleaseDate");
                if (date == null) {
                    product.setReleaseDate(null);
                } else {
                    product.setReleaseDate(date.toLocalDate());
                }

                products.add(product);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {

                }

                stmt = null;
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {

                }

                conn = null;
            }

        }

        return products;
    }

    /**
     * Selects a product based on its code
     * @param productCode code of product to be selected
     * @return Product
     */
    public Product selectProduct(String productCode) {
        String sql = "SELECT ProductID, ProductCode, ProductDescription, ProductPrice, ProductReleaseDate "
                + "FROM ProdMaint.Product WHERE ProductCode = ?;";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, productCode);
            rs = stmt.executeQuery();

            if (rs.next()) {
                ProductBean product = new ProductBean();
                product.setProductId(rs.getInt("ProductID"));
                product.setCode(rs.getString("ProductCode"));
                product.setDescription(rs.getString("ProductDescription"));
                product.setPrice(rs.getDouble("ProductPrice"));
                Date date = rs.getDate("ProductReleaseDate");
                if (date == null) {
                    product.setReleaseDate(null);
                } else {
                    product.setReleaseDate(date.toLocalDate());
                }
                
                

                return product;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {

                }

                stmt = null;
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {

                }

                conn = null;
            }

        }

        return null;
    }

    /**
     * Deletes a product from database
     * @param product product to be deleted
     */
    public void deleteProduct(Product product) {
        String sql = "DELETE FROM ProdMaint.Product WHERE ProductCode = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getCode());
            stmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {

                }

                stmt = null;
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {

                }

                conn = null;
            }
        }

    }

    /**
     * Checks to see if product exists in database
     * @param productCode code for product to check
     * @return true if it exists
     */
    public boolean exists(String productCode) {
        String sql = "SELECT ProductCode, ProductDescription, ProductPrice, ProductReleaseDate "
                + "FROM ProdMaint.Product WHERE ProductCode = ?;";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, productCode);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {

                }

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {

                }

                stmt = null;
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {

                }

                conn = null;
            }

        }

        return false;
    }

    /**
     * Inserts a product into the database
     * @param product product to be inserted
     */
    public void insertProduct(Product product) {
        String sql = "INSERT INTO ProdMaint.Product (ProductCode, ProductDescription, ProductPrice, ProductReleaseDate)"
                + " VALUES (?,?,?,?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getCode());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            
            if (product.getReleaseDate() == null) {
                stmt.setDate(4, null);
            } else {
                stmt.setDate(4, Date.valueOf(product.getReleaseDate()));
            }
            
            stmt.execute();


        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {

                }

                stmt = null;
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {

                }

                conn = null;
            }
        }

    }

    /**
     * Updates a product in the database
     * @param product product to be updated
     */
    public void updateProduct(Product product) {
        String sql = "UPDATE ProdMaint.Product SET ProductCode = ?, ProductDescription = ?, ProductPrice = ?, ProductReleaseDate = ?"
                + " WHERE ProductCode = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getCode());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            if (product.getReleaseDate() == null) {
                stmt.setDate(4, null);
            } else {
                stmt.setDate(4, Date.valueOf(product.getReleaseDate()));
            }
            stmt.setString(5, product.getCode());
            stmt.execute();


        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {

                }

                stmt = null;
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {

                }

                conn = null;
            }
        }

    }

}
