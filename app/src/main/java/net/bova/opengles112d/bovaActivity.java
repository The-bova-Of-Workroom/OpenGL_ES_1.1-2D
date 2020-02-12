package net.bova.opengles112d;

import android.app.Activity;

import android.os.Bundle;


public class bovaActivity extends Activity {
    private viewOpenGL view;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        view= new viewOpenGL(this);
        setContentView(view);
    }

    @Override protected void onResume() {
        super.onResume();

        view.onResume();
    }

    @Override protected void onPause() {
        super.onPause();

        view.onPause();
    }

    @Override  protected void onDestroy() {
        super.onDestroy();
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
    }


}
