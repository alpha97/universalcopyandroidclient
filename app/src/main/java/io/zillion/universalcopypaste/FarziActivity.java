package io.zillion.universalcopypaste;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.zillion.universalcopypaste.utils.UserManager;

/**
 * Created by jafarnaqvi on 07/06/16.
 */
public class FarziActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle saved){
        super.onCreate(saved);
        if (new UserManager(this).getUser() != null)
            startService(new Intent(this, ClipBoardService.class));
    }
}
