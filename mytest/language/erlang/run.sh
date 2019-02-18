#!/bin/bash
mkdir -p ebin
erlc useless.erl -o ebin/
erl -pa ./ebin

# 运行useless:greet(male, kenny).