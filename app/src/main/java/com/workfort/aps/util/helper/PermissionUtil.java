package com.workfort.aps.util.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {
    public static final int REQUEST_CODE_PERMISSION_DEFAULT = 1;
    public static final int REQUEST_CODE_STORAGE = 2;
    private static PermissionUtil invokePermission;

    private PermissionUtil() {

    }

    public static PermissionUtil on() {
        if (invokePermission == null) {
            invokePermission = new PermissionUtil();
        }

        return invokePermission;
    }

    public boolean request(Activity activity, String... permissions) {
        return request(activity, REQUEST_CODE_PERMISSION_DEFAULT, permissions);
    }

    public boolean request(Activity activity, int requestCode, String... permissions) {
        if (activity == null) return false;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        List<String> finalArgs = new ArrayList<>();
        for (String permission : permissions) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                finalArgs.add(permission);
            }
        }

        if (finalArgs.isEmpty()) {
            return true;
        }

        activity.requestPermissions(finalArgs.toArray(new String[finalArgs.size()]), requestCode);

        return false;
    }

    public boolean request(Fragment fragment, String... permissions) {
        return request(fragment, REQUEST_CODE_PERMISSION_DEFAULT, permissions);
    }

    public boolean request(Fragment fragment, int requestCode, String... permissions) {
        if (fragment == null || fragment.getContext() == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        List<String> finalArgs = new ArrayList<>();
        for (String aStr : permissions) {
            if (fragment.getContext().checkSelfPermission(aStr) != PackageManager.PERMISSION_GRANTED) {
                finalArgs.add(aStr);
            }
        }

        if (finalArgs.isEmpty()) {
            return true;
        }

        fragment.requestPermissions(finalArgs.toArray(new String[finalArgs.size()]), requestCode);

        return false;
    }

    public boolean isAllowed(Context context, String str) {
        if (context == null) return false;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        return context.checkSelfPermission(str) == PackageManager.PERMISSION_GRANTED;
    }
}
