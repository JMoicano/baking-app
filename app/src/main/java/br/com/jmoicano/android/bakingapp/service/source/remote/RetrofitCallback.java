package br.com.jmoicano.android.bakingapp.service.source.remote;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallback<T> implements Callback<T> {

    private MutableLiveData<Resource<T>> resource;
    private String errorTag;

    public RetrofitCallback(MutableLiveData<Resource<T>> resource, String errorTag) {
        this.resource = resource;
        this.errorTag = errorTag;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.body() != null) {
            if (response.isSuccessful()) {
                resource.postValue(Resource.success(response.body()));
            } else {
                if (response.errorBody() != null) {
                    ErrorResponse error = null;
                    if (response.code() >= 400 && response.code() < 500) {
                        error = new Gson().fromJson(
                                response.errorBody().charStream(),
                                ErrorResponse.class
                        );
                    } else {
                        error = new ErrorResponse(response.code(),
                                "There was something worng in the server!");
                    }
                    resource.postValue(Resource.<T>error(error));
                }
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        ErrorResponse error;
        if (t instanceof IOException) {
            error = new ErrorResponse(503,
                    "Connection problem!"
            );
        } else {
            error = new ErrorResponse(500,
                    "Something went wrong!");
        }
        Log.e(errorTag, "onFailure: " + t.getMessage(), t);
        resource.postValue(Resource.<T>error(error));
    }
}
