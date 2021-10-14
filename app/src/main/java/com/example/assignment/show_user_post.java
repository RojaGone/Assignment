package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class show_user_post extends AppCompatActivity {
    TextView username,user_dob,description;
    ImageView user_post_img,user_img;
    CircleImageView user_prof;
    VideoView user_post_video;
    Button done_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_post);
        getSupportActionBar().hide();

        username = findViewById(R.id.username);
        user_dob = findViewById(R.id.user_dob);
        description = findViewById(R.id.description);
        user_post_img = findViewById(R.id.user_post_img);
        user_img = findViewById(R.id.user_img);
        user_post_video = findViewById(R.id.user_post_video);
        done_btn = findViewById(R.id.done_btn);

        Intent intent = getIntent();
        String descri = intent.getStringExtra("descri");
        String img_uri = intent.getStringExtra("img_uri");
        String video_uri = intent.getStringExtra("video_uri");
        Log.e("abc","des : "+descri);
        Log.e("abc1","img : "+img_uri);
        Log.e("abc3","video : "+video_uri);

        username.setText("Alok");
        user_dob.setText("10-7-1999");
        description.setText(descri);
        Uri myUri = Uri.parse("content://media/external/images/media/31111");
        user_img.setImageURI(myUri);

        if(img_uri != null){
            user_post_video.setVisibility(View.GONE);
            Uri myUri_post_img = Uri.parse(img_uri);
            user_post_img.setImageURI(myUri_post_img);

        }else{
            user_post_img.setVisibility(View.GONE);
            Uri myUri_post_video = Uri.parse(video_uri);
            user_post_video.setVisibility(View.VISIBLE);
            user_post_video.setVideoURI(myUri_post_video);
            user_post_video.start();
            user_post_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                    }
                });
        }

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postUrl = "https://app.fakejson.com/q";
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JSONObject postData = new JSONObject();
                try {
                    postData.put("token", "QC8xlKAlI2nLRW6w0xIOmg");
                    JSONObject dataData = new JSONObject();
                    dataData.put("username", "Alok");
                    dataData.put("DOB", "10-7-1999");
                    dataData.put("user_profile", "content://media/external/images/media/31111");
                    dataData.put("Description", descri);
                    if(img_uri != null){
                        dataData.put("user_post_img", "img_uri");
                        dataData.put("user_post_video", "");
                    }else{
                        dataData.put("user_post_img", "");
                        dataData.put("user_post_video", video_uri);
                    }
                    postData.put("data", dataData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("helo","POST response : "+response);
                        Toast.makeText(show_user_post.this, "successfully added your post", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent1);
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