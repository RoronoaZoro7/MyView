package com.myview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    private boolean istrue;
    private BouncingMenu bouncingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        if(!istrue){
            bouncingMenu = BouncingMenu.MakMenu(findViewById(R.id.tv), R.layout.bouncing_view);
            bouncingMenu.show();
            istrue = true;
        }else{
            istrue = false;
            bouncingMenu.remoview();
        }

    }
}
