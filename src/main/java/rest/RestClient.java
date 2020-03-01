package rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RestClient {

    private static RestAPI client = null;


    public static RestAPI getClient() {
        if (client == null) {
            createClient();
        }
        return client;
    }

    private static void createClient() {
        String API_BASE_URL = "http://itunes.apple.com";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(JacksonConverterFactory.create());

        Retrofit retrofit = builder
                .client(httpClient.build())
                .build();

        client = retrofit.create(RestAPI.class);
    }

}
