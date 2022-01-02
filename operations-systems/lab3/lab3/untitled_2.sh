#!/bin/bash
script=~/lab3/untitled_1.sh
report=~/lab3/report
at -f $script now +2 minutes
tail -f $report