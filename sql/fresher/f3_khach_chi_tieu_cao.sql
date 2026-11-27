-- F3. Khách chi tiêu cao (xem đề). Viết MỘT câu SELECT bên dưới, kết thúc bằng dấu ;
SELECT c.full_name, c.email, 
        COUNT(b.id) as so_lan_dat,
        SUM(b.total_amount) as tong_chi_tieu
From customers c
JOIN bookings b on c.id = b.customer_id
WHERE b.status = 'CONFIRMED'
AND b.total_amount > 500000
GROUP BY c.id, c.full_name, c.email
ORDER BY tong_chi_tieu ASC, email DESC;
