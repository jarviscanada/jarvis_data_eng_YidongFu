#!/bin/bash

#script pseudocode
#hint: `systemctl status docker || ...`

#Setup arguments
usage=$1
db_username=$2
db_password=$3

su centos

systemctl status docker || systemctl start docker

#if argument is create
if [ "$1" = 'create'];then
  if [ $(docker container ls -a -f name=jrvs-psql | wc -l) -eq 2 ]; then
    echo "container is already created"
    exit 1

  if [ -z "$2" ] || [ -z "$3" ]; then
    echo "username or password is invalid"
    exit 1

  docker volume create pgdata
  docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USER=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
  exit $?

if [ $(docker container ls -a -f name=jrvs-psql | wc -l) -eq 1 ]; then
    echo "container does not exist"
    exit 1

#if argument is start
if $1 = "start"
  if [ $(docker container ls -a -f name=jrvs-psql | wc -l) -eq 1 ]; then
     echo "container does not exist"
  docker container start jrvs-psql
  exti $?

#if argument is stop
if $1 = "stop"
  docker container stop jrvs-psql
  exit $?