import { createContext, useState, useEffect } from "react";
import { GET_FONT_NAME } from "../constants/FontConstants";
export const AppContext = createContext();
export const { translation, tts } = { translation: "translation", tts: "tts" };
const AppContextProvider = (props) => {
    const [getFontSize, setFontSize] = useState('');
    const [getDefaultFontSize, setDefaultFontSize] = useState(GET_FONT_NAME);


    //SearchModelRequest

    const [getSearchModelErrorMessage, setSearchModelErrorMessage] = useState({});
    const [isSkipToMainContent, setSkipToMainContent] = useState(false);
    // =============================================================================

    const updateSkipToMainContent = (isSkip) => setSkipToMainContent(isSkip);



    const updateFont = (fontSize) => {
        let root = document.documentElement; // Access the root element (html) of the document
        if (fontSize === 'increase') {
            root.style.fontSize = '18px'; // Set root font size to 18px
        } else if (fontSize === 'decrease') {
            root.style.fontSize = '14px'; // Set root font size to 14px
        } else {
            root.style.fontSize = '16px'; // Set root font size to 16px for default or any other cases
        }
        setFontSize(fontSize)
    }

    return (
        <AppContext.Provider value={{
            // checkModelAvailibityAsPerLanguage,
            // getCurrentOutputAudioGender,
            getDefaultFontSize,
            getFontSize,
            // getIsAudio,
            // getIsModelPairAvailable,
            updateFont,
            // updateAudioOutputGender,
            // updateInputLanguage,
            // updateOutputLanguage,
            // getOutPutLanguage,
            // getInputLanguage,
            // updateTranslationModel,
            // updateTtsModel,
            // updateAsrModel,
            // getTranslationModel,
            // getTtsModel,
            // getAsrModel,
            // updateInputText,
            // updateInputAudio,
            // updateOutputText,
            // updateOutputAudio,
            // getInputText,
            // getInputAudio,
            // getOutputText,
            // getOutputAudio,
            // updateIsModelRandom,
            // isRandomModel,
            // updateSelectedModelInputTTS,
            // updateSelectedModelInputASR,
            // updateSelectedModelOutputTTS,
            // updateSelectedModelOutputASR,
            // updateSelectedModelTRANSLATION,
            // getSelectedInputAsrModel,
            // getSelectedInputTtsModel,
            // getSelectedOutputAsrModel,
            // getSelectedOutputTtsModel,
            // getSelectedTranslationModel,
            // updateLanguageSpecificModelListTRANSLATION,
            // updateLanguageSpecificModelListInputTTS,
            // updateLanguageSpecificModelListInputASR,
            // updateLanguageSpecificModelListOutputTTS,
            // updateLanguageSpecificModelListOutputASR,
            // getFilteredTranslationModels,
            // getFilteredInputAsrModels,
            // getFilteredInputTtsModels,
            // getFilteredOutputAsrModels,
            // getFilteredOutputTtsModels,
            // updateGoToServiceMainPage,
            // getGoToServiceMainPage,
            getSearchModelErrorMessage,
            // getInputLanguageList,
            isSkipToMainContent,
            updateSkipToMainContent,
            // getCurrentAudioGender,
            // updateAudioGender,
            // updateInputLanguage,
            // updateOutputLanguage,
            // updateOutputLanguageList,
            // getOutPutLanguage,
            // getInputLanguage,
            // updateInputText,
            // updateInputAudio,
            // updateOutputText,
            // updateOutputAudio,
            // getInputText,
            // getInputAudio,
            // getOutputText,
            // getOutputAudio,
            // getInputLanguageList,
            // getCallbackUrl,
            // getInferenceApiKey,
            // getAnuvaadLanguages,
            // getOutputLanguageList,
            // isError,
            // getErrorMessage,
            // handleLanguageSwitch,
            // isLanguageSwitch,
            // getServerError,
            // updateServerError,
            // isTypingAction,
            // isVoiceAction,
            // updateTypingAction,
            // updateVoiceAction,  
            // getTranslationModelList,
            // getASRModelList,
            // getTTSModelList,
            // getOCRModelList,
            // getNERModelList,
            // getTRANSLITRATIONModelList,
            // getLDModelList,

        }}>
            {props.children}
        </AppContext.Provider>
    )
}
export default AppContextProvider;