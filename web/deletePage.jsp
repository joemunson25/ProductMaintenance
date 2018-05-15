<%-- 
    Document   : deletePage
    Created on : Oct 5, 2017, 1:25:46 PM
    Author     : josephmunson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Product</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/tableForm.css">
    </head>
    <body>
        <div class="container">
        <h2>Delete Product - Confirmation</h2>
        <form action="products" method="POST">
            <table class="table">
                <tr>
                    <th>Code:</th>
                    <td><input type="text" name="code" value="${product.code}" readonly/></td>
                </tr>
                <tr>
                    <th>Description:</th>
                    <td><input type="text" name="description" value="${product.description}" readonly/></td>
                </tr>
                <tr>
                    <th>Release Date (yyyy-mm-dd): </th>
                    <td><input type="text" name="releaseDate" value="${product.releaseDate}" readonly/></td>
                </tr>
                <tr>
                    <th>Price:</th>
                    <td><input type="text" name="price" value="${product.price}" readonly/></td>
                </tr>
            </table>
            <input type="submit" name="deleteButton" value="Delete" /> 
            <input type="submit" name="cancelButton" value="Cancel" />
        </form>
        </div>
    </body>
</html>
