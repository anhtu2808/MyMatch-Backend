# Dashboard API Documentation

## Tổng quan
API Dashboard cung cấp các endpoint để lấy dữ liệu cho dashboard quản trị, bao gồm KPI cards, biểu đồ, và bảng dữ liệu với khả năng lọc và phân trang.

## Authentication
Tất cả các endpoint yêu cầu:
- JWT token hợp lệ
- Quyền `dashboard:read`

---

## 1. KPI Cards Endpoint

### `GET /api/dashboard/kpis`

**Mô tả**: Lấy các chỉ số KPI tổng quan cho dashboard

**Query Parameters**:
- `startDate` (optional): Ngày bắt đầu (ISO DateTime format)
- `endDate` (optional): Ngày kết thúc (ISO DateTime format)
- `universityId` (optional): Lọc theo trường đại học
- `campusId` (optional): Lọc theo cơ sở

**Response**:
```json
{
  "code": 200,
  "message": "Lấy KPI dashboard thành công",
  "result": {
    "totalUsers": 1000,
    "revenue": 50000000.0,
    "activeStudents": 850,
    "pendingActions": 25,
    "totalSwaps": 150,
    "totalTransactions": 2000
  }
}
```

**KPI Metrics**:
- `totalUsers`: Tổng số người dùng (có thể lọc theo date range, university, campus)
- `revenue`: Tổng doanh thu (VND) từ các payment đã hoàn thành trong khoảng thời gian
- `activeStudents`: Số sinh viên đang hoạt động (isActive = true)
- `pendingActions`: Tổng số hành động đang chờ xử lý (pending swaps + pending student requests + pending team requests)
- `totalSwaps`: Tổng số swap
- `totalTransactions`: Tổng số giao dịch

---

## 2. Chart Endpoints

### 2.1. Revenue Trend Chart

### `GET /api/dashboard/charts/revenue-trend`

**Mô tả**: Lấy dữ liệu xu hướng doanh thu theo thời gian

**Loại biểu đồ**: **Line Chart** hoặc **Area Chart**
- Trục X: Thời gian (date)
- Trục Y: Doanh thu (VND)
- Hiển thị xu hướng doanh thu theo ngày/tuần/tháng

**Query Parameters**:
- `startDate` (optional): Ngày bắt đầu
- `endDate` (optional): Ngày kết thúc
- `groupBy` (optional): Nhóm theo `DAY`, `WEEK`, hoặc `MONTH` (mặc định: `DAY`)
- `universityId` (optional): Lọc theo trường đại học
- `campusId` (optional): Lọc theo cơ sở

**Response**:
```json
{
  "code": 200,
  "message": "Lấy dữ liệu xu hướng doanh thu thành công",
  "result": [
    {
      "date": "2024-01-01T00:00:00",
      "revenue": 1000000.0
    },
    {
      "date": "2024-01-02T00:00:00",
      "revenue": 1500000.0
    }
  ]
}
```

**Gợi ý visualization**:
- Sử dụng Line Chart để hiển thị xu hướng
- Có thể thêm Area Chart để làm nổi bật vùng dưới đường
- Cho phép zoom/pan để xem chi tiết
- Hiển thị tooltip khi hover vào điểm dữ liệu

---

### 2.2. User Growth Chart

### `GET /api/dashboard/charts/user-growth`

**Mô tả**: Lấy dữ liệu tăng trưởng người dùng theo thời gian

**Loại biểu đồ**: **Line Chart** hoặc **Dual-Axis Line Chart**
- Trục X: Thời gian (date)
- Trục Y: Số lượng người dùng
- Hiển thị 2 đường:
  - `newUsers`: Số người dùng mới trong khoảng thời gian
  - `totalUsers`: Tổng số người dùng tích lũy (cumulative)

**Query Parameters**:
- `startDate` (optional): Ngày bắt đầu
- `endDate` (optional): Ngày kết thúc
- `groupBy` (optional): Nhóm theo `DAY`, `WEEK`, hoặc `MONTH` (mặc định: `DAY`)
- `universityId` (optional): Lọc theo trường đại học
- `campusId` (optional): Lọc theo cơ sở

