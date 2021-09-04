package IVIaximus12.ustinov_lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnLeft;
    private ImageButton btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        btnLeft = findViewById(R.id.imageButtonLeft);
        btnLeft = findViewById(R.id.imageButtonRight);
    }


}