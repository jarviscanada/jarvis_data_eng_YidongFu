#!/bin/bash

#Setup arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#Parse the data
mem_info=`cat /proc/meminfo`

memory_free=$(echo "$mem_info"  | egrep "^MemFree:" | awk '{print $2, $3}' | xargs)
cpu_idle=$(vmstat | awk '{ for (i=1; i<=NF; i++) if ($i=="id") { getline; print $i }}')
cpu_kernel=$(vmstat| awk '{ for (i=1; i<=NF; i++) if ($i=="sy") { getline; print $i }}')
disk_io=$(vmstat -d| awk '{ for (i=1; i<=NF; i++) if ($i=="sec") { getline; print $11 }}')
disk_available=$(df -BM /|awk '{ for (i=1; i<=NF; i++) if ($i=="Available") { getline; print $4 }}')
timestamp=`date -u "+%Y-%m-%d %H:%M:%S"`

#Insert Data into PSQL DB
insert_stmt="INSERT INTO host_usage ("timestamp", memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
VALUES ('$timestamp','$memory_free', '$cpu_idle', '$cpu_kernel', '$disk_io','$disk_available');"

export PGPASSWORD='password'; psql -h localhost -U postgres -d host_agent -c "$insert_stmt"

exit $?
