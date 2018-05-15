<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : addProduct
    Created on : Oct 5, 2017, 10:33:42 AM
    Author     : josephmunson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Product</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/tableForm.css">
    </head>
    <body>
        <div class="container">
            <h2>Add Product</h2>
            <form action="products" method="POST">
                <table class="table">
                    <tr>
                        <th>Code:</th>
                        <td><input type="text" name="code" value="${code}" /></td>
                    </tr>
                    <tr>
                        <th>Description:</th>
                        <td><input type="text" name="description" value="${description}" /></td>
                    </tr>
                    <tr>
                        <th>Release Date (yyyy-mm-dd): </th>
                        <td><input type="text" name="releaseDate" value="${releaseDate}" /></td>
                    </tr>
                    <tr>
                        <th>Price:</th>
                        <td><input type="text" name="price" value="${price}" /></td>
                    </tr>
                </table>

                <input type="submit" name="action" value="Add" /> 
                <input type="submit" name="cancelButton" value="Cancel" />
                <input type="reset" value="Reset" />
            </form>
                <br>    
            <c:if test="${successMessage != null}">
                
                ${successMessage} <br>
                Product ${product.code} has been created.
            </c:if>  
            <ul>
                <c:forEach var="error" items="${errorMessages}">
                    <li style="color:red;">${error}</li>
                    </c:forEach>
            </ul>

            <hr>
            <a href="products">Back to Products</a>
        </div>
    </body>
</html>
