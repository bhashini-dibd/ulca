
# ULCA Test Datasets

Sample datasets covering a range of scenarios to facilitate API tests.



### `parallel-corpus`

|S No.| Test Case Type | Test Dataset Name                                               | Test Case Description      |
|-----|----------------|-----------------------------------------------------------------|----------------------------|
|   1 | positive       | [positive-testcase-01](./parallel-dataset/positive-testcase-01) | *basic correct dataset*    |
|   2 | positive       | [positive-testcase-02](./parallel-dataset/positive-testcase-02) | *machine translated target validated*              |
|   3 | positive       | [positive-testcase-03](./parallel-dataset/positive-testcase-03) | *machine translated target validated & post-edited*|
|   4 | positive       | [positive-testcase-04](./parallel-dataset/positive-testcase-04) | *human translated*          |
|   5 | positive       | [positive-testcase-05](./parallel-dataset/positive-testcase-05) | *web scrapped labse aligned*|
|   6 | positive       | [positive-testcase-06](./parallel-dataset/positive-testcase-06) | *duplicate pairs in data file*|
|   7 | negative       | [negative-testcase-01](./parallel-dataset/negative-testcase-01) | *incorrect dataset-type in params file* |
|   8 | negative       | [negative-testcase-02](./parallel-dataset/negative-testcase-02) | *incorrect language code in params file*|
|   9 | negative       | [negative-testcase-03](./parallel-dataset/negative-testcase-03) | *missing required fields (license) in params file*|
|  10 | negative       | [negative-testcase-04](./parallel-dataset/negative-testcase-04) | *incorrect structure for domain in params file(string added where array of string is expected)*|
|  11 | negative       | [negative-testcase-05](./parallel-dataset/negative-testcase-05) | *value not part of enum in params file (collectionDescription defined as 'unknown', which is not part of the enum)*|
|  12 | negative       | [negative-testcase-06](./parallel-dataset/negative-testcase-06) | *array out of bound for a field in params file (collectionSource array size defined as > 10)*|
|  13 | negative       | [negative-testcase-07](./parallel-dataset/negative-testcase-07) | *incorrect keys in data file*|
|  14 | negative       | [negative-testcase-08](./parallel-dataset/negative-testcase-08) | *overridden value have incorrect structure in data file (collectionDetails is not defined under collectionMethod)*|



### `monolingual-corpus`

|S No.| Test Case Type | Test Dataset Name                                               | Test Case Description      |
|-----|----------------|-----------------------------------------------------------------|----------------------------|
|   1 | positive       | [positive-testcase-01](./monolingual-dataset/positive-testcase-01) | *basic correct dataset*    |
|   2 | negative       | [negative-testcase-01](./monolingual-dataset/negative-testcase-01) | *incorrect dataset-type in params file* |
|   3 | negative       | [negative-testcase-02](./monolingual-dataset/negative-testcase-02) | *incorrect language code in params file*|
|   4 | negative       | [negative-testcase-03](./monolingual-dataset/negative-testcase-03) | *missing required fields (license) in params file*|
|   5 | negative       | [negative-testcase-04](./monolingual-dataset/negative-testcase-04) | *incorrect structure for domain in params file(string added where array of string is expected)*|
|   6 | negative       | [negative-testcase-05](./monolingual-dataset/negative-testcase-05) | *value not part of enum in params file (domain defined as 'unknown', which is not part of the enum)*|
|   7 | negative       | [negative-testcase-06](./monolingual-dataset/negative-testcase-06) | *array out of bound for a field in params file (collectionSource array size defined as > 10)*|
|   8 | negative       | [negative-testcase-07](./monolingual-dataset/negative-testcase-07) | *incorrect keys in data file*|



### `ocr-corpus`

|S No.| Test Case Type | Test Dataset Name                                          | Test Case Description      |
|-----|----------------|------------------------------------------------------------|----------------------------|
|   1 | positive       | [positive-testcase-01](./ocr-dataset/positive-testcase-01) | *basic correct dataset (computer-typed png)*    |
|   2 | positive       | [positive-testcase-02](./ocr-dataset/positive-testcase-02) | *basic correct dataset (scanned tiff)*    |
|   3 | negative       | [negative-testcase-01](./ocr-dataset/negative-testcase-01) | *incorrect dataset-type in params file* |
|   4 | negative       | [negative-testcase-02](./ocr-dataset/negative-testcase-02) | *incorrect language code in params file*|
|   5 | negative       | [negative-testcase-03](./ocr-dataset/negative-testcase-03) | *missing required fields (license) in params file*|
|   6 | negative       | [negative-testcase-04](./ocr-dataset/negative-testcase-04) | *incorrect structure for domain in params file(string added where array of string is expected)* |  
|   7 | negative       | [negative-testcase-05](./ocr-dataset/negative-testcase-05) | *value not part of enum in params file (dpi defined as 200_dpi, which is not part of the enum)*|
|   8 | negative       | [negative-testcase-06](./ocr-dataset/negative-testcase-06) | *array out of bound for a field in params file (collectionSource array size defined as > 10)*|
|   9 | negative       | [negative-testcase-07](./ocr-dataset/negative-testcase-07) | *incorrect keys in data file*|
|  10 | negative       | [negative-testcase-08](./ocr-dataset/negative-testcase-08) | *overridden value have incorrect structure in data file (collectionDetails is not defined under collectionMethod)*|



### `asr-corpus`

|S No.| Test Case Type | Test Dataset Name                                          | Test Case Description      |
|-----|----------------|------------------------------------------------------------|----------------------------|
|   1 | positive       | [positive-testcase-01](./asr-dataset/positive-testcase-01) | *audio auto aligned*    |
|   2 | positive       | [positive-testcase-02](./asr-dataset/positive-testcase-02) | *audio machine transcribed*    |
|   3 | positive       | [positive-testcase-03](./asr-dataset/positive-testcase-03) | *audio manual transcribed*    |
|   4 | positive       | [positive-testcase-04](./asr-dataset/positive-testcase-04) | *midlands english snr*    |
|   5 | negative       | [negative-testcase-01](./asr-dataset/negative-testcase-01) | *incorrect dataset-type in params file* |
|   6 | negative       | [negative-testcase-02](./asr-dataset/negative-testcase-02) | *incorrect language code in params file*|
|   7 | negative       | [negative-testcase-03](./asr-dataset/negative-testcase-03) | *missing required fields (license) in params file*|
|   8 | negative       | [negative-testcase-04](./asr-dataset/negative-testcase-04) | *incorrect structure for domain in params file(string added where array of string is expected)*|
|   9 | negative       | [negative-testcase-05](./asr-dataset/negative-testcase-05) | *value not part of enum in params file (channel defined as '5.1', which is not part of the enum)*|
|  10 | negative       | [negative-testcase-06](./asr-dataset/negative-testcase-06) | *array out of bound for a field in params file (collectionSource array size defined as > 10)*|
|  11 | negative       | [negative-testcase-07](./asr-dataset/negative-testcase-07) | *incorrect keys in data file*|
|  12 | negative       | [negative-testcase-08](./asr-dataset/negative-testcase-08) | *overridden value have incorrect structure in data file (methodType & methodDetails are not defined under snr)*|



### `layout-document-corpus`

|S No.| Test Case Type | Test Dataset Name                                             | Test Case Description      |
|-----|----------------|---------------------------------------------------------------|----------------------------|
|   1 | positive       | [positive-testcase-01](./layout-document-dataset/positive-testcase-01) | *computer typed document layouts*    |



## Test Case Files
Each test case would contain `params.json` and `data.json`

The individual test case details are documented under respective README.
