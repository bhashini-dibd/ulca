# Dataset Utilities

Repo contains scripts to convert various data to the ULCA accepted format.

## txt_2_ulca_monolingual.py

To convert a utf-8 txt file to  data.json as supported by ULCA.

Code usage:

      python txt_2_ulca_monolingual.py -input "/home/input.en" -output "/home/data.json"

## txt_2_ulca_bilingual.py

To combine two different utf-8 txt files and create a single data.json as supported by ULCA.
Both files are expected to contain similar number of sentences, being a bi-lingual dataset.

Code usage:

      python txt_2_ulca_bilingual.py -input_src "/home/input.en" -input_tgt "/home/input.hi" -output "/home/data.json"
