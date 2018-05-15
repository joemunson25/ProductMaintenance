<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : editPage
    Created on : Oct 5, 2017, 1:25:34 PM
    Author     : josephmunson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Product</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/tableForm.css">
    </head>
    <body>
        <div class="container">
            <h2>Edit Product</h2>
            <form action="products" method="POST">
                <table class="table">
                    <tr>
                        <th>Code:</th>
                        <td><input type="text" name="code" value="${code}" readonly/></td>
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
                <input type="submit" name="editButton" value="Save" /> 
                <input type="submit" name="cancelButton" value="Cancel" />
                <input type="reset" value="Reset" />
            </form>
            <br>    
            <c:if test="${errorMessages != null}">
                <ul>
                    <c:forEach var="error" items="${errorMessages}">
                        <li style="color:red;">${error}</li>
                        </c:forEach>
                </ul>
            </c:if>



            <hr>
            <a href="products">Back to Products</a>
        </div>
    </body>
</html>
