<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Personal Finance Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        h1 {
            font-weight: bold;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-danger {
            border: none;
        }
        .card-overview {
            background: linear-gradient(135deg, #f5f7fa, #c3cfe2);
            border: none;
        }
        .overview-icon {
            font-size: 2rem;
            color: #007bff;
            margin-right: 10px;
        }
        .btn {
            border-radius: 30px;
            font-weight: 500;
        }
        .btn i {
            margin-right: 5px;
        }
        .navbar-brand {
            font-size: 1.5rem;
            font-weight: bold;
        }
        .navbar {
            background-color: #ffffff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .budget-card {
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
        }
        .section-card {
            margin-top: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .filter-form {
            margin-bottom: 20px;
            padding: 15px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .table-responsive {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg fixed-top">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/"><i class="fas fa-wallet me-2"></i>Quản lý chi tiêu</a>
        <div class="ms-auto d-flex align-items-center">
            <span class="me-3 text-muted">Xin chào, ${username}!</span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger btn-sm">
                <i class="fas fa-sign-out-alt"></i> Đăng xuất
            </a>
        </div>
    </div>
</nav>

<div class="container mt-5 pt-5">
    <!-- Navigation bar -->
    <div class="bg-white p-3 rounded shadow-sm mb-4 overflow-auto" style="white-space: nowrap;">
        <div class="d-inline-flex gap-2">
            <a href="${pageContext.request.contextPath}/transaction/add" class="btn btn-outline-success">
                <i class="fas fa-plus-circle"></i>Thêm ghi chép
            </a>
            <a href="${pageContext.request.contextPath}/categories" class="btn btn-outline-warning">
                <i class="fas fa-tags"></i>Danh mục
            </a>
            <a href="${pageContext.request.contextPath}/budgets" class="btn btn-outline-info">
                <i class="fas fa-chart-pie"></i>Ngân sách
            </a>
            <a href="${pageContext.request.contextPath}/accounts" class="btn btn-outline-secondary">
                <i class="fas fa-university"></i>Tài khoản
            </a>
            <a href="${pageContext.request.contextPath}/reports" class="btn btn-outline-dark">
                <i class="fas fa-chart-line"></i>Báo cáo
            </a>
            <a href="${pageContext.request.contextPath}/reminders" class="btn btn-outline-danger">
                <i class="fas fa-bell"></i>Tích luỹ
            </a>
        </div>
    </div>

    <%-- <!-- Filter Form for Transactions -->
    <div class="filter-form">
        <h5>Filter Transactions</h5>
        <form action="${pageContext.request.contextPath}/" method="get" class="row g-3">
            <div class="col-auto">
                <label for="year" class="visually-hidden">Year</label>
                <select name="year" id="year" class="form-select">
                    <option value="">All Years</option>
                    <c:forEach var="year" begin="2020" end="2025">
                        <option value="${year}" ${param.year == year ? 'selected' : ''}>${year}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-auto">
                <label for="month" class="visually-hidden">Month</label>
                <select name="month" id="month" class="form-select">
                    <option value="">All Months</option>
                    <c:forEach var="m" begin="1" end="12">
                        <option value="${m}" ${param.month == m ? 'selected' : ''}>${m}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary">Filter</button>
            </div>
        </form>
    </div> --%>
    
    <%
    int currentYear = java.time.Year.now().getValue();
    int minYear = 1970;
    int maxYear = 3000;
    String selectedYear = request.getParameter("year");
    String selectedMonth = request.getParameter("month");
	%>
<div class="filter-form">
<form action="${pageContext.request.contextPath}/" method="get" class="row g-3">
	<div class="col-auto">
        <label for="month" class="visually-hidden">Month</label>
        <select name="month" id="month" class="form-select" style="min-width: 110px;">
            <option value="">All Months</option>
            <%
                for (int m = 1; m <= 12; m++) {
                    boolean isSelected = false;
                    if (selectedMonth != null && !selectedMonth.isEmpty()) {
                        isSelected = Integer.parseInt(selectedMonth) == m;
                    } else {
                        // Nếu không chọn thì ưu tiên tháng hiện tại
                        isSelected = m == java.time.LocalDate.now().getMonthValue();
                    }
            %>
                <option value="<%=m%>" <%=isSelected ? "selected" : ""%>><%=m%></option>
            <%
                }
            %>
        </select>
    </div>
    
    <div class="col-auto">
        <label for="year" class="visually-hidden">Year</label>
        <select name="year" id="year" class="form-select" style="min-width: 130px;">
            <option value="">All Years</option>
            <%
                for (int y = minYear; y <= maxYear; y++) {
                    boolean isSelected = false;
                    if (selectedYear != null && !selectedYear.isEmpty()) {
                        isSelected = Integer.parseInt(selectedYear) == y;
                    } else {
                        isSelected = y == currentYear;
                    }
            %>
                <option value="<%=y%>" <%=isSelected ? "selected" : ""%>><%=y%></option>
            <%
                }
            %>
        </select>
    </div>
    <div class="col-auto">
        <button type="submit" class="btn btn-primary">Lọc</button>
    </div>
</form>
</div>

    <!-- Transaction List -->
    <div class="section-card">
        <h4><i class="fas fa-list"></i> Ghi chép gần đây</h4>
        <c:if test="${not empty transactions and not empty transactions}">
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
                                	<a href="${pageContext.request.contextPath}/transaction/edit?id=${transaction.id}" class="btn btn-sm btn-warning ms-2">Sửa</a>
                                    <a href="${pageContext.request.contextPath}/transaction/delete?id=${transaction.id}" class="btn btn-sm btn-danger ms-2" onclick="return confirm('Bạn chắc chắn xoá ghi chép này ?');">Xoá</a>   	
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${empty transactions}">
            <div class="alert alert-warning" role="alert">
                Không tìm thấy ghi chép
            </div>
        </c:if>
    </div>

	<!-- Quick Overview -->
    <div class="card card-overview shadow-sm mb-4">
        <div class="card-body">     
            <div class="row text-center">
                <div class="col-md-4 mb-3">
                    <div class="overview-icon"><i class="fas fa-arrow-down"></i></div>
                    <p>Thu nhập</p>
                    <h5 class="text-success">${totalIncome}</h5>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="overview-icon"><i class="fas fa-arrow-up"></i></div>
                    <p>Chi tiêu</p>
                    <h5 class="text-danger">${totalExpenses}</h5>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="overview-icon"><i class="fas fa-wallet"></i></div>
                    <p>Còn thừa</p>
                    <h5 class="${remainingBudget >= 0 ? 'text-success' : 'text-danger'}">${remainingBudget}</h5>
                </div>
            </div>
        </div>
    </div>  
    
    <!-- Modal Mục tiêu đã đạt -->
	<c:if test="${not empty achievedReminders}">
    	<div class="modal fade" id="achievedModal" tabindex="-1" aria-labelledby="achievedModalLabel" aria-hidden="true">
        	<div class="modal-dialog">
            	<div class="modal-content">
                	<div class="modal-header bg-success-subtle">
                    	<h5 class="modal-title" id="achievedModalLabel">
                        	<i class="fas fa-trophy"></i> Mục tiêu đã đạt!
                    	</h5>
                    	<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                	</div>
                	<div class="modal-body">
                    	<c:forEach var="reminder" items="${achievedReminders}">
                        	<div class="alert alert-success shadow-sm fw-semibold mb-2">
                            	🎯 <b>${reminder.billName}</b>
                            	– Số tiền: <span class="text-danger">${reminder.amount}</span>
                            	(Đã đủ vào ngày: <b>${reminder.dueDate}</b>)
                        	</div>
                    	</c:forEach>
                	</div>
                	<div class="modal-footer">
                    	<a href="${pageContext.request.contextPath}/reminders" class="btn btn-primary">View All Reminders</a>
                	</div>
            	</div>
        	</div>
    	</div>
    	<script>
        	document.addEventListener('DOMContentLoaded', function () {
            	var achievedModal = new bootstrap.Modal(document.getElementById('achievedModal'));
            	achievedModal.show();
        	});
    	</script>
	</c:if>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>