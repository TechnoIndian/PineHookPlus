package com.pinehook.plus;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.Map;

public class Loader extends ContentProvider {
    private static final String TAG = "Loader";

    @Override
    public boolean onCreate() {
        try {
            Context context = getContext();
            if (context != null) {
                Log.d(TAG, "Loading native library");
                NativeLibLoader.loadNativeLib(context, "libpine.so");
                Log.d(TAG, "Loading modules");
                Hook.loadModules(context);
                Log.d(TAG, "Loading config");
                Map<String, Map<String, Object>> config = NativeLibLoader.loadConfig(context);
                Log.d(TAG, "Executing hook");
                Hook.doHook(config);
                Log.d(TAG, "Hook executed successfully");
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error during initialization", e);
            return false;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs,
                        String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return "";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        return 0;
    }
}
