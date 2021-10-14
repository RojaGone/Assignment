package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class commentAtivity extends AppCompatActivity {
CircleImageView user_img;
EditText add_comment;
Button post_comment_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_ativity);
        getSupportActionBar().hide();

        user_img = findViewById(R.id.user_img);
        add_comment = findViewById(R.id.add_comment);
        post_comment_btn = findViewById(R.id.post_comment_btn);

        Uri myUri = Uri.parse("content://media/external/images/media/31111");
        user_img.setImageURI(myUri);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        List<commentModel> jsonResponses = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://app.fakejson.com/q/5XdtyZTZ?token=QC8xlKAlI2nLRW6w0xIOmg", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = response.getJSONArray("postlist");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String user_img = jsonObject.getString("user_profile");
                        String username = jsonObject.getString("username");
                        String comment = jsonObject.getString("comment");
                        Log.e("com","comment : "+user_img);
                        Log.e("com","comment : "+username);
                        Log.e("com","comment : "+comment);
                        jsonResponses.add(new commentModel(user_img,username,comment));
                    }
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_comments);
                    commentAdapter adapter = new commentAdapter(jsonResponses,getApplicationContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("helo","error : "+error);
            }
        });
        requestQueue.add(jsonObjectRequest);

        post_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postUrl = "https://app.fakejson.com/q";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JSONObject postData = new JSONObject();
                try {
                    String get_comment = add_comment.getText().toString();
                    postData.put("token", "QC8xlKAlI2nLRW6w0xIOmg");
                    JSONObject dataData = new JSONObject();
                    dataData.put("comment", get_comment);
                    postData.put("data", dataData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("helo","comment POST response : "+response);
                        Toast.makeText(commentAtivity.this, "successfully added your comment", Toast.LENGTH_SHORT).show();
                        add_comment.setText("");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("helo","re : "+error.toString());
                    }
                });
                requestQueue.add(jsonObjectRequest1);
            }
        });
    }
}