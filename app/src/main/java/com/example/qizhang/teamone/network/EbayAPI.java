package com.example.qizhang.teamone.network;

import android.util.Base64;

import com.example.qizhang.teamone.model.ApplicationToken;
import com.example.qizhang.teamone.model.broswe.SearchResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class EbayAPI {


    /**
     * Use the App ID as your credentials to make Finding API calls in the selected environment.
     * If an eBay member wants to use your application to create listings, manage orders, etc., you'll need to generate a User Token for them
     */
    // zhangqi2062@students.itu.edu producttion
    public static final String APP_ID = "QIZHANG-TeamOne-PRD-82ccbdebc-e7346eb6";
    public static final String DEV_ID = "2bd06d88-eb92-41aa-b077-6a2d2ddb67b7";
    public static final String CERT_ID = "PRD-2ccbdebc59d4-d552-4edf-ac4c-6837";
    public static final String RuName = "QI_ZHANG-QIZHANG-TeamOne-cjhwcl";

    private static final String BASE_URL = "https://api.ebay.com/";
    private static final String PROD_BASE_URL = "https://api.ebay.com/identity/v1/oauth2/token/";

    public static final String APPLICATION_ACCESS_TOKEN_SCOPE =
            "https://api.ebay.com/oauth/api_scope";

    private static EbayService service = null;

    private static EbayService clientTokenService = null;

    public static EbayService getService() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(EbayService.class);
        }

        return service;
    }


    public static EbayService getClientTokenService() {
        if (clientTokenService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PROD_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            clientTokenService = retrofit.create(EbayService.class);
        }
        return clientTokenService;
    }




    public interface EbayService {
        String base = APP_ID + ":" + CERT_ID;
        final static String base64Code = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        @FormUrlEncoded
        @POST(PROD_BASE_URL)
        @Headers({"Content-Type: application/x-www-form-urlencoded",
                "Authorization: Basic UUlaSEFORy1UZWFtT25lLVBSRC04MmNjYmRlYmMtZTczNDZlYjY6DQpQUkQtMmNjYmRlYmM1OWQ0LWQ1NTItNGVkZi1hYzRjLTY4Mzc="})
        Call<ApplicationToken> getApplicationToken(@Field("grant_type") String grant_type,
                                                   @Field("redirect_uri") String ruName,
                                                   @Field("scope") String scope);

        @GET("/buy/browse/v1/item_summary/search")
        Call<SearchResponse> getSearchResult(@Header("Authorization") String token, @Query("q") String keyword, @Query("limit") int limit);


        // Defind End Points
//        @GET("services/search/FindingService/v1?SECURITY-APPNAME=" + APP_ID +
//                "&SERVICE-VERSION=1.0.0" +
//                "&REST-PAYLOAD&categoryId=10181" +
//                "&paginationInput.entriesPerPage=2" +
//                "&RESPONSE-DATA-FORMAT=JSON")
//        Call<FindItemsByCategoryResponse> getSearchRespone(@Query("OPERATION-NAME") String operation_name);
    }
}