**Response**:
```json
{
  "code": 200,
  "message": "Lấy dữ liệu tăng trưởng người dùng thành công",
  "result": [
    {
      "date": "2024-01-01T00:00:00",
      "newUsers": 10,
      "totalUsers": 1000
    },
    {
      "date": "2024-01-02T00:00:00",
      "newUsers": 15,
      "totalUsers": 1015
    }
  ]
}
```

**Gợi ý visualization**:
- Sử dụng Line Chart với 2 đường line
- Màu khác nhau cho `newUsers` (ví dụ: xanh dương) và `totalUsers` (ví dụ: xanh lá)
- Có thể sử dụng Bar Chart kết hợp với Line Chart (bar cho newUsers, line cho totalUsers)
- Hiển thị legend để phân biệt 2 metrics

---

### 2.3. Swap Success Rate Chart

### `GET /api/dashboard/charts/swap-success-rate`

**Mô tả**: Lấy dữ liệu tỷ lệ thành công của swap

**Loại biểu đồ**: **Pie Chart** hoặc **Donut Chart** hoặc **Bar Chart**
- Hiển thị phân bổ trạng thái swap: Approved, Rejected, Pending
- Hiển thị tỷ lệ thành công (success rate) dưới dạng percentage

**Query Parameters**:
- `startDate` (optional): Ngày bắt đầu
- `endDate` (optional): Ngày kết thúc
- `universityId` (optional): Lọc theo trường đại học
- `campusId` (optional): Lọc theo cơ sở

**Response**:
```json
{
  "code": 200,
  "message": "Lấy dữ liệu tỷ lệ thành công swap thành công",
  "result": {
    "totalSwaps": 150,
    "approvedSwaps": 100,
    "rejectedSwaps": 30,
    "pendingSwaps": 20,
    "successRate": 66.67
  }
}
```

