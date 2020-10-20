package com.example.demowaterresource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class End extends AppCompatActivity {
    TextView HyperLink;
    Button image;

    TextView download;
    Spanned Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);


        image = (Button)findViewById ( R.id.image2 );
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = "Flood";
                Intent intent = new Intent(End.this, login_page.class);
                intent.putExtra("choice",choice);
                startActivity(intent);
            }
        });

        ((TextView) findViewById(R.id.download)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) findViewById(R.id.download)).setText(Html.fromHtml(getResources().getString(R.string.download)));

    }
    
    
}
       /* @Bind(R.id.download)
                download = (TextView) findViewById(R.id.download);
        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ButterKnife.bind(this, view);
            download.setMovementMethod(LinkMovementMethod.getInstance());
        }

*/