#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jul 24 10:45:14 2021.

@author: dhiru579 @ Tarento.
"""
import config
import sys
from selenium import webdriver


SUPPORTED_BROWSER = ["chrome", "firefox", "opera"]


def chrome_driver_func():
    """
    chrome_driver_func starts the chrome browser with chromedriver.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    try:
        options = webdriver.chrome.options.Options()
        options.add_argument("--no-sandbox")
        if config.BROWSER_HEADLESS_MODE:
            options.add_argument("--headless")
        options.add_argument("--display=:0")
        options.add_argument("--disable-dev-shm-usage")
        options.add_argument("--disable-application-cache")
        options.add_argument("--disable-extensions")
        options.add_argument("--start-maximized")
        options.add_argument("--log-level=3")
        prefs = {
            "profile.password_manager_enabled": False,
            "credentials_enable_service": False,
        }
        options.add_experimental_option("prefs", prefs)
        driver = webdriver.Chrome(config.CHROME_DRIVER_PATH, options=options)
        print("#Using Google Chrome")
        return driver
    except Exception:
        print("#Browser not working - Google Chrome")
        print("[check chrome and chromedriver is installed.]")
        return None


def firefox_driver_func():
    """
    firefox_driver_func starts the firefox browser with geckodriver.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    try:
        options = webdriver.firefox.options.Options()
        options.add_argument("--no-sandbox")
        if config.BROWSER_HEADLESS_MODE:
            options.add_argument("--headless")
        options.add_argument("--display=:0")
        options.add_argument("--disable-dev-shm-usage")
        options.add_argument("--disable-application-cache")
        options.add_argument("--disable-extensions")
        options.add_argument("--start-maximized")
        driver = webdriver.Firefox(
            executable_path=config.FIREFOX_DRIVER_PATH, options=options)
        print("#using Mozilla Firefox")
        return driver
    except Exception:
        print("#Browser not working - Mozilla Firefox")
        print("[check firefox and geckodriver is installed.]")
        return None


def opera_driver_func():
    """
    opera_driver_func starts the opera browser with operadriver.

    NOTE - headless mode(no-window-display)

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    try:
        options = webdriver.opera.options.Options()
        driver = webdriver.Opera(
            executable_path=config.OPERA_DRIVER_PATH, options=options)
        print("#using Opera")
        return driver
    except Exception:
        print("#Browser not working - Opera")
        print("[check opera and operadriver is installed.]")
        return None


def load_driver(browser_name):
    """
    load_driver loads the input browser.

    NOTE - if browser not supported then will exit.

    Parameters
    ----------
    browser_name : str
        name of the browser.

    Returns
    -------
    driver : selenium.driver
        A Browser window object.

    """
    if browser_name.lower() in SUPPORTED_BROWSER:
        if browser_name.lower() == "chrome":
            driver = chrome_driver_func()
        elif browser_name.lower() == "firefox":
            driver = firefox_driver_func()
        elif browser_name.lower() == "opera":
            driver = opera_driver_func()
    else:
        print("Browser \""+browser_name+"\" not Supported"
              + "Browser-Support = " + SUPPORTED_BROWSER)
        sys.exit(0)
    if driver is None:
        sys.exit(0)
    return driver


def close_driver(driver):
    """
    close_driver closes the driver.

    Parameters
    ----------
    driver : selenium.driver
        A Browser window object.

    Returns
    -------
    None.

    """
    driver.close()
    driver.quit()
    return None
