const configs = {
    BASE_URL: "http://34.221.132.8:8080/",
    BASE_URL_AUTO: process.env.REACT_APP_APIGW_BASE_URL ? process.env.REACT_APP_APIGW_BASE_URL :  "https://prod-auth.ulcacontrib.org",
    DEV_SALT: process.env.SALT ? process.env.SALT : '85U62e26b2aJ68dae8eQc188e0c8z8J9',
    
};
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
];

export default configs;
