#!*bin/bash
mkfifo pipe
\. hand5.sh &
read s
while true
do
	case $s in
	 	"+"|"*"|"/"|"-"|[[:digit:]]*)
			echo "$s" > pipe
	 	;;
	 	"QUIT")
			echo "Complete"
			echo $s > pipe
			break
		;;
		*)
			echo "Wrong input data"
			echo "QUIT" > pipe
			break
		;;
	esac 
	read s
done