package com.mojang.minecraftpe;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NativeActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;

import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.fmod.FMOD;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

import me.cheesy.cheeselauncher.HoverButton;
import me.cheesy.cheeselauncher.pm.SneakyPackageManager;

public class MainActivity extends NativeActivity {
    private ArrayList<ActivityListener> mActivityListeners;
    private boolean mMasq;
    private String mMCPENativeLibraryDirectory;
    private Context mMinecraftAPKContext;

    public MainActivity() {
        mActivityListeners = new ArrayList<ActivityListener>();
        mMasq = false;
    }

    /**
     * Shows a general error message.
     * @param message
     */
    private void errorOccurred(String message) {
        new AlertDialog.Builder(this).setTitle("An error occurred.")
                .setMessage(message).setPositiveButton("OK", null).show();
    }

    @Override
    public void onCreate(Bundle icicle) {

        // Create package context.
        if (this.getPackageName().equals("com.mojang.minecraftpe")) {
            mMinecraftAPKContext = this;
        } else {
            try {
                mMinecraftAPKContext = createPackageContext("com.mojang.minecraftpe",
                        Context.CONTEXT_IGNORE_SECURITY);
            } catch (PackageManager.NameNotFoundException e) {
                // Should NEVER happen.
            }
        }

        // Hide status bar.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Attempt to load.
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo pi = packageManager.getPackageInfo("com.mojang.minecraftpe", 0);
            ApplicationInfo ai = pi.applicationInfo;
            String nativeLibraryDir = ai.nativeLibraryDir;
            mMCPENativeLibraryDirectory = nativeLibraryDir;

            // Load all libraries.
            Log.i("CheeseLoader", "Loading Minecraft: Pocket Edition libraries...");
            System.load(nativeLibraryDir + "/libgnustl_shared.so");
            System.load(nativeLibraryDir + "/libfmod.so");
            System.load(nativeLibraryDir + "/libminecraftpe.so");
            Log.i("CheeseLoader", "Loading CheeseLauncher native library...");
            System.loadLibrary("cheeselauncher");
            Log.i("CheeseLoader", "Initializing Java-side of FMOD.");
            FMOD.init(this);
        } catch (PackageManager.NameNotFoundException e) {
            errorOccurred("Looks like you don't have MCPE installed.");
        } catch (UnsatisfiedLinkError ule) {
            errorOccurred("An unsatisfied link error has occurred: " + ule.getMessage());
        }

        setVolumeControlStream(3);

        // Masquerade to load some things properly.
        mMasq = true;
        super.onCreate(icicle);
        nativeRegisterThis();
        mMasq = false;

