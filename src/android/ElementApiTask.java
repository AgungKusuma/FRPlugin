package com.element.plugin;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;

import com.element.camera.Capture;
import com.element.camera.ElementSDK;
import com.google.gson.Gson;

import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ElementApiTask extends AsyncTask<Object, Void, Void> {

    private static final MediaType TYPE = MediaType.parse("application/json; charset=utf-8");

    private final ApiTaskCallback apiTaskCallback;

    private final Request.Builder builder;

    public ElementApiTask(ApiTaskCallback apiTaskCallback, String endpoints) {
        Context context = apiTaskCallback.getContext();
        this.apiTaskCallback = apiTaskCallback;
        this.builder = new Request.Builder().url("https://10.20.226.69:1501/".concat(endpoints)); // TODO: replace this with your base address

        builder.addHeader("apiKey", "ISlhCOVBPpZYhStiCXJOJ7XdnTsAMPDsFYhmPCkyooTtclCL91gE6fzWZiwHSoSU@id-fm"); // TODO: Replace this with your api key
        builder.addHeader("appVersion", "1.0");
        builder.addHeader("os", "ANDROID");
        builder.addHeader("appId", context.getPackageName());
        builder.addHeader("deviceModel", Build.MODEL);
        builder.addHeader("sdkVersion", "1.0");
    }

    @Override
    protected Void doInBackground(Object... params) {
        OkHttpClient client = new OkHttpClient();

        try {
            FmRequest fmRequest = new FmRequest();
            fmRequest.userId = (String) params[0];
            fmRequest.setImages((Capture[]) params[1]);
            if (params.length >= 3) {
                fmRequest.name = (String) params[2];
            }

            String json = new Gson().toJson(fmRequest);
            RequestBody requestBody = RequestBody.create(TYPE, json);
            builder.post(requestBody);

            Request request = builder.build();
            Response response = client.newCall(request).execute();

            apiTaskCallback.onResult(response);

        } catch (Exception e) {
            apiTaskCallback.onException(e.getMessage());
        }

        return null;
    }

    private static class FmRequest {
        String userId;
        String name = "";
        double latitude;
        double longitude;
        String timeZone;
        Image[] images;

        FmRequest() {
            Location location = ElementSDK.getLocation();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            timeZone = TimeZone.getDefault().getID();
        }

        void setImages(Capture[] imageData) {
            images = new Image[imageData.length];
            int i = 0;
            for (Capture capture : imageData) {
                images[i] = new Image();
                images[i].index = i;
                images[i].modality = "face";
                images[i].mode = "gaze";
                images[i].data = Base64.encodeToString(capture.data, Base64.DEFAULT);
                images[i].tag = capture.tag;
                i++;
            }
        }
    }

    public static class Image {
        int index;
        String modality;
        String mode;
        String data;
        String tag;
    }

    public static class FmResponse {
        public String displayMessage;
        public double confidenceScore;
    }

    public static class ServerMessage {
        public String message;
    }

    public interface ApiTaskCallback {
        Context getContext();

        void onResult(Response response) throws Exception;

        void onException(String message);
    }
}
