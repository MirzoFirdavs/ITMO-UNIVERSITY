#!/bin/bash
curPPid=0
ChildrenCnt=0
ART=0
while read line
do
editedLine=$(echo $line | sed -e 's/=/ /g')
PPid=$(echo $editedLine | awk '{ print($5) }')
if [[ $curPPid == $PPid ]]
then
    echo $line >> fifth
    ChildrenCnt=$(($ChildrenCnt+1))
    curART=$(echo $editedLine | awk '{ print($NF) }')
    ART=$(echo $ART $curART | awk '{ print($1+$2) }')
else
    average=$(echo $ART $ChildrenCnt | awk '{ print($1/$2) }')
    echo "Average_Running_Children_of_ParentID=$curPPid is $average" >> fifth
    echo $line >> fifth
    curPPid=$PPid
    ChildrenCnt=1
    ART=$(echo $editedLine | awk '{ print($NF) }')
fi
done < fourth
average=$(echo $ART $ChildrenCnt | awk '{ print($1/$2) }')
echo "Average_Running_Children_of_ParentID=$curPPid is $average" >> fifth