package realimage.stackoverflow.helpers;

/**
 * Created by Venkatesh
 * venkatselva8@gmail.com
 */

public class Config {

    public static final String OAUTH_CALLBACK_SCHEME = "oauthflow-stackoverflow.com";
    public static final String OAUTH_CALLBACK_HOST = "callback";
    public static final String OAUTH_CALLBACK_URL = Config.OAUTH_CALLBACK_HOST + "://" + Config.OAUTH_CALLBACK_SCHEME;

    public static final String CLIENT_ID = "8421";
    public static final String CONSUMER_SECRET = "4JwgAwYU0JTOjAU6gRKFIA((";

    public static final String CURRENT_SORT = "CURRENT_SORT";
    public static final String SORT_ACTIVITY = "activity";
    public static final String SORT_VOTES = "votes";
    public static final String SORT_CREATION = "creation";
    public static final String SORT_HOT = "hot";
    public static final String SORT_WEEK = "week";
    public static final String SORT_MONTH = "month";

    public static final String API_KEY = "nl0BolV)b28oWoFaND5VRw((";
    public static final String API_FILTER_FOR_QUESTIONS = "!)Q2ANGPVz)tSvZguWTzAF_jz";
    public static final String STACKOVERFLOW = "stackoverflow";
    public static final String DESC = "desc";
    public static final String PREF_ACCESS_TOKEN = "PREF_ACCESS_TOKEN";
    public static final String EMPTY_ACCESS_TOKEN = "EMPTY_ACCESS_TOKEN";

    public static final String TO_LOAD_URL = "TO_LOAD_URL";
    public static final String PREFS_NAME = "PREF_STACK_OVERFLOW";
    public static String ACCESS_CODE_FROM_WEBVIEW = "";   // Default is "" (empty)
    public static boolean IS_AVAILABLE_ACCESS_CODE_FROM_WEBVIEW = false;   // Default is false


}
