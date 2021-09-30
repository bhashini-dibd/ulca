############################################################################################################
# AIM     : Script to convert regular utf-8 monolingual text file to ULCA Format
# USAGE   : python3 ulca_formatter.py -input "/home/input.en" -output "/home/output.json"                                     
############################################################################################################

import sys
import argparse
import pandas as pd

msg = "ULCA Formatter"

# Initialize parser & add arguments
parser = argparse.ArgumentParser(description = msg)
parser.add_argument("-input", "--input", help = "input filepath")
parser.add_argument("-output", "--output", help = "output filepath")
args = parser.parse_args()

if args.input is None or args.output is None:
    sys.exit("ERROR : filepath missing")


input_file = args.input
output_file = args.output

with open(input_file, encoding="utf-8") as f:
    lines1 = f.readlines()

df = pd.DataFrame(list(zip(lines1)), columns =['text']) 
df = df.replace('\n','', regex=True)
df.to_json(output_file, force_ascii=False,orient='records',indent=2)
print("DONE")