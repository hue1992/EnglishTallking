package com.born2go.englishtalking;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.born2go.englishtalking.adapter.ListCallCenterAdapter;
import com.born2go.englishtalking.entities.CallCenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mListCallCenter;
    private MainActivity thiz;
    private View container;
    private View mList;
    private View mNoInternet;
    private MenuItem sortCallCenter;
    private ListCallCenterAdapter listCallCenterAdapter;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thiz = this;

        container = findViewById(R.id.container);
        mList = findViewById(R.id.mList);
        mNoInternet = findViewById(R.id.mNoInternet);

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mListCallCenter = (RecyclerView) findViewById(R.id.mListCallCenter);
        mListCallCenter.setLayoutManager(new LinearLayoutManager(this));

        refresh.setOnRefreshListener(this);

        getListCallCenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        sortCallCenter = menu.findItem(R.id.sortCallCenter);
        sortCallCenter.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //switch id action action bar
        switch (id) {
            case R.id.sortCallCenter:
                sortCallCenter();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private void sortCallCenter() {
        if (listCallCenterAdapter != null) {
            listCallCenterAdapter.sort();
        }
    }

    public void gotoDetails(CallCenter callCenter) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("callCenter", callCenter);
        startActivity(intent);
    }

    public void callNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant

            return;
        }
        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void getListCallCenter() {
        requestPermissionPhone();
        if (isNetworkConnected()) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            //progressDialog.setTitle("Please waiting");
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            //http://202.62.111.231:12086/779/contents
            String url = "http://222.255.29.25:18090/englishtalk/api/cc_list/list";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (response != null) {
                        Log.d(TAG, "response:" + response);
                        List<CallCenter> callCenters = new ArrayList<>();
                        int leght = response.length();
                        for (int i = 0; i < leght; i++) {
                            JSONObject jsonObject = response.optJSONObject(i);
                            CallCenter callCenter = parseFormJson(jsonObject);
                            if (callCenter != null) {
                                callCenters.add(callCenter);
                            }
                        }
                        if (callCenters.size() > 0) {
                            listCallCenterAdapter = new ListCallCenterAdapter(thiz, callCenters);
                            mListCallCenter.setAdapter(listCallCenterAdapter);
                            sortCallCenter.setVisible(true);
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(thiz, "No call center", Toast.LENGTH_SHORT).show();
                            sortCallCenter.setVisible(false);
                            progressDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(thiz, "No Call center", Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(thiz, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
            ESFSingleton.getInstance().getRequestQueue().add(jsonArrayRequest);
        } else {
            Snackbar snackbar = Snackbar
                    .make(container, "Failed to connect to server.Please try again.", Snackbar.LENGTH_INDEFINITE).setAction("RELOAD", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //reload activity
                            Intent refresh = new Intent(thiz, MainActivity.class);
                            startActivity(refresh);//Start the same Activity
                            finish();
                        }
                    });

            snackbar.show();

        }
    }

    private void requestPermissionPhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant

            return;
        }

    }

    private CallCenter parseFormJson(JSONObject jsonObject) {
        try {
            String videoId = "";
            String ccName = jsonObject.getString("ccName");
            String avatar = jsonObject.getString("avatar");

            String partnerId = jsonObject.getString("partnerId");
            String msisdn = jsonObject.getString("msisdn");
            long totalTime = jsonObject.getLong("totalTime");
            long totalPoint = jsonObject.getLong("totalPoint");

            int active = jsonObject.getInt("active");
            int status = jsonObject.getInt("status");
            String timeLimit = jsonObject.getString("timeLimit");
            String url = jsonObject.getString("url");

            if (!url.trim().isEmpty() && !url.equals("null")) {
                Log.d(TAG, "url:" + url);
                videoId = jsonObject.getString("url").split("=")[1];
            }
            String cc_id = jsonObject.getString("cc_id");
            String grade = jsonObject.getString("rate");

            CallCenter callCenter = new CallCenter(ccName, avatar, partnerId, msisdn, totalTime, totalPoint, active, status, timeLimit, videoId, cc_id, String.valueOf(grade + "/10.0"));
            return callCenter;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onRefresh() {
        refresh.setRefreshing(false);
        getListCallCenter();
    }
}
