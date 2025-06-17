<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Budget List - Personal Finance Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">Budget List</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <a href="${pageContext.request.contextPath}/budget/add" class="btn btn-success mb-3">Add New Budget</a>
        <c:choose>
            <c:when test="${not empty budgets}">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>Category</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="budget" items="${budgets}">
                                <tr>
                                    <td>${budget.category.name}</td>
                                    <td>${budget.amount}</td>
                                    <td>
                                        <span class="badge ${budget.status == 'ToDo' ? 'bg-secondary' : budget.status == 'InProgress' ? 'bg-warning' : 'bg-success'}">
                                            ${budget.status}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/budget/delete?id=${budget.id}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this budget?');">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning" role="alert">
                    No budgets found for this user.
                </div>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary mt-3">Back to Home</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>