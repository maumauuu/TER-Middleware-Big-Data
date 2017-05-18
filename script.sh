#!/bin/bash

tar zxvf spark.tar.gz 
tar zxvf scala.tar.gz

chmod a+x ~/.bashrc

source ~/.bashrc

./spark/sbin/start-slave.sh spark://192.168.43.113:7077
