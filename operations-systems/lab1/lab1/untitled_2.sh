#!/bin/bash

ans=""
read A
while [[ "$A" != "q" ]]; do
    ans+="$A"
    read A
done
echo "$ans"