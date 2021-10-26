############################################################################################################
# AIM     : Script to convert regular utf-8 Bilingual text file to ULCA Format
# USAGE   : python txt_2_ulca_bilingual.py -input_src "/home/input.en" -input_tgt "/home/input.hi"
#           -output "/home/data.json"                                     
############################################################################################################

import sys
import argparse
import pandas as pd

msg = "Ulca Bilingual Formatter"

# Initialize parser & add arguments
parser = argparse.ArgumentParser(description = msg)
parser.add_argument("-input_src", "--input_src", help = "input src filepath")
parser.add_argument("-input_tgt", "--input_tgt", help = "input tgt filepath")
parser.add_argument("-output", "--output", help = "output filepath")
args = parser.parse_args()

if args.input_src is None or args.input_tgt is None or args.output is None:
    sys.exit("ERROR : filepath missing")

try:

    input_file_src = args.input_src
    input_file_tgt = args.input_tgt
    output_file = args.output

    with open(input_file_src, encoding="utf-8") as f:
        lines1 = f.readlines()

    with open(input_file_tgt, encoding="utf-8") as f:
        lines2 = f.readlines()

    df = pd.DataFrame(list(zip(lines1, lines2)), columns =['sourceText', 'targetText']) 
    df = df.replace('\n','', regex=True)
    df.to_json(output_file, force_ascii=False,orient='records',indent=2)
    print("DONE")

except Exception as e:
    print("___Exception Occoured___\n")
    print(e)