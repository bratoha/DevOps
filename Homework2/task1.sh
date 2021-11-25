#!/bin/bash

number_of_workers="4"
column_name="link"
folder_path="output"
dataset_file="labelled_newscatcher_dataset.csv"

while getopts ":n:w:d:o:" opt; do
  case $opt in
    n) column_name="$OPTARG"
    ;;
    w) number_of_workers="$OPTARG"
    ;;
    o) folder_path="$OPTARG"
    ;;
    d) dataset_file="$OPTARG"
	;;
    \?) echo "error: Invalid option -$OPTARG" >&2
    exit 1
    ;;
  esac

  case $OPTARG in
    -*) echo "error: Option $opt needs a valid argument"
    exit 1
    ;;
  esac
done

if [ $number_of_workers -le 0 ]; then
 echo "error: Incorrect number of workers"
 exit 1
fi

column_number=$(head -1 labelled_newscatcher_dataset.csv | tr ';' '\n' | nl | grep -w "$column_name" | tr -d " " | awk -F " " '{print $1}')

if [ -z $column_number ]; then
   echo "error: Column with name '$column_name' does not exists in file $dataset_file" 
   exit 1
fi

while read -r line
do
    field=$(echo "$line" | cut -f $column_number -d';')
    echo "$field"
done < labelled_newscatcher_dataset.csv | parallel --progress -j $number_of_workers wget -q {} -P "$folder_path"
