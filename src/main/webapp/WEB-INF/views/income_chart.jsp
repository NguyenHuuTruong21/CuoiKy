<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Income Chart - Personal Finance Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            background: #f8f9fa;
        }
        .chart-container {
            position: relative;
            height: 450px;
            width: 100%;
        }
        .card-custom {
            border-radius: 20px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.05);
        }
        .btn-back {
            border-radius: 30px;
            font-weight: 500;
        }
        h1.display-6 {
            font-weight: 700;
            color: #343a40;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 text-center mb-4">💰 Income Chart by Category</h1>

        <div class="card card-custom shadow-sm">
            <div class="card-body">
                <div class="chart-container">
                    <canvas id="incomeChart"></canvas>
                </div>
            </div>
        </div>

        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/reports" class="btn btn-secondary btn-back">
                <i class="fas fa-arrow-left"></i> Back to Reports
            </a>
        </div>
    </div>

    <script>
        const ctx = document.getElementById('incomeChart').getContext('2d');
        const labels = [<c:forEach var="entry" items="${incomeData}" varStatus="loop">'${entry.key}'${not loop.last ? ',' : ''}</c:forEach>];
        const data = [<c:forEach var="entry" items="${incomeData}" varStatus="loop">${entry.value}${not loop.last ? ',' : ''}</c:forEach>];

        const colorPalette = [
            '#36A2EB', '#4BC0C0', '#9966FF', '#FF9F40', '#81C784',
            '#FF6384', '#FFCE56', '#E57373', '#64B5F6', '#FFD54F'
        ];

        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Income by Category',
                    data: data,
                    backgroundColor: colorPalette,
                    borderColor: '#fff',
                    borderWidth: 2
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'bottom',
                        labels: {
                            font: { size: 14 },
                            color: '#333'
                        }
                    },
                    title: {
                        display: true,
                        text: 'Income Distribution by Category',
                        font: { size: 20 },
                        color: '#212529'
                    }
                }
            }
        });
    </script>

    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
