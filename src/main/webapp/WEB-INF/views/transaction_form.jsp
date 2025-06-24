<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${mode == 'edit' ? "Sửa" : "Thêm"} Giao dịch</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">
            <c:choose>
                <c:when test="${mode == 'edit'}">Sửa giao dịch</c:when>
                <c:otherwise>Thêm ghi chép</c:otherwise>
            </c:choose>
        </h1>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <div class="card shadow-sm">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}${mode == 'edit' ? '/transaction/update' : '/transaction/add'}" method="post">
                    <c:if test="${mode == 'edit'}">
                        <input type="hidden" name="id" value="${transaction.id}" />
                    </c:if>
                    <div class="mb-3">
                        <label class="form-label">Loại</label>
                        <select name="type" class="form-select" required>
                            <option value="" disabled ${empty transaction.type ? 'selected' : ''}>Chọn</option>
                            <option value="income" ${transaction.type == 'income' ? 'selected' : ''}>Thu nhập</option>
                            <option value="expense" ${transaction.type == 'expense' ? 'selected' : ''}>Chi tiêu</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Số tiền</label>
                        <input type="number" name="amount" class="form-control" value="${transaction.amount}" placeholder="Nhập số tiền" required step="0.01">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Ngày nhập</label>
                        <input type="date" name="date" class="form-control" value="${not empty transaction.date ? transaction.date : ''}" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Danh mục</label>
                        <select name="categoryId" class="form-select" required>
                            <option value="" disabled ${empty transaction.category ? 'selected' : ''}>Chọn danh mục</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.id}" ${transaction.category.id == cat.id ? 'selected' : ''}>${cat.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Tài khoản</label>
                        <select name="accountId" class="form-select" required>
                            <option value="" disabled ${empty transaction.account ? 'selected' : ''}>Chọn tài khoản</option>
                            <c:forEach var="acc" items="${accounts}">
                                <option value="${acc.id}" ${transaction.account.id == acc.id ? 'selected' : ''}>${acc.name} (Balance: ${acc.balance})</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Nội dung</label>
                        <input type="text" name="description" class="form-control" value="${transaction.description}" placeholder="Nhập nội dung(tuỳ chọn)">
                    </div>
                    <button type="submit" class="btn btn-primary">
                        ${mode == 'edit' ? "Cập nhật" : "Lưu"}
                    </button>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary ms-2">Huỷ</a>
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
