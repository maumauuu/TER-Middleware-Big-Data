#!/bin/bash

tar zxvf spark.tar.gz 
tar zxvf scala.tar.gz

chmod a+x ~/.bashrc

./spark/sbin/start-slave.sh spark://192.168.043.0137077
./spark/sbin/start-slave.sh spark://192.168.043.014:7077
