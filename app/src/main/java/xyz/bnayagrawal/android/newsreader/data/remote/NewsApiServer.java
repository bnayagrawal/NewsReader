package xyz.bnayagrawal.android.newsreader.data.remote;

public final class NewsApiServer {

    //Base url and API endpoint
    public static final String BASE_URL = "https://newsapi.org/";
    public static final String BASE_ENDPOINT = "v2/";

    //Endpoints
    public static final String ENDPOINT_TOP_HEADLINES = "top-headlines";
    public static final String ENDPOINT_EVERYTHING = "everything";
    public static final String ENDPOINT_SOURCES = "sources";

    /*
     * Query parameters for {@link ENDPOINT_TOP_HEADLINES} & {@link ENDPOINT_EVERYTHING}
     */

    //The number of results to return per page (request). 20 is the default, 100 is the maximum.
    public static final String QUERY_PARAM_PAGE_SIZE = "pageSize";

    //Use this to page through the results if the total results found is greater than the page size.
    public static final String QUERY_PARAM_PAGE = "page";

    //A comma-separated string of identifiers (maximum 20) for the news sources or blog you want headlines from.
    public static final String QUERY_PARAM_SOURCES = "sources";

    //Keywords or a phrase to search for. advanced search supported for {@link ENDPOINT_EVERYTHING}
    public static final String QUERY_PARAM_QUERY = "q";

    /*
     * Query parameters for all endpoints
     */

    //The 2-letter ISO 3166-1 code of the country you want to get headlines for.
    public static final String ALL_QUERY_PARAM_COUNTRY = "country";

    //The category you want to get headlines for
    public static final String ALL_QUERY_PARAM_CATEGORY = "category";

    /*
     * Query parameters for {@link ENDPOINT_EVERYTHING}
     */

    //A comma-separated string of domains (eg bbc.co.uk, techcrunch.com, engadget.com) to restrict the search to.
    public static final String EVERYTHING_QUERY_PARAM_DOMAINS = "domains";

    //A date and optional time for the oldest article allowed. This should be in ISO 8601 format (e.g. 2018-07-08 or 2018-07-08T08:17:52)
    public static final String EVERYTHING_QUERY_PARAM_FROM = "from";

    //A date and optional time for the newest article allowed. This should be in ISO 8601 format (e.g. 2018-07-08 or 2018-07-08T08:17:52)
    public static final String EVERYTHING_QUERY_PARAM_TO = "to";

    //The 2-letter ISO-639-1 code of the language you want to get headlines for.
    public static final String EVERYTHING_QUERY_PARAM_LANGUAGE = "language";

    //The order to sort the articles in.
    public static final String EVERYTHING_QUERY_PARAM_SORT_BY = "sortBy";

    /*
     * Query parameters for {@link ENDPOINT_SOURCES}
     */

    //The 2-letter ISO-639-1 code of the language you want to get headlines for.
    public static final String SOURCES_QUERY_PARAM_LANGUAGE = "language";

    /*
     * Possible options for some query parameters
     */

    //Possible options for parameter "country"
    public static final String[] POSSIBLE_COUNTRY_OPTIONS = {
            "ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de",
            "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp", "kr", "lt",
            "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs", "ru",
            "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za"
    };

    //Possible options for parameter "category"
    public static final String[] POSSIBLE_CATEGORY_OPTIONS = {
            "general", "business", "entertainment", "health", "science", "sports", "technology"
    };

    //Possible options for parameter "language"
    public static final String[] POSSIBLE_LANUGAGE_OPTIONS = {
            "ar", "de", "en", "es", "fr", "he", "it", "nl", "no", "pt", "ru", "se", "ud", "zh"
    };

    //Possible options for parameter "sortBy"
    public static final String[] POSSIBLE_SORT_BY_OPTIONS = {
            "relevancy", "popularity", "publishedAt"
    };

    /*
     * Status codes returned on request
     */
    public static final String STATUS_OK = "ok";
    public static final String STATUS_ERROR = "error";

    /*
     * News types
     */
    public enum NewsType {
        TOP_HEADLINES, EVERYTHING
    }
}
