#!/bin/bash
awk '$5~/^\/sbin\//{print $1, $5}' <<< "$(ps -ax)"
