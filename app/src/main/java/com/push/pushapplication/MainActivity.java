package com.push.pushapplication;

import android.util.Log;
import android.app.Application;
import androidx.appcompat.app.AppCompatActivity;
import org.jboss.aerogear.android.core.Callback;
import org.jboss.aerogear.android.unifiedpush.PushRegistrar;
import org.jboss.aerogear.android.unifiedpush.RegistrarManager;
import org.jboss.aerogear.android.unifiedpush.fcm.AeroGearFCMPushConfiguration;
import java.net.URI;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private final String VARIANT_ID       = "759a07b4-631a-4231-9a6d-fdcee0494b3b";
    private final String SECRET           = "c1e3f9fb-b663-4df2-9af8-44afae36ee2e";
    private final String FCM_SENDER_ID    = "700184178882";
    private final String UNIFIED_PUSH_URL = "http://10.0.2.2:9999/";
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

        RegistrarManager.registerMainThreadHandler((context, message)-> {
            Log.i(TAG, message.toString());
        });

    }
}

