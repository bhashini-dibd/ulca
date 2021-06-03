
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
|   6 | negative       | [negative-testcase-01](./parallel-dataset/negative-testcase-01) | *incorrect dataset-type in params file* |
|   7 | negative       | [negative-testcase-02](./parallel-dataset/negative-testcase-02) | *incorrect language code in params file*|
|   8 | negative       | [negative-testcase-03](./parallel-dataset/negative-testcase-03) | *missing required fields (license)  in params file*|
|   9 | negative       | [negative-testcase-04](./parallel-dataset/negative-testcase-04) | *incorrect structure for domain in params file(string added where array of string is expected)*|
|  10 | negative       | [negative-testcase-05](./parallel-dataset/negative-testcase-05) | *value not part of enum in params file (collectionDescription defined as 'unknown', which is not defined in the enum)*|
|  11 | negative       | [negative-testcase-06](./parallel-dataset/negative-testcase-06) | *array out of bound for a field in params file (collectionSource array size defined as > 10)*|
|  12 | negative       | [negative-testcase-07](./parallel-dataset/negative-testcase-07) | *incorrect keys in data file*|
|  13 | negative       | [negative-testcase-08](./parallel-dataset/negative-testcase-08) | *overridden value have incorrect structure in data file (collectionDetails is not defined under collectionMethod)*|


## Test Case Files
Each test case would contain `params.json` and `data.json`

The individual test case details are documented under respective README.