**Gợi ý visualization**:
- **Pie Chart**: Hiển thị phân bổ trạng thái (Approved, Rejected, Pending)
- **Donut Chart**: Tương tự Pie Chart nhưng có lỗ ở giữa, có thể hiển thị success rate ở giữa
- **Bar Chart**: Hiển thị số lượng từng trạng thái
- Hiển thị success rate dưới dạng số lớn, nổi bật (ví dụ: 66.67%)
- Màu sắc gợi ý:
  - Approved: Xanh lá (#10B981)
  - Rejected: Đỏ (#EF4444)
  - Pending: Vàng (#F59E0B)

---

## 3. Data Tables Endpoints

### 3.1. Users Table

### `GET /api/dashboard/tables/users`

**Mô tả**: Lấy danh sách người dùng với pagination và filtering

**Loại hiển thị**: **Data Table** với pagination, sorting, filtering

**Query Parameters**:
- `startDate` (optional): Lọc theo ngày tạo
- `endDate` (optional): Lọc theo ngày tạo
- `universityId` (optional): Lọc theo trường đại học
- `campusId` (optional): Lọc theo cơ sở
- `isActive` (optional): Lọc theo trạng thái hoạt động
- `roleType` (optional): Lọc theo loại role (STUDENT, MANAGER)
- `page` (default: 1): Số trang
- `size` (default: 10): Số item mỗi trang
- `sortBy` (default: "id"): Trường để sort
- `sortDirection` (default: "DESC"): Hướng sort (ASC/DESC)

**Response**: PageResponse với danh sách UserDashboardResponse

---

### 3.2. Students Table

### `GET /api/dashboard/tables/students`

**Mô tả**: Lấy danh sách sinh viên với pagination và filtering

**Loại hiển thị**: **Data Table** với pagination, sorting, filtering

**Query Parameters**:
- `startDate` (optional): Lọc theo ngày tạo
- `endDate` (optional): Lọc theo ngày tạo
- `universityId` (optional): Lọc theo trường đại học
- `campusId` (optional): Lọc theo cơ sở
- `isActive` (optional): Lọc theo trạng thái hoạt động
- `page` (default: 1): Số trang
- `size` (default: 10): Số item mỗi trang
- `sortBy` (default: "id"): Trường để sort
- `sortDirection` (default: "DESC"): Hướng sort (ASC/DESC)

**Response**: PageResponse với danh sách StudentDashboardResponse

---

### 3.3. Swaps Table

### `GET /api/dashboard/tables/swaps`

**Mô tả**: Lấy danh sách swap với pagination và filtering

**Loại hiển thị**: **Data Table** với pagination, sorting, filtering

**Query Parameters**:
- `startDate` (optional): Lọc theo ngày tạo
- `endDate` (optional): Lọc theo ngày tạo
- `universityId` (optional): Lọc theo trường đại học
- `campusId` (optional): Lọc theo cơ sở
- `status` (optional): Lọc theo trạng thái swap (PENDING, APPROVED, REJECTED, CANCELLED)
- `page` (default: 1): Số trang
- `size` (default: 10): Số item mỗi trang
- `sortBy` (default: "id"): Trường để sort
- `sortDirection` (default: "DESC"): Hướng sort (ASC/DESC)

**Response**: PageResponse với danh sách SwapDashboardResponse

---

### 3.4. Transactions Table

### `GET /api/dashboard/tables/transactions`

**Mô tả**: Lấy danh sách giao dịch với pagination và filtering

**Loại hiển thị**: **Data Table** với pagination, sorting, filtering

**Query Parameters**:
- `startDate` (optional): Lọc theo ngày tạo
- `endDate` (optional): Lọc theo ngày tạo
- `universityId` (optional): Lọc theo trường đại học
- `campusId` (optional): Lọc theo cơ sở
- `type` (optional): Lọc theo loại (IN, OUT)
- `status` (optional): Lọc theo trạng thái (PENDING, COMPLETED, FAILED, CANCELLED)
- `source` (optional): Lọc theo nguồn (TOP_UP, SERVICE_PURCHASE, REWARD, REFUND, ADMIN_ADJUSTMENT)
- `page` (default: 1): Số trang
- `size` (default: 10): Số item mỗi trang
- `sortBy` (default: "id"): Trường để sort
- `sortDirection` (default: "DESC"): Hướng sort (ASC/DESC)

**Response**: PageResponse với danh sách TransactionDashboardResponse

---

## 4. Filter Options Endpoint

### `GET /api/dashboard/filters/options`

**Mô tả**: Lấy danh sách các options cho filter dropdowns

**Query Parameters**:
- `universityId` (optional): Nếu có, chỉ trả về campuses của university đó

**Response**:
```json
{
  "code": 200,
  "message": "Lấy danh sách filter options thành công",
  "result": {
    "universities": [
      {
        "id": 1,
        "name": "Đại học ABC"
      }
    ],
    "campuses": [
      {
        "id": 1,
        "name": "Cơ sở Hà Nội",
        "universityId": 1,
        "universityName": "Đại học ABC"
      }
    ]
  }
}
```

---

## Tóm tắt loại biểu đồ

| Endpoint | Loại biểu đồ | Mô tả |
|----------|--------------|-------|
| `/charts/revenue-trend` | **Line Chart / Area Chart** | Xu hướng doanh thu theo thời gian |
| `/charts/user-growth` | **Line Chart (Dual-line)** | Tăng trưởng người dùng (new users + total users) |
| `/charts/swap-success-rate` | **Pie Chart / Donut Chart / Bar Chart** | Phân bổ trạng thái swap và tỷ lệ thành công |
| `/tables/*` | **Data Table** | Bảng dữ liệu với pagination, sorting, filtering |

---

## Gợi ý thư viện visualization

- **Chart.js**: Dễ sử dụng, hỗ trợ nhiều loại chart
- **Recharts**: React chart library, rất phù hợp cho dashboard
- **ApexCharts**: Mạnh mẽ, nhiều tùy chọn customization
- **D3.js**: Linh hoạt cao nhưng phức tạp hơn

---

## Lưu ý

1. Tất cả date parameters sử dụng ISO DateTime format: `2024-01-01T00:00:00`
2. Revenue được tính bằng VND (Double)
3. Date grouping (groupBy) chỉ áp dụng cho chart endpoints
4. Pagination bắt đầu từ page 1 (không phải 0)
5. Sort direction mặc định là DESC

