#!/bin/bash
\. proc4.sh &
pid1=$!
\. proc4.sh &
pid2=$!
\. proc4.sh &
pid3=$!
echo $pid1 $pid2 $pid3
sleep 10
cpulimit -p $pid1 -l 10 &
sleep 10
kill $pid3
sleep 10
kill pid1
kill pid2
