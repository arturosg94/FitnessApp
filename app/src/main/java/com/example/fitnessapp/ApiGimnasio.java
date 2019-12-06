package com.example.fitnessapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class ApiGimnasio {

    private Context mContext;

    private final String APICONNECTION="http://websdpcp01/Nivel2WS/api/CallaoStandardized_ProductionLive/";
    //METODOS ABSTRACTOS QUE SERAN LLAMDOS AL INSTANCIAR EL OBJETO
    public abstract void onReturnResult(JSONObject JSONResult);
    public abstract void onReturnResult(JSONArray JSONResult);
    public abstract void onReturnResult(String result);
    public abstract void onError(String result);

    public void ServerConection(String Method, final JSONObject params,final String table) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                APICONNECTION+Method,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        try {
                            if(isJsonObject(response)){
                                try {
                                    JSONObject obj=new JSONObject(response.toString());
                                    JSONArray arr=obj.getJSONArray(table);
                                    onReturnResult(arr);
                                }catch (JSONException e) {
                                    JSONObject obj=new JSONObject(response.toString());
                                    onReturnResult(obj);
                                }
                            }else if(isJsonArray(response)){
                                JSONArray JA=new JSONArray(response);
                                onReturnResult(JA);
                            }else{
                                onReturnResult(response.toString());
                            }
                        } catch (JSONException e) {
                            onError(e.getMessage());
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String msg=error.getMessage();
                        onError(error.getMessage());
                        //Failure Callback
                    }
                })
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        // Adding the request to the queue along with a unique string tag
        // .getInstance().addToRequestQueue(jsonObjReq, "headerRequest");

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(jsonObjReq);
    }

    private boolean isJsonArray(Object object){
        boolean a=false;
        try {
            JSONArray ja=new JSONArray(object);
            a=true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  a;
    }
    private boolean isJsonObject(Object object){
        boolean a=false;
        try {
            JSONObject ja=new JSONObject(object.toString());
            a=true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  a;
    }
}
