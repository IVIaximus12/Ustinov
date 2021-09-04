package IVIaximus12.ustinov_lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnLeft;
    private ImageButton btnRight;
    private ImageView mainGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getGif();
    }

    private void initView() {
        btnLeft = findViewById(R.id.imageButtonLeft);
        btnRight = findViewById(R.id.imageButtonRight);
        mainGif = findViewById(R.id.image_gif);
    }

    private void getGif() {
        String url = "https://cdn.fishki.net/upload/post/201406/05/1275160/giphy-9.gif";
        Glide.with(this)
                .asGif()
                .load(url)
                .placeholder(R.drawable.kitty_placeholder)
                .error(R.drawable.kitty_placeholder_error)
                .into(mainGif);
    }
}