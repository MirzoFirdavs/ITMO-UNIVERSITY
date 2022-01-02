#!/bin/bash
[ ! -d "~/.trash" ] && mkdir ~/.trash
i=0
while [[ -e "~/.trash/$1_$i" ]] ; do
	((i=i + 1))
done
ln $1 ~/.trash/$1_$i
rm $1 && echo "$1_$i $(realpath $1)" >> ~/.trash.log