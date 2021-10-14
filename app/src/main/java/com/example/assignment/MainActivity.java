package com.example.assignment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    EditText description_txt;
    ImageView gallary;
    CircleImageView user_prof;
    public static final int IMAGE_PICKER_SELECT = 100;
        String imageURI;
//    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        description_txt = findViewById(R.id.description_txt);
//        videoView = findViewById(R.id.videoView);
        gallary = findViewById(R.id.gallary);
        user_prof = findViewById(R.id.user_prof);

        Uri myUri = Uri.parse("content://media/external/images/media/31111");
        user_prof.setImageURI(myUri);
        description_txt.setText("");
        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runtimePermission();
            }
        });

        RequestQueue requestQueue;
        List<MyListDataModel> jsonResponses = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://app.fakejson.com/q/5XdtyZTZ?token=QC8xlKAlI2nLRW6w0xIOmg", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("postlist");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String username = jsonObject.getString("username");
                        String user_dob = jsonObject.getString("DOB");
                        String like = jsonObject.getString("like");
                        String comment = jsonObject.getString("comment");
                        String user_img = jsonObject.getString("user_profile");
                        String user_post_img = jsonObject.getString("post_image");
                        String user_post_video = jsonObject.getString("post_video");
                        String description = jsonObject.getString("description");

                        jsonResponses.add(new MyListDataModel(username,user_dob,like,comment, user_img,user_post_img,user_post_video,description));
                    }

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
                    MyListAdapter adapter = new MyListAdapter(jsonResponses,getApplicationContext());
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

    }

    private void runtimePermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                gallaryIntent();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "permission is denied...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }

    private void gallaryIntent() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        pickIntent.setType("image/* video/*");
        startActivityForResult(pickIntent,IMAGE_PICKER_SELECT);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Uri selectedMediaUri = data.getData();
            if (selectedMediaUri.toString().contains("image")) {
                //handle image
                if(requestCode == IMAGE_PICKER_SELECT){
                    imageURI = data.getData().toString();
                    Log.e("helo","image uri : "+imageURI);
                    String txt_description = description_txt.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), show_user_post.class);
                    intent.putExtra("img_uri",imageURI);
                    intent.putExtra("descri",txt_description);
                    startActivity(intent);
                    Log.e("helo1234","img link is selected :"+data.getData());
                }
            } else if (selectedMediaUri.toString().contains("video")) {
                //handle video
                String videoURI = data.getData().toString();

                String txt_description = description_txt.getText().toString();
                Intent intent = new Intent(getApplicationContext(), show_user_post.class);
                intent.putExtra("video_uri",videoURI);
                intent.putExtra("descri",txt_description);
                startActivity(intent);

                Log.e("helo1","video link is selected :"+data.getData());
            }
        }
    }
}

