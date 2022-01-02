#!/bin/bash
printf "%s, " $(grep -r -E -o -h -s "[a-zA-Z0-9_.+-]+@[-a-zA-Z0-9]+\.[a-zA-Z0-9.-]+" /etc | sort | uniq) | sed -e 's/, $/./' > emails.lst 