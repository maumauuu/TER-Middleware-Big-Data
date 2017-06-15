#!/usr/bin/env bash

docker-machine create --driver virtualbox node-1
docker-machine create --driver virtualbox node-2

eval "$(docker-machine env node-1)"
docker swarm init --advertise-addr $(docker-machine ip node-1)
token=$(docker swarm join-token -q worker)

eval "$(docker-machine env node-2)"
docker swarm join --token $token $(docker-machine ip node-1):2377

docker network create --driver overlay --subnet 10.0.0.0/22 spark

docker stack deploy -c spark.yml spark
