export const DatasetItems = [

    { value: 'parallel-corpus', label: 'Parallel Dataset' },
    { value: 'monolingual-corpus', label: 'Monolingual Dataset' },
    { value: 'asr-corpus', label: 'ASR / TTS Dataset' },
    { value: 'ocr-corpus', label: 'OCR Dataset' },


];
export const Language = [
    { value: 'en', label: 'English' },
    { value: 'hi', label: 'Hindi' },
    { value: 'bn', label: 'Bengali' },
    { value: 'ta', label: 'Tamil' },
    { value: 'mr',label: 'Marathi'},
    { value: 'ml', label: 'Malayalam' },
    { value: 'te', label: 'Telugu' },
    { value: 'kn', label: 'Kannada' },
    { value: 'pa', label: 'Punjabi' },
    { value: 'gu', label: 'Gujarati' },
    { value: 'as', label: 'Assamese' },
    { value: 'ur', label: 'Urdu' },
    { value: 'or', label: 'Odia' },

];


export const FilterBy = {
    collectionMethod: [
        {
            value: 'web-scrapping-machine-readable',
            label: 'Web Scrapping Machine Readable'
        },
        {
            value: 'web-scrapping-ocr',
            label: 'Web Scrapping Ocr'
        },
        {
            value: 'manual-human-translated',
            label: 'Manual Human Translated'
        },
        {
            value: 'algorithm-auto-aligned',
            label: 'Algorithm Auto Aligned'
        },
        {
            value: 'algorithm-back-translated',
            label: 'Algorithm Back Translated'
        },
        {
            value: 'human-validated',
            label: 'Human Validated'

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
            label: 'News'
        },
        {
            value: 'education',
            label: 'Education'
        },
        {
            value: 'legal',
            label: 'Legal'
        },
        {
            value: 'healthcare',
            label: 'Healthcare'
        },
        {
            value: 'agriculture',
            label: 'Agriculture'

        },
        {
            value: 'automobile',
            label: 'Automobile'
        },
        {
            value: 'tourism',
            label: 'Tourism'
        }]

    }
export default DatasetItems;
