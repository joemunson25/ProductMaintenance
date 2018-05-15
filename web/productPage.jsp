<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 
    Document   : productPage
    Created on : Oct 5, 2017, 8:54:49 AM
    Author     : josephmunson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">        
            <h2>Products</h2>
            <table class="table">
                <th>Code</th>
                <th>Description</th>
                <th>Release Date</th>
                <th>Years Released</th>
                <th>Price</th>
                <th></th>
                <th></th>
                    <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.code}</td>
                        <td>${product.description}</td>
                        <td><c:choose>
                                <c:when test="${product.releaseDate != null}">
                                    ${product.releaseDate}
                                </c:when>
                                <c:otherwise>
                                    none
                                </c:otherwise>
                            </c:choose>

                        </td>
                        <td><c:choose>
                                <c:when test="${product.yearsReleased >= 0}">
                                    ${product.yearsReleased}
                                </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatNumber value="${product.price}" type="currency" /></td>
                        <td><a href="
                               <c:url value="products">
                                   <c:param name="action" value="edit" />
                                   <c:param name="productCode" value="${product.code}" /></c:url>
                                   ">Edit</a></td>
                            <td><a href="<c:url value="products">
                                   <c:param name="action" value="delete" />
                                   <c:param name="productCode" value="${product.code}" /></c:url>
                                   ">Delete</a></td>
                        </tr> 
                </c:forEach>
                <br>


            </table>


            <form>
                <input type="submit" name="addButton" value="Add Product" />
            </form>

            <c:if test="${deleteMsg != null}">
                <br>${deleteMsg}
            </c:if>

            <c:if test="${editMsg != null}">
                <br>${editMsg}
            </c:if>
        </div>
    </body>
</html>
