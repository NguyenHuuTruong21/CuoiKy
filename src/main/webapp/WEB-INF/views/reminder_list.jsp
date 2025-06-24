<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách tích luỹ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <style>
        .nav-scroll {
            white-space: nowrap;
            overflow-x: auto;
        }
        .nav-scroll .btn {
            border-radius: 30px;
            margin-right: 0.5rem;
        }
        .badge {
            font-size: 0.9rem;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-4">
        <h1 class="display-6 fw-bold mb-4 text-center text-primary">Tích luỹ</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <!-- Pending Reminders -->
        <div class="card border-warning mb-4 shadow-sm">
            <div class="card-header bg-warning text-dark fw-bold">
                <i class="fas fa-hourglass-half"></i>Danh sách đang đang chờ tích luỹ
            </div>
            <div class="card-body">
                <c:if test="${not empty pendingReminders}">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead class="table-warning">
                                <tr>
                                    <th>Tích luỹ cho việc gì</th>
                                    <th>Số tiền cần tích luỹ</th>
                                    <th>Hẹn vào ngày</th>
                                    <th>Xác nhận</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="reminder" items="${pendingReminders}">
                                    <tr>
                                        <td>${reminder.billName}</td>
                                        <td>${reminder.amount}</td>
                                        <td>${reminder.dueDate}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/reminder/markPaid?id=${reminder.id}" class="btn btn-sm btn-success">
                                                <i class="fas fa-check-circle"></i> Đánh dấu đã hoàn thành
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <c:if test="${empty pendingReminders}">
                    <div class="alert alert-info">Không tìm thấy tích luỹ nào</div>
                </c:if>
            </div>
        </div>

        <!-- All Reminders -->
        <div class="card border-secondary shadow-sm">
            <div class="card-header bg-dark text-white fw-bold d-flex justify-content-between align-items-center">
                <span><i class="fas fa-clipboard-list"></i> Danh sách tích luỹ đang chờ xác nhận</span>
                <a href="${pageContext.request.contextPath}/reminder/add" class="btn btn-sm btn-light">
                    <i class="fas fa-plus"></i>Thêm tích luỹ
                </a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle">
                        <thead class="table-dark text-white">
                            <tr>
                                <th>Tích luỹ cho việc gì</th>
                                <th>Số tiền cần tích luỹ</th>
                                <th>Hẹn vào ngày</th>
                                <th>Trạng thái</th>
                                <th>Tuỳ chọn</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="reminder" items="${reminders}">
                                <tr>
                                    <td>${reminder.billName}</td>
                                    <td>${reminder.amount}</td>
                                    <td>${reminder.dueDate}</td>
                                    <td>
                                        <span class="badge ${reminder.paid ? 'bg-success' : 'bg-danger'}">
                                            ${reminder.paid ? 'Đã hoàn thành' : 'Chưa hoàn thành'}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/reminder/delete?id=${reminder.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn chắc chắn muốn xoá tích luỹ này ?');">
                                            <i class="fas fa-trash-alt"></i> Xoá
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Quay về trang chủ
            </a>
        </div>
    </div>



    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
