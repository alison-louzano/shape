package com.example.shape;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private CustomView customView;
    private Button but_move;
    private Button but_continue;
    private Boolean first_click = false;
    private Bitmap bitmap_canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customView = (CustomView) findViewById(R.id.custom_view);

        but_move = (Button) findViewById(R.id.but_move);
        but_continue = (Button) findViewById((R.id.but_continue));


        but_move.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(first_click == false){
                    customView.getBooleanClick(true);
                    first_click = true;
                }
                else{
                    customView.getBooleanClick(false);
                    first_click = false;
                }
            }
        });

        but_continue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                bitmap_canvas = customView.getCanvasImage();
                intent.putExtra("path", saveToInternalStorage(bitmap_canvas));
                startActivity(intent);
                finish();
            }
        });
    }


    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "cartographie.png");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}