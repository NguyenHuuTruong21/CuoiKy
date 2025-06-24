<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách ngân sách</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">Ngân sách</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <a href="${pageContext.request.contextPath}/budget/add" class="btn btn-success mb-3">Thêm ngân sách mới</a>
        <c:choose>
            <c:when test="${not empty budgets}">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>Danh mục</th>
                                <th>Số tiền</th>                                
                                <th>Đã dùng (%)</th>
                                <th>Tuỳ chọn</th>
                            </tr>
                        </thead>
                        
                        <tbody>
                            <c:forEach var="budget" items="${budgets}">
                                <tr>
                                    <td>${budget.category.name}</td>
                                    <td>${budget.amount}</td>
                                    <td>
                                    	<c:out value="${percentUsedMap[budget.id]}" />%
            							<c:if test="${percentUsedMap[budget.id] > 100}">
                							<span style="color:red;">Đã vượt ngân sách!</span>
            							</c:if>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/budget/delete?id=${budget.id}" 
                                        class="btn btn-sm btn-danger" onclick="return confirm('Bạn chắc chắn muốn xoá ngân sách này ?');">Xoá</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning" role="alert">
                    Không có ngân sách nào
                </div>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary mt-3">Quay về trang chủ</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>