package com.app.osgrim;

import android.content.Intent;
import android.view.View;

public class BilanCirListener implements OnItemClickListener {

    private MainActivity mainAct;

    @Override
    public void onClick(View view, int position) {
        mainAct = MainActivity.getInstance();
        Intent intent = new Intent(mainAct, BilanCirActivity.class);
        intent.putExtra("pos", position);
        mainAct.startActivity(intent);
    }
}
