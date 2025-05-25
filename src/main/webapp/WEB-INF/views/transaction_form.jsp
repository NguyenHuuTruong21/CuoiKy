<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${transaction.id == 0 ? 'Add' : 'Edit'} Transaction - Personal Finance Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">${transaction.id == 0 ? 'Add' : 'Edit'} Transaction</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <div class="card shadow-sm">
            <div class="card-body">
                <form action="${transaction.id == 0 ? '/transaction/add' : '/transaction/update'}" method="post">
                    <c:if test="${transaction.id != 0}">
                        <input type="hidden" name="id" value="${transaction.id}"/>
                    </c:if>
                    <div class="mb-3">
                        <label class="form-label">Type</label>
                        <select name="type" class="form-select" required>
                            <option value="income" ${transaction.type == 'income' ? 'selected' : ''}>Income</option>
                            <option value="expense" ${transaction.type == 'expense' ? 'selected' : ''}>Expense</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Amount</label>
                        <input type="number" name="amount" value="${transaction.amount}" step="0.01" class="form-control" placeholder="Enter amount" required/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Date</label>
                        <input type="date" name="date" value="${transaction.date}" class="form-control" required/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Category</label>
                        <select name="categoryId" class="form-select" required>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.id}" ${transaction.category != null && transaction.category.id == category.id ? 'selected' : ''}>
                                    ${category.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Account</label>
                        <select name="accountId" class="form-select" required>
                            <c:forEach var="account" items="${accounts}">
                                <option value="${account.id}" ${transaction.account != null && transaction.account.id == account.id ? 'selected' : ''}>
                                    ${account.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <input type="text" name="description" value="${transaction.description}" class="form-control" placeholder="Enter description (optional)"/>
                    </div>
                    <button type="submit" class="btn btn-primary">${transaction.id == 0 ? 'Add' : 'Update'} Transaction</button>
                    <a href="${pageContext.request.contextPath}/transactions" class="btn btn-secondary ms-2">Cancel</a>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>