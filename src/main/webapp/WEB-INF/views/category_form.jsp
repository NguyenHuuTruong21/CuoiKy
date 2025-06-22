<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${mode == 'edit' ? 'Sửa danh mục' : 'Thêm danh mục'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to right, #e0f7fa, #f1f8e9);
            min-height: 100vh;
        }
        .card {
            border: none;
            border-radius: 1rem;
        }
        .form-label {
            font-weight: 500;
        }
    </style>
</head>
<body>
    <div class="container d-flex align-items-center justify-content-center" style="min-height: 100vh;">
        <div class="col-md-6">
            <div class="text-center mb-4">
                <h1 class="display-6 fw-bold text-primary">
                    <i class="bi bi-folder-plus"></i>
                    ${mode == 'edit' ? 'Sửa danh mục' : 'Thêm danh mục'}
                </h1>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <div class="card shadow p-4">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/category/${mode}" method="post">
                        <c:if test="${mode == 'edit'}">
                            <input type="hidden" name="id" value="${category.id}" />
                        </c:if>
                        <div class="mb-3">
                            <label class="form-label">Tên danh mục</label>
                            <input type="text" name="name" class="form-control" 
                                   value="${mode == 'edit' ? category.name : ''}" required/>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Loại</label>
                            <select name="type" class="form-select" required>
                                <option value="" disabled ${mode == 'add' ? 'selected' : ''}>Chọn</option>
                                <option value="income" ${mode == 'edit' && category.type == 'income' ? 'selected' : ''}>Thu nhập</option>
                                <option value="expense" ${mode == 'edit' && category.type == 'expense' ? 'selected' : ''}>Chi tiêu</option>
                            </select>
                        </div>
                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/categories" class="btn btn-outline-secondary">Huỷ</a>
                            <button type="submit" class="btn btn-success">
                                ${mode == 'edit' ? 'Cập nhật' : 'Thêm'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap & Icons -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
</body>
</html>
