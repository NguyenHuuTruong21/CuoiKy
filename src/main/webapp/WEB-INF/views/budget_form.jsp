<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Budget - Personal Finance Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">Add Budget</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <div class="card shadow-sm">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/budget/add" method="post">
                    <!-- <div class="mb-3">
                        <label class="form-label">ID</label>
                        <input type="text" name="id" class="form-control" placeholder="Enter budget ID" required/>
                    </div> -->
                    <div class="mb-3">
                        <label class="form-label">Category</label>
                        <select name="categoryId" class="form-select" required>
                            <option value="" disabled selected>Select category</option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Amount</label>
                        <input type="number" name="amount" step="0.01" class="form-control" placeholder="Enter budget amount" required/>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Budget</button>
                    <a href="${pageContext.request.contextPath}/budgets" class="btn btn-secondary ms-2">Cancel</a>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>