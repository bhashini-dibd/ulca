# Evaluvation Metrics

Repo contains various metrics implementations to evaluate the corpus level performance of ASR/OCR/Translation models submitted to to ULCA.

## ASR & OCR METRICS

The output of OCR/ASR engines often contains a number of mistakes such as misspelt words or spurious characters. In order to obtain a measure that is independent of the text size, the number of mistakes is usually normalized to the length of the expected content (the ground truth text). The quotient between the number of mistakes and the text length is known as the error rate. Since its error rate, lower the best. values will be in range of 0 to 1, where closer to 0 means the best probable output.

The error rate is usually calculated at two different levels:

### WER

- The WER is derived from the Levenshtein distance, working at the word level instead of the phoneme level.
- This kind of measurement, however, provides no details on the nature of translation errors and further work is therefore required to identify the main source(s) of error and to focus any research effort.
- WER = (S + D + I) / N = (S + D + I) / (S + D + C) where S is the number of substitutions, D is the number of deletions, I is the number of insertions, C is the number of correct words, N is the number of words in the reference (N=S+D+C).
- WER's output is always a number between 0 and 1.
- This value indicates the percentage of words that were incorrectly predicted. The lower the value, the better the performance of the ASR system with a WER of 0 being a perfect score.
- Library used : [Jiwer](https://github.com/jitsi/jiwer "Jiwer")
- Reference implementation : [Here](https://github.com/huggingface/datasets/blob/master/metrics/wer/wer.py "Here")

### CER

- CER is similar to Word Error Rate (WER), but operates on character instead of word.
- CER = (S + D + I) / N = (S + D + I) / (S + D + C) where S is the number of substitutions, D is the number of deletions, I is the number of insertions, C is the number of correct characters, N is the number of characters in the reference (N=S+D+C).
- CER's output is always a number between 0 and 1.
- This value indicates the percentage of words that were incorrectly predicted. The lower the value, the better the performance of the ASR/OCR system with a CER of 0 being a perfect score.
- Library used : [Jiwer](https://github.com/jitsi/jiwer "Jiwer")
- Reference implementation : [Here](https://github.com/huggingface/datasets/blob/master/metrics/cer/cer.py "Here")

## TRANSLATION METRICS

Unlike ASR and OCR, there might be multiple correct alternatives to the reference content, when it comes to translation. All these cases must be considered.  Wrt translation metrics, generally the values will be in range of 0 to 1, where values closer to 1 means more accurate it is.

### BLEU SCORE

- Bilingual Evaluation Understudy Score
- BLEU is a quality metric score for MT systems that attempts to measure the correspondence between a machine translation output and a human translation.
- Library used : [Sacrebleu](https://github.com/mjpost/sacrebleu "Sacrebleu")
- Reference implementation : [Here](https://github.com/AI4Bharat/indicTrans/blob/main/compute_bleu.sh "Here")
- Tokenizer : [Indicnlp](https://github.com/anoopkunchukuttan/indic_nlp_library "Indicnlp")

### RIBES SCORE

- Rank-based Intuitive Bilingual Evaluation Score
- Different from BLEU's micro-average precision, RIBES calculates the macro-average precision by averaging the best RIBES score for each pair of hypothesis and its corresponding references
- Library used : [Nltk](https://www.nltk.org/ "Nltk")
- Reference implementation : [Here](https://www.nltk.org/_modules/nltk/translate/ribes_score.html "Here")
- Tokenizer : [Indicnlp](https://github.com/anoopkunchukuttan/indic_nlp_library "Indicnlp")

### GLEU SCORE

- Google-BLEU
- correlates quite well with the BLEU metric on a corpus level but does not have its drawbacks for our per sentence reward objective
- For the GLEU score, we record all sub-sequences of 1, 2, 3 or 4 tokens in output and target sequence (n-grams).
- compute a recall, which is the ratio of the number of matching n-grams to the number of total n-grams in the target (ground truth) sequence, and a precision, which is the ratio of the number of matching n-grams to the number of total n-grams in the generated output sequence.
- GLEU score is simply the minimum of recall and precision. This GLEU score's range is always between 0 (no matches) and 1 (all match) and it is symmetrical when switching output and target.
- GLEU score correlates quite well with the BLEU metric on a corpus level but does not have its drawbacks for our per sentence reward objective.
- Library used : [Nltk](https://www.nltk.org/ "Nltk")
- Reference implementation : [Here](https://www.nltk.org/_modules/nltk/translate/gleu_score.html "Here")
- Tokenizer : [Indicnlp](https://github.com/anoopkunchukuttan/indic_nlp_library "Indicnlp")

### BERT SCORE

- BERTScore leverages the pre-trained contextual embeddings from BERT and matches words in candidate and reference sentences by cosine similarity.
- It has been shown to correlate with human judgment on sentence-level and system-level evaluation.
- Model based approach and hence computationally expensive
- Value lies in range of 0 to 1, closest to 1 being the best
- Supported Languages : [Here](https://github.com/google-research/bert/blob/master/multilingual.md#list-of-languages "Here")
- Library used : [bert-score](https://github.com/Tiiiger/bert_score "bert-score")
- Reference implementation : [Here](https://github.com/huggingface/datasets/blob/master/metrics/bertscore/bertscore.py "Here")
- Tokenizer : Default

### METEOR

- Metric for Evaluation of Translation with Explicit ORdering
- Includes stemming and synonymy matching, along with the standard exact word matching.
- Based on a generalized concept of unigram matching between the machine-produced translation and human-produced reference translations.
- Meteor-Hindi was developed in 2010 to make hindi evaluvations more accurate
- The metric was designed to fix some of the problems found in the more popular BLEU metric, and also produce good correlation with human judgement at the sentence or segment level.
- Library used : [Nltk](https://www.nltk.org/ "Nltk")
- Reference implementation : [Here](https://github.com/huggingface/datasets/blob/master/metrics/meteor/meteor.py "Here")
- Tokenizer : Default

### chrF

- Character-level F-score
- MT evaluation metric that use the F-score statistic for character n-gram matching.
- chrF measures the amount of overlap of short sequences of characters (n-grams) between the MT output and the reference.
- Since it’s character-based, it’s not very sensitive to how the sentences are tokenized.
- Library used : [Sacrebleu](https://github.com/mjpost/sacrebleu/blob/master/sacrebleu/metrics/chrf.py "Sacrebleu")
- Reference implementation : [Here](https://github.com/huggingface/datasets/tree/main/metrics/chrf "Here")


## TRANSLITERATION METRICS

Generally transliteration is evaluvated at word level, rather than sentence level.

### CER

- Character Error Rate, Similar to that of ASR/OCR described above.
- Reference implementation : [Here](https://github.com/AI4Bharat/IndicXlit/blob/master/model_training_scripts/evaluate/evaluate_result_with_rescore_option.py#L153 "Here")

### TOP-1 ACCURACY
- Top-1 accuracy is the conventional version of accuracy.
- It measures the proportion of examples for which the predictedlabel matches the single target label.
- The model answer (the one with highest probability) must be exactly the expected answer.
- Reference implementation : [Here](https://github.com/AI4Bharat/IndicXlit/blob/master/model_training_scripts/evaluate/evaluate_result_with_rescore_option.py#L532 "Here")


*Additionally, few other metrics such as Rouge, Comet etc: were also experiemnted. However, they are not implemented as of now since they are not giving scores in a proper range for Indic languages. Feel free to drop your suggestions or knowledge about any new metrics. we would love to include them!*

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
