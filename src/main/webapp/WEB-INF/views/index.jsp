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
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index"><i class="fas fa-wallet me-2"></i>Finance Manager</a>
        <div class="ms-auto d-flex align-items-center">
            <span class="me-3 text-muted">Hello, ${username}!</span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger btn-sm">
                <i class="fas fa-sign-out-alt"></i> Logout
            </a>
        </div>
    </div>
</nav>

<div class="container mt-5 pt-5">
    <!-- Navigation bar -->
    <div class="bg-white p-3 rounded shadow-sm mb-4 overflow-auto" style="white-space: nowrap;">
        <div class="d-inline-flex gap-2">
            <a href="${pageContext.request.contextPath}/transaction/add" class="btn btn-outline-success">
                <i class="fas fa-plus-circle"></i> Add Transaction
            </a>
            <a href="${pageContext.request.contextPath}/categories" class="btn btn-outline-warning">
                <i class="fas fa-tags"></i> Categories
            </a>
            <a href="${pageContext.request.contextPath}/budgets" class="btn btn-outline-info">
                <i class="fas fa-chart-pie"></i> Budgets
            </a>
            <a href="${pageContext.request.contextPath}/accounts" class="btn btn-outline-secondary">
                <i class="fas fa-university"></i> Accounts
            </a>
            <a href="${pageContext.request.contextPath}/reports" class="btn btn-outline-dark">
                <i class="fas fa-chart-line"></i> Reports
            </a>
            <a href="${pageContext.request.contextPath}/reminders" class="btn btn-outline-danger">
                <i class="fas fa-bell"></i> Reminders
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
    
    <!-- Filter Form for Transactions -->
	<div class="filter-form">
	    <h5>Filter Transactions</h5>
	    <form action="${pageContext.request.contextPath}/" method="get" class="row g-3">
	        <div class="col-auto">
	            <label for="year" class="visually-hidden">Year</label>
	            <input type="number" name="year" id="year" class="form-control" placeholder="Enter Year (2020-2025)" 
	                   value="${param.year}" min="2020" max="2090">
	        </div>
	        <div class="col-auto">
	            <label for="month" class="visually-hidden">Month</label>
	            <input type="number" name="month" id="month" class="form-control" placeholder="Enter Month (1-12)" 
	                   value="${param.month}" min="1" max="12">
	        </div>
	        <div class="col-auto">
	            <button type="submit" class="btn btn-primary">Filter</button>
	        </div>
	    </form>
	</div>

    <!-- Transaction List -->
    <div class="section-card">
        <h4><i class="fas fa-list"></i> Recent Transactions</h4>
        <c:if test="${not empty transactions and not empty transactions}">
            <div class="table-responsive">
                <table class="table table-bordered table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>Type</th>
                            <th>Amount</th>
                            <th>Date</th>
                            <th>Category</th>
                            <th>Account</th>
                            <th>Description</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="transaction" items="${transactions}" varStatus="loop">
                            <tr>
                                <td>
                                    <span class="badge ${transaction.type == 'income' ? 'bg-success' : 'bg-danger'}">
                                        ${transaction.type}
                                    </span>
                                </td>
                                <td>${transaction.amount}</td>
                                <td>${transaction.date}</td>
                                <td>${transaction.category.name}</td>
                                <td>${transaction.account.name}</td>
                                <td>${transaction.description}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/transaction/delete?id=${transaction.id}" class="btn btn-sm btn-danger ms-2" onclick="return confirm('Are you sure you want to delete this transaction?');">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${empty transactions}">
            <div class="alert alert-warning" role="alert">
                No transactions found.
            </div>
        </c:if>
    </div>

	<!-- Quick Overview -->
    <div class="card card-overview shadow-sm mb-4">
        <div class="card-body">
            <h4 class="card-title mb-4"><i class="fas fa-chart-bar"></i> Quick Overview</h4>
            <div class="row text-center">
                <div class="col-md-4 mb-3">
                    <div class="overview-icon"><i class="fas fa-arrow-down"></i></div>
                    <p>Total Income</p>
                    <h5 class="text-success">${totalIncome}</h5>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="overview-icon"><i class="fas fa-arrow-up"></i></div>
                    <p>Total Expenses</p>
                    <h5 class="text-danger">${totalExpenses}</h5>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="overview-icon"><i class="fas fa-wallet"></i></div>
                    <p>Total Budget Goals</p>
                    <h5 class="${remainingBudget >= 0 ? 'text-success' : 'text-danger'}">${remainingBudget}</h5>
                </div>
            </div>
        </div>
    </div>   

    <!-- Modal for Reminder Alerts -->
    <c:if test="${not empty pendingReminders}">
        <div class="modal fade" id="reminderModal" tabindex="-1" aria-labelledby="reminderModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-warning-subtle">
                        <h5 class="modal-title" id="reminderModalLabel"><i class="fas fa-exclamation-circle me-2"></i>Pending Bill Reminders</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <c:forEach var="reminder" items="${pendingReminders}">
                            <div class="alert alert-warning" role="alert">
                                <strong>${reminder.billName}</strong>: ${reminder.amount} due on ${reminder.dueDate}
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
                var reminderModal = new bootstrap.Modal(document.getElementById('reminderModal'));
                reminderModal.show();
            });
        </script>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>