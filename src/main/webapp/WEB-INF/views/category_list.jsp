<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách danh mục</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">Danh sách danh mục</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <a href="${pageContext.request.contextPath}/category/add" class="btn btn-success mb-3">Thêm danh mục</a>
        <div class="table-responsive">
            <table class="table table-bordered table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <!-- <th>ID</th> -->
                        <th>Tên danh mục</th>
                        <th>Loại</th>
                        <th>Tuỳ chọn</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="category" items="${categories}">
                        <tr>                        
                            <td>${category.name}</td>
                            <td>
                                <span class="badge ${category.type == 'income' ? 'bg-success' : 'bg-danger'}">
        							<c:choose>
            							<c:when test="${category.type == 'income'}">Thu nhập</c:when>
            							<c:otherwise>Chi tiêu</c:otherwise>
        							</c:choose>
    							</span>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/category/delete?id=${category.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn chắc chắn muốn xoá danh mục này ?');">Xoá</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary mt-3">Quay về trang chủ</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>