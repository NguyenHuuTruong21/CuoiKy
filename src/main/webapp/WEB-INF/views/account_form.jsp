<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm tài khoản</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #f8f9fa, #e0f7fa);
            min-height: 100vh;
        }
        .form-container {
            max-width: 600px;
            margin: auto;
        }
        .card {
            border-radius: 16px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
        }
        .btn-primary {
            background-color: #0d6efd;
            border-radius: 8px;
        }
        .btn-secondary {
            border-radius: 8px;
        }
        h1 {
            color: #333;
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <div class="form-container">
            <h1 class="text-center display-6 fw-bold mb-4">🧾 Thêm tài khoản</h1>

            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <div class="card p-4">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/account/add" method="post">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Tên tài khoản</label>
                            <input type="text" name="name" class="form-control form-control-lg" placeholder="Tên" required />
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Số dư</label>
                            <input type="number" name="balance" step="0.01" class="form-control form-control-lg" placeholder="Số dư tài khoản" required />
                        </div>
                        <div class="d-flex justify-content-between mt-4">
                            <button type="submit" class="btn btn-primary px-4">Thêm</button>
                            <a href="${pageContext.request.contextPath}/accounts" class="btn btn-secondary px-4">Huỷ</a>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