        // Activate garbage collector to clean up.
        System.gc();
    }

    @Override
    public PackageManager getPackageManager() {
        // Fetch the real package manager from our
        // super class.
        PackageManager realPackageManager = super.getPackageManager();

        if (mMasq) {
            // Masquerading, return the sneaky one.
            return new SneakyPackageManager(realPackageManager, mMCPENativeLibraryDirectory);
        }

        // Not masquerading, return the real
        // package manager.
        return realPackageManager;
    }

    @Override
    public void onStop() {
        nativeStopThis();
        super.onStop();
        // TODO: deviceManager#unregister
    }

    @Override
    public void onPause() {
        nativeSuspend();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Show menu button.
        final HoverButton hb = new HoverButton(MainActivity.this);
        final MainActivity thiz = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e("CheeseLauncher", "Interrupted when sleeping");
                }
                thiz.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hb.showAtLocation(thiz.getWindow().getDecorView(),
                                Gravity.TOP | Gravity.CENTER_VERTICAL, 0, 0);
                    }
                });
            }
        }).start();
        // TODO: Show keyboard again.
        /*
            if(mHiddenTextInputDialog != null)
            {
                String s = textInputWidget.getText().toString();
                int i = textInputWidget.allowedLength;
                boolean flag = textInputWidget.limitInput;
                textInputWidget.getInputType();
                mHiddenTextInputDialog.dismiss();
                mHiddenTextInputDialog = null;
                showKeyboard(s, i, flag, flag);
            }
         */
    }

    @Override
    public void onStart() {
        super.onStart();
        // TODO: deviceManager#register
    }

    // Put native methods here.
    native void nativeBackPressed();
    native void nativeBackSpacePressed();
    native void nativeOnPickImageCanceled(long l);
    native void nativeOnPickImageSuccess(long l, String s);
    native void nativeRegisterThis();
    native void nativeReturnKeyPressed();
    native void nativeSetTextboxText(String s);
    native void nativeStopThis();
    native void nativeSuspend();
    native void nativeTypeCharacter(String s);
    native void nativeUnregisterThis();

    private void createAlertDialog(boolean flag, boolean flag1, boolean flag2) {
        // Stub.
    }

    private void onDialogCanceled() {
        // Stub.
    }

    private void onDialogCompleted() {
        // Stub.
    }

    public static void saveScreenshot(String string,
                                      int something, int somethingElse, int somethingElseToo[]) {
        // Stub.
    }

    public void addListener(ActivityListener activityListener) {
        mActivityListeners.add(activityListener);
    }

    public void buyGame() {
        // Stub.
    }

    public int checkLicense() {
        // Stub.
        return 0;
    }

    public void clearLoginInformation() {
        // Stub.
    }

    public String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public boolean dispatchKeyEvent(KeyEvent ke) {
        if (ke.getKeyCode() == 4 && ke.getAction() == KeyEvent.KEYCODE_SOFT_LEFT) {
            return false;
        }

        if (ke.getCharacters() != null)
            nativeTypeCharacter(ke.getCharacters());

        return super.dispatchKeyEvent(ke);
    }

    public void displayDialog(int i) {
        // Stub.
    }

    public String getAccessToken() {
        // Stub.
        return "";
    }

    public int getAndroidVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Returns broadcast addresses.
     * @return
     */
    public String[] getBroadcastAddresses() {
        ArrayList<String> arraylist = new ArrayList<>();
        try
        {
            System.setProperty("java.net.preferIPv4Stack", "true");
            for(Enumeration enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements();)
            {
                NetworkInterface obj = (NetworkInterface) enumeration.nextElement();
                if(!obj.isLoopback())
                {
                    Iterator someIterator;
                    someIterator = obj.getInterfaceAddresses().iterator();
                    while(someIterator.hasNext())
                    {
                        InterfaceAddress interfaceaddress = (InterfaceAddress)someIterator.next();
                        if(interfaceaddress.getBroadcast() != null)
                            arraylist.add(interfaceaddress.getBroadcast().toString().substring(1));
                    }
                }
            }

        }
        catch(Exception exception) {

        }
        return (String[])arraylist.toArray(new String[arraylist.size()]);
    }

    public String getClientId() {
        // Stub.
        return "";
    }

    /**
     * Returns a formatted date string.
     * @param i
     * @return
     */
    public String getDateString(int i)
    {
        return DateFormat.getDateInstance(3, Locale.US).format(new Date((long)i * 1000L));
    }

    /**
     * Returns the device ID used by Mojang's snooper.
     * @return
     */
    public String getDeviceId() {
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String s1 = sharedpreferences.getString("snooperId", "");
        String s = s1;
        if(s1.isEmpty())
        {
            s = UUID.randomUUID().toString().replaceAll("-", "");
            android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("snooperId", s);
            editor.commit();
        }
        return s;
    }

    public String getDeviceModel() {
        // TODO: Implement.
        // return HardwareInformation.getDeviceModelName();
        return "FakeDevice";
    }

    public String getExternalStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public byte[] getFileDataBytes(String path) {
        byte[] returning;
        try {
            InputStream inputStream = getInputStreamForAsset(path);
            if (inputStream == null) return null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int length = 0;
                length = inputStream.read(buffer);
                if (length < 0) break;
                byteArrayOutputStream.write(buffer, 0, length);
            }
            returning = byteArrayOutputStream.toByteArray();
            return returning;
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }

    protected InputStream getInputStreamForAsset(String name) {
        InputStream is = null;
        try {
            is = mMinecraftAPKContext.getAssets().open(name);
            return is;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public int[] getImageData(String s, boolean flag)
    {
        AssetManager assetManager;
        InputStream inputStream;
        Bitmap bitmap;
        int i;
        int j;
        if(flag) {
            assetManager = mMinecraftAPKContext.getAssets();
            try {
                inputStream = assetManager.open(s);
            } catch(Exception e) {
                System.err.println((new StringBuilder()).append("getImageData: Could not open image ").append(s).toString());
                return null;
            }
            bitmap = BitmapFactory.decodeStream(inputStream);
        } else {
            bitmap = BitmapFactory.decodeFile(s);
            if(bitmap == null)
            {
                System.err.println((new StringBuilder()).append("getImageData: Could not open image ").append(s).toString());
                return null;
            }
        }
        i = bitmap.getWidth();
        j = bitmap.getHeight();
        int[] map = new int[i * j + 2];
        map[0] = i;
        map[1] = j;
        bitmap.getPixels(map, 2, i, 0, 0, i, j);
        return map;
    }

    // Borrowed from BlockLauncher.
    public int getKeyFromKeyCode(int i, int j, int k) {
        return KeyCharacterMap.load(k).get(i, j);
    }

    public float getKeyboardHeight() {
        // TODO: Implement.
//        return (float)virtualKeyboardHeight;
        return 40.0f; // Random.
    }

    public String getLocale() {
        // TODO: Implement.
//        return HardwareInformation.getLocale();
        return "United States";
    }

    public float getPixelsPerMillimeter() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return ((displayMetrics.xdpi + displayMetrics.ydpi) * 0.5F) / 25.4F;
    }

    public String getPlatformStringVar(int i) {
        if (i == 0)
            return Build.MODEL;
        else
            return null;
    }

    public String getProfileId() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("profileId", "");
    }

    public String getProfileName() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("profileName", "");
    }

    public int getScreenHeight()
    {
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return Math.min(display.getWidth(), display.getHeight());
    }

    public int getScreenWidth()
    {
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return Math.max(display.getWidth(), display.getHeight());
    }

    public long getTotalMemory()
    {
        // TODO: Implement spoofing feature.
        ActivityManager activitymanager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        android.app.ActivityManager.MemoryInfo memoryinfo = new android.app.ActivityManager.MemoryInfo();
        activitymanager.getMemoryInfo(memoryinfo);
        return memoryinfo.availMem;
    }

    public int getUserInputStatus() {
        // TODO: Implement.
//        return _userInputStatus;
        return 0;
    }

    public String[] getUserInputString() {
        // TODO: Implement.
        return new String[] { "TEST." };
//        return _userInputText;
    }

    public boolean hasBuyButtonWhenInvalidLicense() {
        return true;
    }

    public boolean hasHardwareChanged()
    {
        return false;
    }

    public void hideKeyboard() {
        // TODO: Implement.
    }

    public void initiateUserInput(int i) {
        // TODO: Implement.
//        _userInputText = null;
//        _userInputStatus = -1;
    }

    protected boolean isDemo() {
        return false;
    }

    public boolean isFirstSnooperStart() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("snooperId", "").isEmpty();
    }

    public boolean isNetworkEnabled(boolean flag)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        if (flag)
            networkInfo = connectivityManager.getNetworkInfo(1);
        else
            networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    boolean isTablet() {
        // TODO: Implement spoofing feature.
        return (getResources().getConfiguration().screenLayout & 0xf) == 4;
    }

    @Override
    public void onActivityResult(int i, int j, Intent intent) {
        // TODO: Implement.
    }

    public void onBackPressed() {
        // Stub.
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        // Stub.
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        return super.onKeyDown(keyCode, keyEvent);
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent keyEvent) {
        return super.onKeyMultiple(keyCode, repeatCount, keyEvent);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // TODO: platform#onViewFocusChanged(hasFocus)
    }

    public void pickImage(long l) {
        // TODO: Implement.
    }

    public void postScreenshotToFacebook(String s, int i, int j, int ai[]) {
        // Stub.
    }

    public void quit() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    public void removeListener(ActivityListener activitylistener) {
        // TODO: Implement.
//        mActivityListeners.remove(activitylistener);
    }

    public void setLoginInformation(String s, String s1, String s2, String s3) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("accessToken", s);
        editor.putString("clientId", s1);
        editor.putString("profileId", s2);
        editor.putString("profileName", s3);
        editor.commit();
    }

    public void setRefreshToken(String s) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("refreshToken", s);
        editor.commit();
    }

    public void setSession(String s) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("sessionID", s);
        editor.commit();
    }

    public void setupKeyboardViews(String activityRootView, int i, boolean flag, boolean flag1) {
        // Sets up the hidden keyboard views, the input proxy, etc.
        // IMPORTANT!
        // TODO: Implement.
        Log.i("MojangMainActivity", "setupKeyboardViews");
    }

    public void showKeyboard(String startText, int fMaxLength, boolean fLimitInput, boolean fNumbersOnly) {
    }

    public void statsTrackEvent(String s, String s1) {
        // Stub.
    }

    public void statsUpdateUserData(String s, String s1) {
        // Stub.
    }

    public void tick() {
        // Stub.
    }

    public void updateTextboxText(String setText)
    {
        // Updates the textbox text.
        // TODO: Implement.
    }

    public void vibrate(int i) {
        // TODO: Implement spoofing feature.
        ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(i);
    }


    public static boolean isPowerVR() {
        return false;
    }

    public void setIsPowerVR(boolean flag) {
        // Stub.
        // TODO: Implement.
    }

    public interface ActivityListener
    {
        void onActivityResult(int i, int j, Intent intent);
        void onDestroy();
    }

    protected void onDestroy() {
        nativeUnregisterThis();
        super.onDestroy();
    }
}
