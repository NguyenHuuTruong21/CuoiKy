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
            background-color: #dc3545;
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
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="text-center mb-5">
        <h1><i class="fas fa-wallet me-2"></i>Personal Finance Manager</h1>
        <p class="lead text-muted">Welcome, ${username}! Manage your transactions, categories, and budgets with ease.</p>
    </div>

    <div class="d-flex justify-content-center flex-wrap gap-3 mb-5">
        <a href="${pageContext.request.contextPath}/transactions" class="btn btn-primary"><i class="fas fa-list"></i> Transactions</a>
        <a href="${pageContext.request.contextPath}/transaction/add" class="btn btn-primary"><i class="fas fa-plus-circle"></i> Add Transaction</a>
        <a href="${pageContext.request.contextPath}/categories" class="btn btn-primary"><i class="fas fa-tags"></i> Categories</a>
        <a href="${pageContext.request.contextPath}/budgets" class="btn btn-primary"><i class="fas fa-chart-pie"></i> Budgets</a>
        <a href="${pageContext.request.contextPath}/accounts" class="btn btn-primary"><i class="fas fa-university"></i> Accounts</a>
        <a href="${pageContext.request.contextPath}/reports" class="btn btn-primary"><i class="fas fa-chart-line"></i> Reports</a>
        <a href="${pageContext.request.contextPath}/reminders" class="btn btn-primary"><i class="fas fa-bell"></i> Reminders</a>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger"><i class="fas fa-sign-out-alt"></i> Logout</a>
    </div>

    <div class="card card-overview shadow-sm">
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
                    <p>Remaining Budget</p>
                    <h5 class="${remainingBudget >= 0 ? 'text-success' : 'text-danger'}">${remainingBudget}</h5>
                </div>
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
