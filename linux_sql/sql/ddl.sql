USE host_agent;
GO

CREATE TABLE IF NOT EXIST host_agent.'host_info'(id SERIAL NOT NULL,
hostname VARCHAR NOT NULL,
cpu_number INT NOT NULL,
cpu_architecture VARCHAR NOT NULL,
cpu_model VARCHAR NOT NULL,
cpu_mhz NUMERIC NOT NULL,
L2_cache VARCHAR NOT NULL,
total_mem INT NOT NULL,
timestamp TIMESTAMP WITH TIME ZONE NOT NULL);

CREATE TABLE IF NOT EXIST host_agent.'host_usage'("timestamp" TIMESTAMP NOT NULL,
host_id SERIAL NOT NULL,
memory_free VARCHAR NOT NULL,
cpu_idle INT NOT NULL,
cpu_kernel INT NOT NULL,
disk_io INT NOT NULL,
disk_available VARCHAR NOT NULL);