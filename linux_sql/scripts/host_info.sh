#!/bin/bash

#Setup arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#Parse the data
hostname=$(hostname)
lscpu_out=`lscpu`
mem_info=`cat /proc/meminfo`

cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model name:" | awk '{print $3,$4,$5,$6,$7}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(echo "$mem_info"  | egrep "^MemTotal:" | awk '{print $2, $3}' | xargs)
timestamp=`date -u "+%Y-%m-%d %H:%M:%S"`

#Insert Data into PSQL DB
insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, timestamp)
VALUES ('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model','$cpu_mhz', '$l2_cache', '$total_mem', '$timestamp');"

export PGPASSWORD='password'; psql -h localhost -U postgres -d host_agent -c "$insert_stmt"