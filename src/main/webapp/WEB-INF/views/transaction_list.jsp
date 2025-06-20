<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <h1 class="display-6 fw-bold mb-4">Danh sách ghi chép</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <a href="${pageContext.request.contextPath}/transaction/add" class="btn btn-success mb-3">Thêm ghi chép</a>
        <c:choose>
            <c:when test="${not empty transactions and not empty transactions}">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>Loại</th>
                                <th>Số tiền</th>
                                <th>Ngày nhập</th>
                                <th>Danh mục</th>
                                <th>Tài khoản</th>
                                <th>Nội dung</th>
                                <th>Tuỳ chọn</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="transaction" items="${transactions}" varStatus="loop">
                                <tr>
                                    <td>
                                        <span class="badge ${transaction.type == 'income' ? 'bg-success' : 'bg-danger'}">
    										<c:choose>
        										<c:when test="${transaction.type == 'income'}">Thu nhập</c:when>
        										<c:otherwise>Chi tiêu</c:otherwise>
   	 										</c:choose>
										</span>
                                    </td>
                                    <td>${transaction.amount}</td>
                                    <td>${transaction.date}</td>
                                    <td>${transaction.category.name}</td>
                                    <td>${transaction.account.name}</td>
                                    <td>${transaction.description}</td>
                                    <td>
                                        <%-- <a href="${pageContext.request.contextPath}/transaction/edit?id=${transaction.id}" class="btn btn-sm btn-primary">Edit</a> --%>
                                        <a href="${pageContext.request.contextPath}/transaction/delete?id=${transaction.id}" class="btn btn-sm btn-danger ms-2" onclick="return confirm('Bạn chắc chắn muốn xoá ?');">Xoá</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning" role="alert">
                    Không tìm thấy ghi chép
                </div>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary mt-3">Quay về trang chủ</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>