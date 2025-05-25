<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction List - Personal Finance Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">Transaction List</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <a href="${pageContext.request.contextPath}/transaction/add" class="btn btn-success mb-3">Add New Transaction</a>
        <div class="table-responsive">
            <table class="table table-bordered table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Type</th>
                        <th>Amount</th>
                        <th>Date</th>
                        <th>Category</th>
                        <th>Account</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="transaction" items="${transactions}">
                        <tr>
                            <td>${transaction.id}</td>
                            <td>
                                <span class="badge ${transaction.type == 'income' ? 'bg-success' : 'bg-danger'}">
                                    ${transaction.type}
                                </span>
                            </td>
                            <td>${transaction.amount}</td>
                            <td>${transaction.date}</td>
                            <td>${transaction.category.name}</td>
                            <td>${transaction.account.name}</td>
                            <td>${transaction.description}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/transaction/edit?id=${transaction.id}" class="btn btn-sm btn-primary me-1">Edit</a>
                                <a href="${pageContext.request.contextPath}/transaction/delete?id=${transaction.id}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this transaction?');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary mt-3">Back to Home</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>