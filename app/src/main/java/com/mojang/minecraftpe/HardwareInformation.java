package com.mojang.minecraftpe;

import android.os.Build;

import java.io.File;
import java.io.FileFilter;

public class HardwareInformation {
    public static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getCPU() {
        return Build.CPU_ABI;
    }

    public static String getCPUName() {
        // TODO: Implement.
        return "FakeCPU";
    }

    public static String getDeviceModelName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model.toUpperCase();
        }
        return manufacturer.toUpperCase() + " " + model;
    }

    public static String getCPUType() {
        // TODO: Implement correctly.
        return "Fake";
    }

    public static String getCPUFeatures() {
        // TODO: Implement correctly.
        return "Fake!";
    }

    public static int getNumCores() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().matches("cpu[0-9]+");
            }
        }

        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch(Exception e) {
            return 1;
        }
    }
}