#!/bin/bash
term()
{
	echo "Handler was finished work from TERM signal of another process"
	exit 0
}
add() 
{
	op=+
}
mul() 
{
	op=*
}
div() 
{
	op=/
}
sub() 
{
	op=-
}
op=+
res=1
numder=2
trap 'add' USR1
trap 'mul' USR2	
trap 'term' SIGTERM
trap 'div' SIGPWR
trap 'sub' SIGQUIT
while true
do
	let res=$res$op$numder
	echo $res
	sleep 3
done