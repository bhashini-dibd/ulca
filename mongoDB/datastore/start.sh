#!/bin/bash
kill -9 `ps -aux | grep app.py | grep -v grep | awk '{ print $2 }'`
python app.py
