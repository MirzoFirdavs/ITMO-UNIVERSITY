#!/bin/bash
cmd=""
max=0
ps -A -o pid,cmd > modTemp
while read line
do
	echo $line > lineTemp
	sed -i -e 's/ /\n/g' lineTemp
	cur=$(grep "^-" lineTemp | wc -w)
	if [[ $cur -gt $max ]]
	then
		cmd=$line
		max=$cur
	fi
done < modTemp
rm modTemp
rm lineTemp
echo "PID and command:"
echo $cmd
echo "Amount of flags: $max"