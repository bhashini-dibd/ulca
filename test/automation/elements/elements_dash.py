from selenium.webdriver.common.by import By

# elements_data-in-ULCA-websites
#         element['name'] ->  name of the element
#         element["by"] ->  selector(eg:By.XPATH,By.ID,By.TAG_NAME,)
#         element["value"] ->  value of the selector

# dashboard-page-elements
DASH_PROFILE_BTN = {
    "name": "DASHBOARD-PAGE-PROFILE-BUTTON",
    "by": By.XPATH,
    "value": '//*[@id="root"]/header/div/div/div[3]/button',
}
DASH_LOGOUT_BTN = {
    "name": "DASHBOARD-PAGE-LOGOUT-BUTTON",
    "by": By.XPATH,
    "value": "//*[@id='data-set']/div[3]/ul",
}
DASH_DTYPE_SLCT_BTN = {
    "name": "DASHBOARD-DATATYPE-SELECT-BUTTON",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/header/div/div/div[1]/button',
}
DASH_DTYPE_CHOSE_BTN = {
    "name": "DASHBOARD-DATATYPE-CHOOSE-BUTTON=",
    "by": By.XPATH,
    "value": '//*[@value="{0}"]',
}
DASH_CHART_SRCLANG_INP = {
    "name": "DASHBOARD-PARALLEL-SOURCE-LANG-INPUT",
    "by": By.ID,
    "value": "source",
}
DASH_CHART_SVG = {
    "name": "DASHBOARD-CHART-SVG-GRAPH",
    "by": By.TAG_NAME,
    "value": "svg",
}
DASH_CHART_LNAMES_LI = {
    "name": "DASHBOARD-CHART-LANGUAGE-NAMES-LIST",
    "by": By.XPATH,
    "value": '//*[@class="recharts-layer recharts-cartesian-axis-tick"]',
}
DASH_CHART_LRECTS_LI = {
    "name": "DASHBOARD-CHART-RECTANGLE-LIST",
    "by": By.XPATH,
    "value": '//*[@class="recharts-layer recharts-bar-rectangle"]',
}
DASH_CHART_LCOUNTS_LI = {
    "name": "DASHBOARD-CHART-LANGUAGE-COUNT-LIST",
    "by": By.XPATH,
    "value": "//*[@class='recharts-text recharts-label']",
}
DASH_CHART_TOTCNT_TXT = {
    "name": "DASHBOARD-DATATYPE-TOTAL-COUNT-H6",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/header/div/div/div[2]/h6',
}
DASH_CHART_SRCCNT_TXT = {
    "name": "DASHBOARD-DATATYPE-SRC-LANG-COUNT-H6",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/div[1]/div/div/h6',
}
DASH_CHART_GROUPCOLLM_BTN = {
    "name": "DASHBOARD-GROUPBY-COLLECTION-BUTTON",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/header/div/div/div[4]/div/div/button[2]',
}
DASH_CHART_GROUPSUBM_BTN = {
    "name": "DASHBOARD-GROUPBY-SUBMITTER-BUTTON",
    "by": By.XPATH,
    "value": '//*[@id="root"]/div/div/header/div/div/div[4]/div/div/button[3]',
}
