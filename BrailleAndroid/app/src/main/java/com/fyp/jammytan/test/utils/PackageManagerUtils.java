/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fyp.jammytan.test.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.Log;

/**
 * Utilities for interacting with the {@link PackageManager}.
 *
 * @author alanv@google.com (Alan Viverette)
 */
public class PackageManagerUtils {
    /** Invalid version code for a package. */
    public static final int INVALID_VERSION_CODE = -1;

    /**
     * @return The package version code or {@link #INVALID_VERSION_CODE} if the
     *         package does not exist.
     */
    public static int getVersionCode(Context context, CharSequence packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return INVALID_VERSION_CODE;
        }

        final PackageInfo packageInfo = getPackageInfo(context, packageName);

        if (packageInfo == null) {
            LogUtils.log(PackageManagerUtils.class, Log.ERROR, "Could not find package: %s",
                    packageName);
            return INVALID_VERSION_CODE;
        }

        return packageInfo.versionCode;
    }

    /**
     * @return The package version name or <code>null</code> if the package does
     *         not exist.
     */
    public static String getVersionName(Context context, CharSequence packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }

        final PackageInfo packageInfo = getPackageInfo(context, packageName);

        if (packageInfo == null) {
            LogUtils.log(PackageManagerUtils.class, Log.ERROR, "Could not find package: %s",
                    packageName);
            return null;
        }

        return packageInfo.versionName;
    }

    /**
     * @return Whether the package is installed on the device.
     */
    public static boolean hasPackage(Context context, String packageName) {
        return (getPackageInfo(context, packageName) != null);
    }

    private static PackageInfo getPackageInfo(Context context, CharSequence packageName) {
        if (packageName == null) {
            return null;
        }

        final PackageManager packageManager = context.getPackageManager();

        try {
            return packageManager.getPackageInfo(packageName.toString(), 0);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

      /**
       * Returns a developer-provided description for the primary application of the provided
       * package name. If the developer provided no name, the package name is used. If the package
       * is not installed on the system, {@code null} is returned.
       *
       * @param context {@link Context} used to resolve package information
       * @param packageName The package name of the application
       * @return A developer-provided description of the application, or {@code null} if the package
       *         is not installed on the system.
       */
      public static CharSequence getPackageDescription(Context context, CharSequence packageName) {
        if ((context == null) || TextUtils.isEmpty(packageName)) {
          return null;
        }

        try {
          PackageManager pm = context.getPackageManager();
          ApplicationInfo info = pm.getApplicationInfo(packageName.toString(), 0);
          return info.loadLabel(pm);
        } catch (NameNotFoundException nnfe) {
          return null;
        }
      }
}