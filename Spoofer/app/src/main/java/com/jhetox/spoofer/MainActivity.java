package com.jhetox.spoofer;

import android.app.Activity;
import android.os.Bundle;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String realSignature = SpoofContext.getSignature(this, true);
        String realPackageName = SpoofContext.getPackageName(this);
        List<String> realPermissions = SpoofContext.getPermissions(this);

        SpoofContext spoof = new SpoofContext(this, "com.whatsapp");
        String spoofSignature = SpoofContext.getSignature(spoof, true);
        String spoofPackageName = SpoofContext.getPackageName(spoof);
        List<String> spoofPermissions = SpoofContext.getPermissions(spoof);

        boolean isFakeRealApp = FakeAppDetector.isFakeApp(this, "com.whatsapp", "38A0F7D505FE18FEC64FBF343ECAAAF310DBD799");
        boolean isFakeSpoofApp = FakeAppDetector.isFakeApp(spoof, "com.whatsapp", "38A0F7D505FE18FEC64FBF343ECAAAF310DBD799");

       setContentView(R.layout.activity_main);
    }
}
