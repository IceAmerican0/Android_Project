package com.aoslec.androidproject.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.aoslec.androidproject.Bean.WeatherBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask extends AsyncTask<Integer,String,Object> {
    Context context=null;
    String mAddr=null;
    ProgressDialog progressDialog=null;
    ArrayList<WeatherBean> weathers;

    //Network Task를 검색, 입력, 수정, 삭제 구분없이 하나로 사용하기 위해 생성자 변수 추가
    String where=null;

    public NetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.weathers = new ArrayList<WeatherBean>();
        this.where = where;
    }

    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialog");
        progressDialog.setMessage("Get . . . .");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer=new StringBuffer();
        InputStream inputStream=null;
        InputStreamReader inputStreamReader=null;
        BufferedReader bufferedReader=null;
        String result=null;

        try{
            URL url=new URL(mAddr);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream=httpURLConnection.getInputStream();
                inputStreamReader=new InputStreamReader(inputStream);
                bufferedReader=new BufferedReader(inputStreamReader);

                while(true){
                    String strline=bufferedReader.readLine();
                    if(strline==null) break;
                    stringBuffer.append(strline+"\n");
                }
                if(where.equals("select")){
                    parserSelect(stringBuffer.toString());
                }else{
                    result=parserAction(stringBuffer.toString());
                }
            }else{

            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(bufferedReader!=null) bufferedReader.close();
                if(inputStreamReader!=null) inputStreamReader.close();
                if(inputStream!=null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(where.equals("select")){
            return weathers;
        }else{
            return result;
        }
    }

    private String parserAction(String str){
        String returnValue=null;
        try{
            JSONObject jsonObject=new JSONObject(str);
            returnValue=jsonObject.getString("result");
        }catch(Exception e){
            e.printStackTrace();
        }

        return returnValue;
    }

    private void parserSelect(String str){
        try{
            JSONObject jsonObject=new JSONObject(str);
            JSONArray jsonArray=new JSONArray(jsonObject.getString("response"));
            weathers.clear();

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=(JSONObject)jsonArray.get(i);
                JSONArray jsonArray1=new JSONArray(jsonObject1.getString("periods"));
                for(int j=0;j<jsonArray1.length();j++) {
                        JSONObject jsonObject2=(JSONObject)jsonArray1.get(j);
                        String dateTimeISO = jsonObject2.optString("dateTimeISO", "no");
                        int maxTempC = jsonObject2.optInt("maxTempC", 0);
                        int minTempC = jsonObject2.optInt("minTempC", 0);
                        int avgTempC = jsonObject2.optInt("avgTempC", 0);
                        int tempC = jsonObject2.optInt("tempC", 0);
                        int maxFeelslikeC = jsonObject2.optInt("maxFeelslikeC", 0);
                        int minFeelslikeC = jsonObject2.optInt("minFeelslikeC", 0);
                        int avgFeelslikeC = jsonObject2.optInt("avgFeelslike", 0);
                        int feelslikeC = jsonObject2.optInt("feelslikeC", 0);
                        int pop = jsonObject2.optInt("pop", 0);
                        String weather = jsonObject2.optString("weather", "no");
                        String icon = jsonObject2.optString("icon", "no");

                        WeatherBean list = new WeatherBean(dateTimeISO, maxTempC, minTempC, avgTempC, tempC, maxFeelslikeC, minFeelslikeC,
                                avgFeelslikeC, feelslikeC, pop, weather, icon);
                        weathers.add(list);

                }


            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

