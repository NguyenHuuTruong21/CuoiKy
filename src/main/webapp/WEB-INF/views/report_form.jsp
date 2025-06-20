<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Report - Personal Finance Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <style>
        body {
            background-color: #f8f9fa;
        }
        .card {
            border-radius: 20px;
        }
        .card-title i {
            margin-right: 8px;
            color: #0d6efd;
        }
        .btn {
            border-radius: 30px;
            font-weight: 500;
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <h1 class="display-5 fw-bold text-center mb-5 text-primary">📊 Báo cáo</h1>

        <div class="row g-4">
            <!-- Monthly Report -->
            <div class="col-md-6">
                <div class="card shadow-sm p-3 h-100">
                    <div class="card-body">
                        <h2 class="card-title fw-semibold mb-4"><i class="fas fa-calendar-alt"></i>Báo cáo theo từng tháng</h2>
                        <form action="${pageContext.request.contextPath}/reports/monthly" method="post">
                            <div class="mb-3">
                                <label class="form-label">Năm</label>
                                <input type="number" name="year" class="form-control" value="2025" placeholder="Enter year (e.g., 2025)" required/>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Tháng</label>
                                <select name="month" class="form-select" required>
                                    <option value="" disabled>Chọn tháng</option>
                                    <option value="1">Tháng 1</option>
                                    <option value="2">Tháng 2</option>
                                    <option value="3">Tháng 3</option>
                                    <option value="4">Tháng 4</option>
                                    <option value="5">Tháng 5</option>
                                    <option value="6">Tháng 6</option>
                                    <option value="7">Tháng 7</option>
                                    <option value="8">Tháng 8</option>
                                    <option value="9">Tháng 9</option>
                                    <option value="10">Tháng 10</option>
                                    <option value="11">Tháng 11</option>
                                    <option value="12">Tháng 12</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary w-100 mt-2">
                                <i class="fas fa-file-alt"></i>Lọc
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Yearly Report -->
            <div class="col-md-6">
                <div class="card shadow-sm p-3 h-100">
                    <div class="card-body">
                        <h2 class="card-title fw-semibold mb-4"><i class="fas fa-calendar"></i>Báo cáo theo năm</h2>
                        <form action="${pageContext.request.contextPath}/reports/yearly" method="post">
                            <div class="mb-3">
                                <label class="form-label">Năm</label>
                                <input type="number" name="year" class="form-control" value="2025" placeholder="Enter year (e.g., 2025)" required/>
                            </div>
                            <button type="submit" class="btn btn-success w-100 mt-2">
                                <i class="fas fa-file-alt"></i>Lọc
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Expense Chart -->
            <div class="col-md-12">
                <div class="card shadow-sm p-4 text-center">
                    <h2 class="card-title fw-semibold mb-3"><i class="fas fa-chart-pie"></i>Chi tiêu</h2>
                    <a href="${pageContext.request.contextPath}/reports/expense-chart" class="btn btn-info text-white px-4">
                        <i class="fas fa-eye"></i>Xem biểu đồ chi tiêu
                    </a>
                </div>
            </div>
            
            <!-- Income Chart -->
			<div class="col-md-12">
    			<div class="card shadow-sm p-4 text-center">
        			<h2 class="card-title fw-semibold mb-3"><i class="fas fa-chart-bar"></i>Thu nhập</h2>
        			<a href="${pageContext.request.contextPath}/reports/income-chart" class="btn btn-success text-white px-4">
            			<i class="fas fa-eye"></i>Xem biểu đồ thu nhập
        			</a>
    			</div>
			</div>
        </div>

        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                <i class="fas fa-home"></i> Quay về trang chủ
            </a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
