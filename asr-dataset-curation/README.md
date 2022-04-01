# ASR DATASET CURATION
Accepts a youtube link and generates small audio chunks(depending on the pauses, also excluding the noise) along with their transcriptions using Vakyansh ASR model.
User can suggest alterations in transcriptions or store them as is to be used as ASR benchmark datasets.

## Prerequisites
- python 3.7
- ubuntu 16.04

Dependencies:
```bash
pip install -r requirements.txt
```
Run:
```bash
python app.py
```

## License
[MIT](https://choosealicense.com/licenses/mit/)
