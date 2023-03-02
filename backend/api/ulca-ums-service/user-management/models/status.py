import enum


class Status(enum.Enum):

    SUCCESS                 =   {"message" : "Request successful"}
    FAILURE_BAD_REQUEST     =   {"message" : "Request failed"}
    SUCCESS_USR_CREATION    =   {"message" : "User registration successful. Please check your email to complete the verification process."}
    SUCCESS_ACTIVATE_USR    =   {"message" : "User account verified and activated successfully"}
    SUCCESS_USR_LOGIN       =   {"message" : "Logged in successfully"}
    SUCCESS_USR_TOKEN       =   {"message" : "Search is successful"}
    SUCCESS_RESET_PWD       =   {"message" : "Your password has been changed successfully"}
    SUCCESS_FORGOT_PWD      =   {"message" : "Password reset link sent. Please check your email."}
    EMPTY_USR_SEARCH        =   {"message" : "Search result is empty"}
    SUCCESS_USR_SEARCH      =   {"message" : "Search result is ready"}
    SUCCESS_USR_UPDATION    =   {"message" : "User details updated successfully"}
    SUCCESS_USER_APIKEY     =   {"message" : "UserApiKey found successfully"}


   

    