from .test_public_website import test_public_website
from .test_asr import test_asr_record
from .test_leaderboard import test_leaderboard
from .test_cards import test_cards
from .test_elements import test_elements_with_browser

def perform_testing_all(login,driver):
    #public-pages-testing
    driver=test_asr_record(driver)
    driver=test_public_website(driver)
    driver=test_leaderboard(driver)
    driver=test_cards(driver,show_str=True)
    status,driver=login(driver)
    if status:
        #after-login-functions
        driver=test_elements_with_browser(driver)
    return driver
    
def perform_testing_partly(login,driver):
    #public-pages-testing
    #driver=test_asr_record(driver)
    driver=test_public_website(driver)
    driver=test_leaderboard(driver)
    driver=test_cards(driver,show_str=False)
    status,driver=login(driver)
    if status:
        #after-login-functions
        driver=test_elements_with_browser(driver)
    return driver
