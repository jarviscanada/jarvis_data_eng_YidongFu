#!/bin/bash

#script pseudocode
#hint: `systemctl status docker || ...`

#Setup arguments
usage=$1
db_username=$2
db_password=$3

systemctl status docker || systemctl start docker

#hint: use case statement to handle argument
#https://linuxize.com/post/bash-case-statement/
#if $1 == "create"
if [ "$1" = 'create'];then
  #hint `docker container ls -a -f name=jrvs-psql | wc -l` should be 2
  #if `jrvs-psql` container is already created
  if [ $(docker container ls -a -f name=jrvs-psql | wc -l) -eq 2 ]; then
    echo "container is already created"
    exit 1

  if [ -z "$2" ] || [ -z "$3" ]; then
    echo "username or password is invalid"
    exit 1

  #hint: `docker volume create ...`
  docker volume create pgdata
  #hint: `docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USER=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres`
  docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USER=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
  #What's $? variable? https://bit.ly/2LanHUi
  exit $?

if [ $(docker container ls -a -f name=jrvs-psql | wc -l) -eq 1 ]; then
    echo "container does not exist"
    exit 1

if $1 = "start"
  if [ $(docker container ls -a -f name=jrvs-psql | wc -l) -eq 1 ]; then
     echo "container does not exist"
  docker container start jrvs-psql
  exti $?

if $1 = "stop"
  docker container stop jrvs-psql
  exit $?

if [ $1 != "create" ] ||  [ $1 != "start" ] || [ $1 != "stop" ]
  echo "It's an invalid argument"
  exit 1

