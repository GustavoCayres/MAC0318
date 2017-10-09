#!/bin/bash

nxjc $1.java
nxjlink -o $1.nxj $1
nxjupload -r $1.nxj
