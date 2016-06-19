package io.zillion.universalcopypaste;


import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import android.os.Process;

import com.android.volley.Request;

import java.util.HashMap;

import io.zillion.universalcopypaste.utils.UserManager;

/**
 * Created by jafarnaqvi on 07/06/16.
 */


public class ClipBoardService extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                Thread.sleep(1000);
                final ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                clipBoard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
                                                            @Override
                                                            public void onPrimaryClipChanged() {

                                                                if ((clipBoard.hasPrimaryClip()) && (clipBoard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))) {

                                                                    ClipData.Item item = clipBoard.getPrimaryClip().getItemAt(0);

// Gets the clipboard as text.
                                                                    String pasteData = item.getText().toString();

// If the string contains data, then the paste operation is done
                                                                    if (pasteData != null) {

                                                                        Toast.makeText(getApplicationContext(),pasteData,Toast.LENGTH_SHORT).show();

// The clipboard does not contain text. If it contains a URI, attempts to get data from it

                                                                        HashMap<String,String> params=new HashMap<String, String>();
                                                                        params.put("user",new UserManager(getApplicationContext()).getUser().getId()+"");
                                                                        params.put("text",pasteData);
                                                                        NetworkRequest networkRequest=new NetworkRequest(getApplicationContext(), "data", params, new SuccessCallback() {
                                                                            @Override
                                                                            public void onResponse(String response) {

                                                                            }
                                                                        }, new FailureCallback() {
                                                                            @Override
                                                                            public void onResponse() {

                                                                            }
                                                                        });

                                                                        networkRequest.setMethod(Request.Method.POST);
                                                                        networkRequest.setCache(false);
                                                                        networkRequest.execute();
                                                                    }


                                                                }
                                                            }
                                                        }
                );

            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}