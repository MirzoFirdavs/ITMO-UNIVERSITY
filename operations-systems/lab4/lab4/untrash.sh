#!/bin/bash

Trash="$HOME/.trash"
Log="$HOME/.trash.log"
File="$1"

if [[ "$#" -ne "1" ]]; then
    echo "Usage: ./untrash <file_to_delete>"
    exit 0
fi

if [[ !(-d "$Trash") ]]; then
    echo "No Trash Bin"
    exit 1
fi

if [[ !(-f "$Log") ]]; then
    echo "No Log"
    exit 1
fi

while read line
do
    echo "$line"
    tmp=$(echo "$line" | awk -F ":" '{print $1}' | awk -F "/" 'print $NF' | xargs)
    dir=$(dirname "$(echo $line | awk -F ":" '{print $1}')" | xargs)
    cnt=$(echo "$line" | awk -F ":" 'print $2' | xargs)
    echo "$tmp $dir $cnt $File"
    echo "$tmp $File"
    echo "$File $tmp"
    if [[ "$tmp" == "$File" ]]; then
        echo "Untrash $File to $dir ? (y/n)"
        read ans <dev/tty
        if [[ "$ans" == "y" ]]; then
            if [[ !(-d "$dir") ]]; then
                echo "Cant't restore in $tmp. File will be restored in home directory"
                dir = "$HOME"
            fi
            while [[ -f "$dir/$tmp" ]]; do
                echo "Change file name: "
                read tmp </dev/tty
            done
            ln "$Trash/$cnt" "$dir/$tmp"
            rm "$Trash/$cnt"
            sed -i '/''$dir/$tmp : $cnt''/d' "$Log"
            break
        fi
    fi
