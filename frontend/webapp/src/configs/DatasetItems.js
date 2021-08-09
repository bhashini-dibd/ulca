export const ModelTask = [

    { value: 'translation', label: 'Translation' },
    { value: 'asr', label: 'ASR' },
    { value: 'tts', label: 'TTS' },
    { value: 'ocr', label: 'OCR' },
    // { value: 'document-layout', label: 'Document Layout' }
];
export const DatasetItems = [

    { value: 'parallel-corpus', label: 'Parallel Dataset' },
    { value: 'monolingual-corpus', label: 'Monolingual Dataset' },
    { value: 'asr-corpus', label: 'ASR / TTS Dataset' },
    { value: 'ocr-corpus', label: 'OCR Dataset' },
    { value: 'asr-unlabeled-corpus', label: 'ASR Unlabeled Dataset' }
];
export const Language = [
    { value: 'as', label: 'Assamese' },
    { value: 'bn', label: 'Bengali' },
    { value: 'en', label: 'English' },
    { value: 'gu', label: 'Gujarati' },
    { value: 'hi', label: 'Hindi' },
    { value: 'kn', label: 'Kannada' },
    { value: 'ml', label: 'Malayalam' },
    { value: 'mr',label: 'Marathi'},
    { value: 'or', label: 'Odia' },
    { value: 'pa', label: 'Punjabi' },
    { value: 'ta', label: 'Tamil' },
    { value: 'te', label: 'Telugu' },
    { value: 'ur', label: 'Urdu' },
    { value: 'ne', label: 'Nepali' },
    

];


export const FilterBy = {
    collectionMethod: [
        {
            value: 'algorithm-auto-aligned',
            label: 'Algorithm Auto Aligned'
        },
        {
            value: 'algorithm-back-translated',
            label: 'Algorithm Back Translated'
        },
        {
            value: 'crowd-sourced',
            label: 'Crowd Sourced'
        },
        {
            value: 'human-validated',
            label: 'Human Validated'

        },
       
        {
            value: 'manual-human-translated',
            label: 'Manual Human Translated'
        },
        
        {
            value: 'phone-recording',
            label: 'Phone Recording'
        },
        
        {
            value: 'web-scrapping-machine-readable',
            label: 'Web Scrapping Machine Readable'
        },
        {
            value: 'web-scrapping-ocr',
            label: 'Web Scrapping Ocr'
        },
    ],


    domain: [
        {
            value: 'agriculture',
            label: 'Agriculture'

        },
        {
            value: 'automobile',
            label: 'Automobile'
        },
        {
            value: 'education',
            label: 'Education'
        },
        {
            value: 'general',
            label: 'General'
        },
        {
            value: 'healthcare',
            label: 'Healthcare'
        },
        {
            value: 'legal',
            label: 'Legal'
        },
        {
            value: 'news',
            label: 'News'
        },
        {
            value: 'tourism',
            label: 'Tourism'
        }]

    }
export default DatasetItems;
