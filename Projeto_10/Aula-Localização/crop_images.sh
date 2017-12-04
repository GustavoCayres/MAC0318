#!/bin/zsh

target_directory=$1
magick_crop_arg=$2

for file in $target_directory/**/*(.); do 
    echo cropping $file
    convert "$file" -crop $magick_crop_arg "$target_directory/cropped_$(echo $file | awk '{print $6}')"
done 
