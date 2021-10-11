const localization_EN_Data = {
  "sourceLanguage": "Source Language",
  "targetLanguage": "Target Language",
  "datasetType": "Dataset Type",
  "domain": "Domain",
  "collectionMethod": "Collection Method",
  "collectionSource": "Collection Source",
  "license": "License",
  "submitterName": "Submitter",
  "alignmentTool": "Alignment Tool",
  "minScore": "Min Score",
  "maxScore": "Max Score",

  "editingTool": "Editing Tool",
  "translationModel": "Translation Model",
  "datasetId": "Dataset Id",
  "channel": "Channel",
  "gender": "Gender",
  "format": "Format",
  "bitsPerSample": "Bits Per Sample",
  "dialect": "Dialect",
  "snrTool": "Snr Tool",
  "collectionDescription": " Collection Description",
  "ocrTool": "Ocr Tool",
  "dpi": "Dpi",
  "imageTextType": "Image Text Type",
  "score": "Score",
  "samplingRate": "Sampling Rate",
  "countOfTranslations": "Count Of Translations",
  "minNoOfSpeakers": "Min No Of Speakers",
  "maxNoOfSpeakers": "Max No Of Speakers",
  "noOfSpeakers": "No Of Speakers",
  "minAge": "Min Age",
  "maxAge": "Max Age",
  "age": "Age",

};

export function translate(locale_text) {
  return localization_EN_Data[locale_text] ? localization_EN_Data[locale_text] : "";
}
