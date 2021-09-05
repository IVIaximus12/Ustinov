package IVIaximus12.ustinov_lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnLeft;
    private ImageButton btnRight;
    private ImageView mainGif;
    private TextView textView;
    private ArrayList<PostModel> postModels = new ArrayList<>();
    private int currentNumberModel = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getRandomPost();
        //getGif();


        View.OnClickListener oclBtnRight = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNumberModel == postModels.size() - 1) {
                    getRandomPost();
                } else {
                    currentNumberModel++;
                    PostModel post = postModels.get(currentNumberModel);
                    textView.setText(post.description);
                    getGif(post.gifURL);
                }

            }
        };

        btnRight.setOnClickListener(oclBtnRight);

        View.OnClickListener oclBtnLeft = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostModel post = postModels.get(currentNumberModel - 1);
                textView.setText(post.description);
                getGif(post.gifURL);
            }
        };

        btnLeft.setOnClickListener(oclBtnLeft);
    }

    private void initView() {
        btnLeft = findViewById(R.id.imageButtonLeft);
        btnRight = findViewById(R.id.imageButtonRight);
        mainGif = findViewById(R.id.image_gif);
        textView = findViewById(R.id.post_description);
    }

    private void getGif(String url) {
        //String url = "https://cdn.fishki.net/upload/post/201406/05/1275160/giphy-9.gif";
        Glide.with(this)
                .asGif()
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.kitty_placeholder)
                .error(R.drawable.kitty_placeholder_error)
                .into(mainGif);
    }

    void getRandomPost() {
        final Request request = new Request.Builder().url("https://developerslife.ru/random?json=true")
                .method("GET", null)
                .build();
        final OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                final PostModel post = gson.fromJson(json, PostModel.class);
                if(!post.gifURL.contains("https")) {
                    post.gifURL = "https".concat(post.gifURL.substring(4));
                }

                postModels.add(post);
                currentNumberModel++;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(post.description);
                        getGif(post.gifURL);
                    }
                });
            }
        });
     }
}