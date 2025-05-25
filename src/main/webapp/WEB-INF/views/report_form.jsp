<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Report - Personal Finance Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h1 class="display-6 fw-bold mb-4">Generate Report</h1>
        <div class="row">
            <div class="col-md-6 mb-4">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h2 class="card-title fw-semibold">Monthly Report</h2>
                        <form action="${pageContext.request.contextPath}/reports/monthly" method="post">
                            <div class="mb-3">
                                <label class="form-label">Year</label>
                                <input type="number" name="year" class="form-control" placeholder="Enter year (e.g., 2025)" required/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Month</label>
                                <select name="month" class="form-select" required>
                                    <option value="" disabled selected>Select month</option>
                                    <option value="1">January</option>
                                    <option value="2">February</option>
                                    <option value="3">March</option>
                                    <option value="4">April</option>
                                    <option value="5">May</option>
                                    <option value="6">June</option>
                                    <option value="7">July</option>
                                    <option value="8">August</option>
                                    <option value="9">September</option>
                                    <option value="10">October</option>
                                    <option value="11">November</option>
                                    <option value="12">December</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Generate Monthly Report</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-6 mb-4">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h2 class="card-title fw-semibold">Yearly Report</h2>
                        <form action="${pageContext.request.contextPath}/reports/yearly" method="post">
                            <div class="mb-3">
                                <label class="form-label">Year</label>
                                <input type="number" name="year" class="form-control" placeholder="Enter year (e.g., 2025)" required/>
                            </div>
                            <button type="submit" class="btn btn-primary">Generate Yearly Report</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="card shadow-sm mb-4">
            <div class="card-body text-center">
                <h2 class="card-title fw-semibold">Expense Chart</h2>
                <a href="${pageContext.request.contextPath}/reports/expense-chart" class="btn btn-info">View Expense Chart by Category</a>
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary mt-3">Back to Home</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>