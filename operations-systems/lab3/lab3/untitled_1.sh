#!/bin/bash
curDate=$(date '+%d.%m.%y_%H:%M:%S')
mkdir ~/lab3/test && echo "catalog test was created successfully" >> ~/lab3/report && touch ~/lab3/test/$curDate
ping -c 3 "www.net_nikogo.ru" || echo "www.net_nikogo.ru : host unavailable" >> ~/lab3/report

