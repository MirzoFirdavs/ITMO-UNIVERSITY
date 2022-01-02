#!/bin/bash
ps -e -U root -o pid,comm | tail -n+2 | sed -r "s/\s*([0-9]+)\s(.+)$/\1:\2/" > task1.txt
wc -l task1.txt