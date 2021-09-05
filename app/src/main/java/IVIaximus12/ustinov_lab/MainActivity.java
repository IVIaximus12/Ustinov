package IVIaximus12.ustinov_lab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    //private properties

    private ImageButton btnLeft;
    private ImageButton btnRight;
    private ImageView mainGif;
    private TextView textView;
    private ArrayList<PostModel> postModels = new ArrayList<>();
    private int currentNumberModel = -1;
    private View.OnClickListener oclBtnRight;
    private View.OnClickListener oclBtnLeft;
    private View.OnClickListener oclRepBtn;
    private LinearLayout noInternetLayout;
    private ConstraintLayout mainLayout;
    private TextView repeatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getRandomPost();

        initOnClickListener();
        setOnClickListener();
    }


    //private methods

    private void initView() {
        btnLeft = findViewById(R.id.imageButtonLeft);
        btnLeft.setEnabled(false);
        btnRight = findViewById(R.id.imageButtonRight);
        mainGif = findViewById(R.id.image_gif);
        textView = findViewById(R.id.post_description);

        noInternetLayout = findViewById(R.id.noInternetLayout);
        mainLayout = findViewById(R.id.main_layout);
        repeatView = findViewById(R.id.repeatButton);
    }

    private void initOnClickListener() {
        oclBtnRight = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentNumberModel == 0) {
                    btnLeft.setEnabled(true);
                }

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

        oclBtnLeft = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNumberModel == 1) {
                    btnLeft.setEnabled(false);
                }
                currentNumberModel--;
                PostModel post = postModels.get(currentNumberModel);
                textView.setText(post.description);
                getGif(post.gifURL);
            }
        };

        oclRepBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    hideMainViews(false);
                    getRandomPost();
                }
            }
        };
    }

    private void setOnClickListener() {
        btnRight.setOnClickListener(oclBtnRight);
        btnLeft.setOnClickListener(oclBtnLeft);
        repeatView.setOnClickListener(oclRepBtn);
    }

    private boolean isOnline() {
        return MyConnectivityManager.isOnline(this);
    }

    private void getGif(String url) {
        Glide.with(this)
                .asGif()
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.kitty_placeholder)
                .error(R.drawable.kitty_placeholder_error)
                .into(mainGif);
    }

    private void hideMainViews(boolean flag) {
        if (flag) {
            noInternetLayout.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.GONE);
        } else {
            noInternetLayout.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
        }
    }

    private void getRandomPost() {
        if (!isOnline()) {
            hideMainViews(true);
            return;
        }

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