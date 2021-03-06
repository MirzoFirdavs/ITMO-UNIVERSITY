#!/bin/bash
cd $HOME
dateFiles="$(ls | grep -E "^Backup-" | sort -n | tail -1 | cut -d "-" -f 2,3,4)"
if [[ ! -d restore ]]
then
	mkdir restore
fi
cd "Backup-$dateFiles"
listInBackup="$(ls)"
for i in $listInBackup
do
	filesNotToCopy="$(echo "$i" | grep -a -P '[A-Za-z0-9]{1,}.[0-9]{4}-[0-9]{2}-[0-9]{2}' )"
	echo "$filesNotToCopy"
	if [ "$i" != "$filesNotToCopy" ]
	then
		cp -R "$i" $HOME/restore
	fi
done