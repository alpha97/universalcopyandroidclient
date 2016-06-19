package io.zillion.universalcopypaste;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jafarnaqvi on 17/05/16.
 */
public class NetworkRequest {

    ProgressDialog asyncdialog;

    private SuccessCallback successCallback;
    private FailureCallback failureCallback;
    private Context context;
    private HashMap params;
    private String url;

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    /*This variable determines wether the req will be cached or not
    * True by default
    * */
    private boolean cache = true;
    private int method;
    private boolean showDialog = false;


    public NetworkRequest(Context context, String url, HashMap params, SuccessCallback successCallback, FailureCallback failureCallback) {

        this.context = context;
        this.params = params;
        this.successCallback = successCallback;
        this.failureCallback = failureCallback;
        this.url = url;
        method = -99;
        asyncdialog = new ProgressDialog(context);
        asyncdialog.setMessage("Please wait...");
        asyncdialog.setCancelable(false);
        asyncdialog.setCanceledOnTouchOutside(false);


    }


    public void execute() {

        if (showDialog)
            asyncdialog.show();


        final String url2 = context.getResources().getString(R.string.ServerIP) + url;

        Log.i("NetWork", "Sending Req to:" + url2);

        if (method == -99)
            method = Request.Method.POST;


        Log.i("Network Req", "Method:" + method);

        StringRequest postRequest = new StringRequest(cache, method, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("NetworkRes", "Response for " + url2 + response);
                        asyncdialog.dismiss();
                        successCallback.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        asyncdialog.dismiss();
                        failureCallback.onResponse();
                    }
                }
        )


        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> paramsTemp = new HashMap<>();
                // the POST parameters:


                if (params == null)
                    return paramsTemp;
                return params;

            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(context).add(postRequest);
    }

    public void setMethod(int method) {
        this.method = method;
    }
}
