package com.example.shape;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main2Activity extends AppCompatActivity {

    private String string_canvas;
    private Bitmap bitmap_canvas;
    private ImageView put_image_canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        put_image_canvas = (ImageView) findViewById(R.id.put_image_canvas);

        Intent intent = getIntent();
        if(intent != null){
            string_canvas = intent.getStringExtra("path");
            try {
                File f = new File(string_canvas, "cartographie.png");
                bitmap_canvas = BitmapFactory.decodeStream(new FileInputStream(f));
                put_image_canvas.setImageBitmap(bitmap_canvas);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
