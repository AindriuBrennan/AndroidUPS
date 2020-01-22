package com.push.pushapplication;

import android.content.Context;
import android.util.Log;
import android.app.Application;
import androidx.appcompat.app.AppCompatActivity;
import org.jboss.aerogear.android.core.Callback;
import org.jboss.aerogear.android.unifiedpush.MessageHandler;
import org.jboss.aerogear.android.unifiedpush.PushRegistrar;
import org.jboss.aerogear.android.unifiedpush.RegistrarManager;
import org.jboss.aerogear.android.unifiedpush.fcm.AeroGearFCMPushConfiguration;
import org.jboss.aerogear.android.unifiedpush.fcm.UnifiedPushMessage;

import java.net.URI;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MessageHandler {


    private final String VARIANT_ID       = "172bf953-f266-4e32-866b-662ff32d653c";
    private final String SECRET           = "7680585e-c22e-4105-b0fc-fbcb150036d4";
    private final String FCM_SENDER_ID    = "881192658444";
    private final String UNIFIED_PUSH_URL = "http://192.168.1.187:9999/";
    private final String TAG = "ups";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RegistrarManager.config("register", AeroGearFCMPushConfiguration.class)
                .setPushServerURI(URI.create(UNIFIED_PUSH_URL))
                .setSenderId(FCM_SENDER_ID)
                .setVariantID(VARIANT_ID)
                .setSecret(SECRET)
                .asRegistrar();

        PushRegistrar registrar = RegistrarManager.getRegistrar("register");
        registrar.register(getApplicationContext(), new Callback<Void>() {
            @Override
            public void onSuccess(Void data) {
                Log.i(TAG, "Registration Succeeded!");
            }
            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RegistrarManager.registerMainThreadHandler(this); // 1
    }

    @Override
    public void onMessage(Context context, Bundle bundle) {
        // display the message contained in the payload
        final String msg = bundle.getString(UnifiedPushMessage.ALERT_KEY); // 3
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }

}

