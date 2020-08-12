--Question 1
SELECT cpu_number, host_id, total_mem  FROM host_info ORDER BY cpu_number, total_mem DESC;

--Question 2
--Insert some Sample Data
INSERT INTO host_usage ("timestamp", host_id, memory_free, cpu_idle, cpu_kernel, disk_io,
                        disk_available)
VALUES ('2020-08-06 15:00:00.000', 1, 300000, 90, 4, 2, 3),
       ('2020-08-06 15:01:00.000', 1, 200000, 80, 4, 2, 3),
       ('2020-08-06 15:02:13.000', 1, 200000, 83, 4, 2, 3),
       ('2020-08-06 15:01:13.000', 2, 300000, 88, 4, 2, 3),
       ('2020-08-06 15:02:24.000', 2, 200000, 76, 4, 2, 3),
       ('2020-08-06 15:04:41.000', 2, 200000, 52, 4, 2, 3),
       ('2020-08-06 15:06:07.000', 1, 300000, 89, 4, 2, 3),
       ('2020-08-06 15:07:39.000', 1, 200000, 76, 4, 2, 3),
       ('2020-08-06 15:09:14.000', 1, 200000, 82, 4, 2, 3),
       ('2020-08-06 15:06:13.000', 2, 300000, 84, 4, 2, 3),
       ('2020-08-06 15:08:24.000', 2, 200000, 71, 4, 2, 3),
       ('2020-08-06 15:09:41.000', 2, 200000, 59, 4, 2, 3);

--Query average free memory
SELECT host_id, date_trunc('hour', timestamp) + date_part('minute', timestamp):: int / 5 * interval '5 min' AS round_5,
AVG(cpu_idle)
FROM host_usage
GROUP BY host_id, date_trunc('hour', timestamp) + date_part('minute', timestamp):: int / 5 * interval '5 min';

--Question 3
SELECT host_id, date_trunc('hour', timestamp) + date_part('minute', timestamp):: int / 5 * interval '5 min' AS round_5,
HAVING(COUNT(cpu_idle)<3)
FROM host_usage
GROUP BY host_id, date_trunc('hour', timestamp) + date_part('minute', timestamp):: int / 5 * interval '5 min';
