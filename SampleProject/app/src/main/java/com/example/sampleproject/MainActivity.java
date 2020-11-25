package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class MainActivity extends AppCompatActivity
{
    ImageView img;
    Animation rotateAnim;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.userName);
        img=findViewById(R.id.playButton);
        img.setClickable(true);
        rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        final Intent intent=new Intent(MainActivity.this,LevelList.class);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img.startAnimation(rotateAnim);
                try {
//                    TimeUnit.SECONDS.sleep(5);
//                    startActivity(intent);
                }catch(Exception ex){
                    Log.e("MainActivity", "Animation time out ");
                }

            }
        });

        rotateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Bundle bundle=new Bundle();
        bundle.getString("UserName",editText.getText().toString());
        intent.putExtras(bundle);
        
    }

    //return home on back pressed
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


}