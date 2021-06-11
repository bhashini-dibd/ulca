const DatasetItems = [

    { value: 'parallel-corpus', label: 'Parallel Dataset' },
    { value: 'monolingual-corpus', label: 'Monolingual Dataset' },
    { value: 'asr-corpus', label: 'ASR / TTS Dataset' },
    { value: 'ocr-corpus', label: 'OCR Dataset' },


];
export const Language = [
    { value: 'en', label: 'English' },
    { value: 'hi', label: 'Hindi' },
    { value: 'be', label: 'Bengali' },
    { value: 'ta', label: 'Tamil' },
    {
        value: 'Mar',
        label: 'Marathi',
    }
];


export const FilterBy = {
    collectionMethod: [
        {
            value: 'web - scrapping - machine - readable',
            label: 'web scrapping machine readable'
        },
        {
            value: 'web - scrapping - ocr',
            label: 'web scrapping ocr'
        },
        {
            value: 'manual - human - translated',
            label: 'manual human translated'
        },
        {
            value: 'algorithm - auto - aligned',
            label: 'algorithm auto aligned'
        },
        {
            value: 'algorithm - back - translated',
            label: 'algorithm back translated'
        },
        {
            value: 'human - validated',
            label: 'human validated'

        },
        {
            value: 'phone-recording',
            label: 'Phone Recording'
        },
        {
            value: 'crowd-sourced',
            label: 'Crowd Sourced'
        }],

    domain: [
        {
            value: 'general',
            label: 'General'
        },
        {
            value: 'news',
            label: 'news'
        },
        {
            value: 'education',
            label: 'education'
        },
        {
            value: 'legal',
            label: 'legal'
        },
        {
            value: 'healthcare',
            label: 'healthcare'
        },
        {
            value: 'agriculture',
            label: 'agriculture'

        },
        {
            value: 'automobile',
            label: 'automobile'
        },
        {
            value: 'tourism',
            label: 'tourism'
        }]

    }
export default DatasetItems;
