#!/bin/bash

ps -ef | grep java | awk '/server-actor/ { print $2 }' | xargs kill -9
ps -ef | grep java | awk '/client-actor/ { print $2 }' | xargs kill -9

