<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reminder List - Personal Finance Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">Reminder List</h1>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <h2 class="fw-semibold mb-3">Pending Reminders</h2>
        <c:if test="${not empty pendingReminders}">
            <div class="table-responsive">
                <table class="table table-bordered table-striped table-hover">
                    <thead class="table-warning">
                        <tr>
                            <th>Bill Name</th>
                            <th>Amount</th>
                            <th>Due Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="reminder" items="${pendingReminders}">
                            <tr class="table-warning">
                                <td>${reminder.billName}</td>
                                <td>${reminder.amount}</td>
                                <td>${reminder.dueDate}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/reminder/markPaid?id=${reminder.id}" class="btn btn-sm btn-success">Mark as Paid</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${empty pendingReminders}">
            <div class="alert alert-info">No pending reminders.</div>
        </c:if>

        <h2 class="fw-semibold mb-3 mt-4">All Reminders</h2>
        <a href="${pageContext.request.contextPath}/reminder/add" class="btn btn-success mb-3">Add New Reminder</a>
        <div class="table-responsive">
            <table class="table table-bordered table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Bill Name</th>
                        <th>Amount</th>
                        <th>Due Date</th>
                        <th>Paid</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reminder" items="${reminders}">
                        <tr>
                            <td>${reminder.id}</td>
                            <td>${reminder.billName}</td>
                            <td>${reminder.amount}</td>
                            <td>${reminder.dueDate}</td>
                            <td>
                                <span class="badge ${reminder.paid ? 'bg-success' : 'bg-danger'}">
                                    ${reminder.paid ? 'Yes' : 'No'}
                                </span>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/reminder/delete?id=${reminder.id}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this reminder?');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary mt-3">Back to Home</a>
    </div>

    <!-- Modal for Reminder Alerts -->
    <c:if test="${not empty pendingReminders}">
        <div class="modal fade" id="reminderModal" tabindex="-1" aria-labelledby="reminderModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-warning text-dark">
                        <h5 class="modal-title" id="reminderModalLabel">Pending Bill Reminders</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <c:forEach var="reminder" items="${pendingReminders}">
                            <div class="alert alert-warning mb-2">
                                <strong>${reminder.billName}</strong>: ${reminder.amount} due on ${reminder.dueDate}
                            </div>
                        </c:forEach>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
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