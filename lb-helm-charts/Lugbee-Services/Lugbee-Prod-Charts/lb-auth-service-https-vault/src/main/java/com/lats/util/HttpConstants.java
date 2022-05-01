package com.lats.util;

public class HttpConstants {

    // HTTP RESPONSE STATUS
    public static final String SUCCESS_STATUS = "success";
    public static final String ERROR_STATUS = "Error";
    public int x = 99;

    // HTTP RESPONSE STATUS CODE
    public static int SUCCESS_STATUS_CODE = 200;
    public static int INTERNAL_SERVER_ERROR_STATUS_CODE = 500;
    public static int CONTENT_NOT_FOUND_STATUS_CODE = 204;
    public static int FORBIDDEN_STATUS_CODE = 403;
    public static int UNAUTHORIZED_STATUS_CODE = 401;
    public static int BAD_REQUEST_STATUS_CODE = 400;
    public static int SERVICE_UNAVAILABLE_STATUS_CODE = 502;

    // TOKEN MESSAGES
    public static final String TOKEN_VERIFIED = "180";
    public static final String NULL_POINTER = "502";
    public static final String NO_TOKEN_FOUND = "181";
    public static final String NO_TOKEN_CREATED = "182";
    public static final String TOKEN_REMOVED = "183";
    public static final String TOKEN_EXPIRED = "184";
    public static final String INVALID_SIGNATURE = "185";
    public static final String INVALID_TOKEN = "186";
    public static final String INVALID_OAUTH2_TOKEN = "189";
    public static final String NULL_TOKEN = "187";
    public static final String TOKEN_GENERATED = "188";


    //CONTROLLER MESSAGES
    public static final String UNAUTHENTICATED_USER = "503";
    public static final String USER_NOT_EXSITS = "504";
    public static final String TECHNICAL_ERROR = "804";
    public static final String INVALID_INPUT = "801";
    public static final String INVALID_TOKEN_TYPE = "802";
    public static final String SERVICE_UNAVAILABLE = "803";
    public static final String INVALID_EMAILID = "505";
    public static final String INVALID_CLIENTID = "506";
    public static final String INVALID_CLIENTTYPE = "507";
    public static final String INVALID_FIRST_NAME = "501";
    public static final String INVALID_LAST_NAME = "508";
    public static final String INVALID_ACCCOUNT_TYPE = "509";
    public static final String USER_ALREADY_EXSITS = "510";
    public static final String ACCOUNT_INACTIVE = "511";
    public static final String VERIFICATION_MAIL_NOT_SENT = "512";
    public static final String SIGNUP_SUCCESSFULL = "524";
    public static final String MOBILE = "538";
    public static final String MOBILE_VERIFIED = "525";
    public static final String ACCOUNT_STATUS_UPDATED = "526";
    public static final String HOST_VERIFICATION_CHANGED = "527";
    public static final String PROFILE_UPDATED = "528";
    public static final String MOBILE_UPDATED = "529";
    public static final String ACCOUNT_DEACTIVATED = "530";
    public static final String SAME_PASSWORD = "531";
    public static final String INCORRECT_PASSWORD = "532";
    public static final String PASSWORD_CHANGED = "533";
    public static final String EMAIL_IS_NULL = "534";
    public static final String NO_PASSWORD = "535";
    public static final String MOBILENO_IS_NULL = "536";
    public static final String EMAIL_NOT_VERIFIED = "582";

    // ACCOUNT DOESN'T EXISTS
    public static final String ACCOUNT_NOT_EXISTS = "537";


    public static final String INVALID_MESSAGE_CODE = "INVALID_MESSAGE_CODE";


}
