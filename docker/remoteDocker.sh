#!/bin/bash

cp docker-tcp.socket /etc/systemd/system/docker-tcp.socket
systemctl daemon-reload
systemctl enable docker-tcp.socket
systemctl stop docker
systemctl start docker-tcp.socket
systemctl start docker
