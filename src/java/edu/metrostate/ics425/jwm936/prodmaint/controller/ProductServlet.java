package edu.metrostate.ics425.jwm936.prodmaint.controller;

import edu.metrostate.ics425.jwm936.prodmaint.model.ProductBean;
import edu.metrostate.ics425.jwm936.prodmaint.model.ProductCatalog;
import edu.metrostate.ics425.prodmaint.model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author josephmunson
 */
public class ProductServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String addButton = request.getParameter("addButton");
        String cancelButton = request.getParameter("cancelButton");
        String editButton = request.getParameter("editButton");
        String deleteButton = request.getParameter("deleteButton");
        ProductCatalog pc = ProductCatalog.getInstance();

        //by default
        if (action == null) {
            action = "products";
        }
        String url = "/productPage.jsp";

        //*********************************************
        //process buttons
        //*********************************************
        //to ADD page
        if (addButton != null) {
            if (addButton.equals("Add Product")) {
                url = "/addProduct.jsp";
            } else {
                url = "/error.html";
            }
        }

        //to PRODUCT page
        if (cancelButton != null) {
            if (cancelButton.equals("Cancel")) {
                url = "/productPage.jsp";
            } else {
                url = "/error.html";
            }
        }

        //saves edits
        if (editButton != null) {
            if (editButton.equals("Save")) {

                //get info from request
                String code = request.getParameter("code");
                String description = request.getParameter("description");
                String releaseDateStr = request.getParameter("releaseDate");
                String priceStr = request.getParameter("price");

                List<String> alerts = new ArrayList<>();

                if (pc.exists(code)) {
                    //check product description
                    if (description == null || description.trim().isEmpty()) {
                        alerts.add("Product Description Required.");
                    }

                    //check release date
                    LocalDate releaseDate = null;
                    if (releaseDateStr != null && !releaseDateStr.trim().isEmpty()) {
                        try {
                            releaseDate = LocalDate.parse(releaseDateStr);
                        } catch (DateTimeParseException e) {
                            alerts.add("Invalid date.");
                        }
                    }

                    //check price
                    Double price = null;
                    try {
                        price = Double.parseDouble(priceStr);
                        if (price < 0) {
                            alerts.add("Price cannot be negative.");
                        }
                        if (price > 99999.99) {
                            alerts.add("Price is too high.");
                        }

                    } catch (NumberFormatException e) {
                        alerts.add("Price not formatted properly.");
                    }

                    if (alerts.isEmpty()) {
                        Product product = pc.selectProduct(code);
                        product.setDescription(description);
                        product.setReleaseDate(releaseDate);
                        product.setPrice(price);
                        pc.updateProduct(product);
                        String editMsg = "Product " + product.getCode() + " saved.";
                        request.setAttribute("editMsg", editMsg);
                    } else {
                        request.setAttribute("errorMessages", alerts);
                        request.setAttribute("code", code);
                        request.setAttribute("description", description);
                        request.setAttribute("releaseDate", releaseDateStr);
                        request.setAttribute("price", priceStr);
                        url = "/editPage.jsp";
                    }
                } else {
                    String editMsg = "Product No Longer Exists.";
                    request.setAttribute("editMsg", editMsg);
                }

            } else {
                url = "/error.html";
            }
        }

        //removes product
        if (deleteButton != null) {
            if (deleteButton.equals("Delete")) {
                //do delete code here
                String code = request.getParameter("code");
                boolean prodExists = pc.exists(code);
                String deleteMsg = "";

                if (prodExists) {
                    Product productToDelete = pc.selectProduct(code);
                    deleteMsg = "Product " + productToDelete.getCode() + " deleted.";
                    pc.deleteProduct(productToDelete);
                }

                request.setAttribute("deleteMsg", deleteMsg);

                //url = "/construction.html";
            } else {
                url = "/error.html";
            }
        }

        //*********************************************
        //process actions
        //*********************************************
        if (action.equals("edit")) { //to EDIT page
            String productCode = request.getParameter("productCode");

            if (productCode != null) {
                if (pc.exists(productCode)) {
                    Product product = pc.selectProduct(productCode);

                    request.setAttribute("code", product.getCode());
                    request.setAttribute("description", product.getDescription());
                    request.setAttribute("releaseDate", product.getReleaseDate());
                    request.setAttribute("price", product.getPrice());
                    url = "/editPage.jsp";
                } else {
                    url = "/error.html";
                }
            } else {
                url = "/error.html";
            }
        } else if (action.equals("delete")) { //delete product
            String productCode = request.getParameter("productCode");

            if (productCode != null) {
                if (pc.exists(productCode)) {
                    request.setAttribute("product", pc.selectProduct(productCode));
                    url = "/deletePage.jsp";
                } else {
                    url = "/error.html";
                }
            } else {
                url = "/error.html";
            }
        } else if (action.equals("products")) { //to PRODUCTS page
            request.setAttribute("products", pc.selectAllProducts());

        } else if (action.equals("Add")) { //add product
            url = "/addProduct.jsp";

            //get info from request
            String code = request.getParameter("code");
            String description = request.getParameter("description");
            String releaseDateStr = request.getParameter("releaseDate");
            String priceStr = request.getParameter("price");

            List<String> alerts = new ArrayList<>();

            //check if required fields are empty
            if (code == null || code.trim().isEmpty()) {
                alerts.add("Product Code Required.");
            }
            if (description == null || description.trim().isEmpty()) {
                alerts.add("Product Description Required.");
            }

            //check if product exists
            if (pc.exists(code)) {
                alerts.add("This product already exists.");
            }

            //check release date
            LocalDate releaseDate = null;
            if (releaseDateStr != null && !releaseDateStr.trim().isEmpty()) {
                try {
                    releaseDate = LocalDate.parse(releaseDateStr);
                } catch (DateTimeParseException e) {
                    alerts.add("Invalid date.");
                }
            }

            //check price
            Double price = null;
            try {
                price = Double.parseDouble(priceStr);
                if (price < 0) {
                    alerts.add("Price cannot be negative.");
                }
                if (price > 99999.99) {
                    alerts.add("Price is too high.");
                }
            } catch (NumberFormatException e) {
                alerts.add("Price not formatted properly.");
            }

            if (alerts.isEmpty()) {
                Product product = new ProductBean();
                product.setCode(code);
                product.setDescription(description);
                product.setReleaseDate(releaseDate);
                product.setPrice(price);
                pc.insertProduct(product);
                request.setAttribute("product", product);
                request.setAttribute("successMessage", "Success!");
            } else {
                request.setAttribute("errorMessages", alerts);
                request.setAttribute("code", code);
                request.setAttribute("description", description);
                request.setAttribute("releaseDate", releaseDateStr);
                request.setAttribute("price", priceStr);
            }

        } else {
            url = "/error.html";
        }

        request.getRequestDispatcher(url).forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
        super.init();

        //configured in web.xml file
//        String catalogPath = getInitParameter("catalogPath");
//
//        ServletContext sc = getServletContext();
//
//        //initialize a catalog
//        if (!ProductCatalog.init(sc.getRealPath(catalogPath))) {
//            throw new ServletException("Unable to initialize catalog");
//        }
    }

}
