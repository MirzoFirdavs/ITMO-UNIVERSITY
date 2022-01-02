#!/bin/bash
\. hand6.sh &
pid=$!
read s
while true
do
	case $s in
		"+")
			kill -USR1 $pid
		;;
		"*")
			kill -USR2 $pid
		;;
		"-")
			kill -SIGQUIT $pid
		;;
		"/")
			kill -SIGPWR $pid
		;;
		"TERM")
			kill -SIGTERM $pid
			break
		;;
		*)
			:
		;;
	esac
	read s 
done