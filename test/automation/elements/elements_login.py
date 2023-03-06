from selenium.webdriver.common.by import By

# elements_data-in-ULCA-websites
#         element['name'] ->  name of the element
#         element["by"] ->  selector(eg:By.XPATH,By.ID,By.TAG_NAME,)
#         element["value"] ->  value of the selector

# login-page-elements
LOGIN_USERNAME_INP = {
    "name": "LOGIN-PAGE-USERNAME-INPUT-FIELD",
    "by": By.ID,
    "value": "outlined-required",
}
LOGIN_PASSWORD_INP = {
    "name": "LOGIN-PAGE-PASSWORD-INPUT-FIELD",
    "by": By.ID,
    "value": "outlined-adornment-password",
}
LOGIN_BTN = {
    "name": "LOGIN-PAGE-LOGIN-BUTTON",
    "by": By.XPATH,
    "value": "//form/button",
}
DASH_PROFILE_BTN = {
    "name": "DASHBOARD-PAGE-PROFILE-BUTTON",
    "by": By.XPATH,
    "value": '//h4/../div[3]/button',
}
