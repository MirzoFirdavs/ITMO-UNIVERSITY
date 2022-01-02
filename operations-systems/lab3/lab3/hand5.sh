#!/bin/bash
op=+
res=1
(tail -f pipe) | while true
do
	read s 
	case $s in 
		"QUIT")
			killall tail
			sudo rm pipe
			exit
		;;
		"+"|"*"|"/"|"-")
			op=$s
		;;
		*)
			let res=$res$op$s
			echo $res
		;;
	esac
done