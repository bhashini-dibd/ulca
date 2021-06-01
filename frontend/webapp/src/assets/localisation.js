const localization_EN_Data = {
  "common.page.label.active": "Active",
  
  "common.page.label.downloadCompleted": "Download completed...",
};

export function translate(locale_text) {
  if (localStorage.getItem("userProfile") && localStorage.getItem(`lang${JSON.parse(localStorage.getItem("userProfile")).id}`)) {
    const lang = localStorage.getItem(`lang${JSON.parse(localStorage.getItem("userProfile")).id}`);
    switch (lang) {
      case "hi":
        return localization_HI_Data[locale_text] || locale_text;
      case "en":
        return localization_EN_Data[locale_text] || locale_text;
      default:
        return null;
    }
  } else {
    return localization_EN_Data[locale_text] || locale_text;
  }
}
