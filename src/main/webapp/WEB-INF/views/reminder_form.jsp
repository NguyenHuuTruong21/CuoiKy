<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm tích luỹ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">Thêm tích luỹ</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <div class="card shadow-sm">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/reminder/add" method="post">
                    <div class="mb-3">
                        <label class="form-label">Tên hoá đơn</label>
                        <input type="text" name="billName" class="form-control" required/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Số tiền cần tích luỹ</label>
                        <input type="number" name="amount" step="0.01" class="form-control" required/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Ngày hẹn</label>
                        <input type="date" name="dueDate" class="form-control" required/>
                    </div>
                    <button type="submit" class="btn btn-primary">Thêm</button>
                    <a href="${pageContext.request.contextPath}/reminders" class="btn btn-secondary ms-2">Huỷ</a>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>