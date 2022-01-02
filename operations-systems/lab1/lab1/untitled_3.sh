#!/bin/bash
	

echo "Press 1 to run nano"
echo "Press 2 to run vi"
echo "Press 3 to run links"
echo "Press 4 to exit"

read A

if [[ $A -eq 1 ]]
then nano 
fi
if [[ $A -eq 2 ]]
then vi
fi
if [[ $A -eq 3 ]]
then links
fi
if [[ $A -eq 4 ]]
then exit
fi