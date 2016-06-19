package io.zillion.universalcopypaste;

import android.content.Intent;

import com.anupcowkur.reservoir.Reservoir;

import io.zillion.universalcopypaste.utils.UserManager;

/**
 * Created by jafarnaqvi on 07/06/16.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Reservoir.init(this, 2048); //in bytes
        } catch (Exception e) {
            //failure
        }
        if (new UserManager(this).getUser() != null)
            startService(new Intent(this, ClipBoardService.class));

    }
}
