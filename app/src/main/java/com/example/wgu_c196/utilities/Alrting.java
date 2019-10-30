package com.example.wgu_c196.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
//TODO
public class Alrting extends BroadcastReceiver {
    @Override
    public void onReceive(Context contxt, Intent intent) {
        Toast.makeText(contxt, intent.getStringExtra("param"), Toast.LENGTH_SHORT).show();
    }
}
