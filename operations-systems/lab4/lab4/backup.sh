#!/bin/bash
cd /home/darling
homeDir=/home/darling
dateFiles="$(ls | grep -E "^Backup-" | sort -n | tail -1 | cut -d "-" -f 2,3,4)"
canMakeDir=0
dateNow="$(date +%Y-%m-%d)"
secondsPrev="$(date -d "$dateFiles" +%s)"
secondsNow="$(date -d "$dateNow" +%s )"
let canMakeDir=($secondsNow-$secondsPrev)/60/60/24
echo $canMakeDir
echo "Backup-$dateNow"
if [[ $canMakeDir -gt 7 ]]
then
	mkdir "Backup-$dateNow"
	cd $homeDir/source
	listInSource="$(ls)"
	echo "$listInSource"
	for i in $listInSource
	do
		cp -R $i $homeDir/Backup-$dateNow
	done
	cd $homeDir
	echo "Directory Backup-$dateNow was created. Files: $listInSource" >> backup-report
else
	cd $homeDir/source
	listInSource="$(ls)"
	cd $homeDir/Backup-$dateFiles
	for i in $listInSource
	do
		if [ -f "$i" ]
		then
			sizeInSource="$(ls -l -- $homeDir/source/$i | awk '{print$5}')"
			sizeInBackup="$(ls -l $i | awk '{print$5}')"
			if [[ $sizeInSource -ne $sizeInBackup ]]
			then
				mv $i $i.$dateNow
				cp -R -- $homeDir/source/$i $homeDir/Backup-$dateFiles
				echo "rename $i to $i-$dateNow" >> $homeDir/backup-report
			fi
		else
			cp -R $homeDir/source/$i $homeDir/Backup-$dateFiles
		fi
	done
fi