<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Báo cáo theo tháng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">Báo cáo chi tiết của ${period}</h1>
        <c:if test="${empty report or (report.income == 0 and report.expenses == 0)}">
            <div class="alert alert-warning" role="alert">
                Không thấy ghi chép cho tháng này
            </div>
        </c:if>
        <c:if test="${not empty report and (report.income != 0 or report.expenses != 0)}">
            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-4">
                            <p class="mb-1">Tổng thu nhập</p>
                            <h4 class="text-success">
                                <fmt:formatNumber value="${report.income}" type="currency" currencySymbol="$"/>
                            </h4>
                        </div>
                        <div class="col-md-4">
                            <p class="mb-1">Tổng chi tiêu</p>
                            <h4 class="text-danger">
                                <fmt:formatNumber value="${report.expenses}" type="currency" currencySymbol="$"/>
                            </h4>
                        </div>
                        
                    </div>
                </div>
            </div>
        </c:if>
        <a href="${pageContext.request.contextPath}/reports" class="btn btn-secondary mt-3">Quay về</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>