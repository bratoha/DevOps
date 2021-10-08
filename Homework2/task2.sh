#!/bin/bash

for arg in "$@"; do
  shift
  case "$arg" in
    "--input") 			set -- "$@" "-h" ;;
    "--train_ratio") 	set -- "$@" "-r" ;;
    *)        			set -- "$@" "$arg"
  esac
done

dataset_path="dataset.csv"
train_ratio="0.5"

# Parse short options
OPTIND=1
while getopts "hrw" opt
do
  case "$opt" in
    "h") dataset_path=$2 ;;
    "r") train_ratio=$2 ;;
    "?") print_usage >&2; exit 1 ;;
  esac
done
shift $(expr $OPTIND - 1)

lines_number=$(cat $dataset_path | wc -l)

file_head=$(head -1 $dataset_path)

train_lines_number=$(awk "BEGIN {print int(($lines_number - 1) * $train_ratio)}")
test_lines_number=$(awk "BEGIN {print $train_lines_number + 1}")

echo "$file_head" > train.csv
echo "$file_head" > test.csv

cat $dataset_path | awk -F ","  -v end="$train_lines_number" 'NR>=2 && NR<=end { print }' >> train.csv
echo "Train dataset was created"

cat $dataset_path | awk -F "," -v start="$test_lines_number" -v end="$lines_number" 'NR>=start && NR<=end { print }' >> test.csv
echo "Test dataset was created"
