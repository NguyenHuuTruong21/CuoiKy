<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Transaction - Personal Finance Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">Add Transaction</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <div class="card shadow-sm">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/transaction/add" method="post">
                    <div class="mb-3">
                        <label class="form-label">Type</label>
                        <select name="type" class="form-select" required>
                            <option value="" disabled ${empty transaction.type ? 'selected' : ''}>Select type</option>
                            <option value="income" ${transaction.type == 'income' ? 'selected' : ''}>Income</option>
                            <option value="expense" ${transaction.type == 'expense' ? 'selected' : ''}>Expense</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Amount</label>
                        <input type="number" name="amount" class="form-control" value="${transaction.amount}" placeholder="Enter amount" required step="0.01">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Date</label>
                        <input type="date" name="date" class="form-control" value="${not empty transaction.date ? transaction.date : ''}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Category</label>
                        <select name="categoryId" class="form-select" required>
                            <option value="" disabled ${empty transaction.category ? 'selected' : ''}>Select category</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.id}" ${transaction.category.id == cat.id ? 'selected' : ''}>${cat.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Account</label>
                        <select name="accountId" class="form-select" required>
                            <option value="" disabled ${empty transaction.account ? 'selected' : ''}>Select account</option>
                            <c:forEach var="acc" items="${accounts}">
                                <option value="${acc.id}" ${transaction.account.id == acc.id ? 'selected' : ''}>${acc.name} (Balance: ${acc.balance})</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <input type="text" name="description" class="form-control" value="${transaction.description}" placeholder="Enter description (optional)">
                    </div>
                    <button type="submit" class="btn btn-primary">Save Transaction</button>
                    <a href="${pageContext.request.contextPath}/transactions" class="btn btn-secondary ms-2">Cancel</a>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>