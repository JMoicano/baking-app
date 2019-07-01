package br.com.jmoicano.android.bakingapp.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;

import br.com.jmoicano.android.bakingapp.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private ServiceGenerator() {
        throw new IllegalArgumentException("Private constructor");
    }

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(BigDecimal.class, new BigDecimalSerializer())
            .create();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
            .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <T> T create(Class<T> serviceClass){
        if(!httpClient.interceptors().contains(logging) && BuildConfig.DEBUG){
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();

        }
        return retrofit.create(serviceClass);
    }

}

