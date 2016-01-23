package me.cheesy.cheeselauncher.pm;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class SneakyPackageManager extends PackageManager {
    private final String mLibDir;
    private final PackageManager mPackageManager;

    public SneakyPackageManager(PackageManager packageManager, String customLib) {
        Log.i("Sneaky", "New PM created!");
        mPackageManager = packageManager;
        mLibDir = customLib;
    }

    @Override
    public void addPackageToPreferred(String packageName) {
        mPackageManager.addPackageToPreferred(packageName);
    }

    @Override
    public boolean addPermission(PermissionInfo info) {
        return mPackageManager.addPermission(info);
    }

    @Override
    public boolean addPermissionAsync(PermissionInfo info) {
        return mPackageManager.addPermissionAsync(info);
    }

    @Override
    public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
        mPackageManager.addPreferredActivity(filter, match, set, activity);
    }

    @Override
    public String[] canonicalToCurrentPackageNames(String[] names) {
        return mPackageManager.canonicalToCurrentPackageNames(names);
    }

    @Override
    public int checkPermission(String permName, String pkgName) {
        return mPackageManager.checkSignatures(permName, pkgName);
    }

    @Override
    public boolean isPermissionRevokedByPolicy(String permName, String pkgName) {
        return false;
    }

    @Override
    public int checkSignatures(String pkg1, String pkg2) {
        return mPackageManager.checkSignatures(pkg1, pkg2);
    }

    @Override
    public int checkSignatures(int uid1, int uid2) {
        return mPackageManager.checkSignatures(uid1, uid2);
    }

    @Override
    public void clearPackagePreferredActivities(String packageName) {
        mPackageManager.clearPackagePreferredActivities(packageName);
    }

    @Override
    public String[] currentToCanonicalPackageNames(String[] names) {
        return mPackageManager.currentToCanonicalPackageNames(names);
    }

    @Override
    public Drawable getActivityIcon(ComponentName activityName) throws NameNotFoundException {
        return mPackageManager.getActivityIcon(activityName);
    }

    @Override
    public Drawable getActivityIcon(Intent intent) throws NameNotFoundException {
        return mPackageManager.getActivityIcon(intent);
    }

    @Override
    public Drawable getActivityBanner(ComponentName activityName) throws NameNotFoundException {
        return null;
    }

    @Override
    public Drawable getActivityBanner(Intent intent) throws NameNotFoundException {
        return null;
    }

    @Override
    public ActivityInfo getActivityInfo(ComponentName component, int flags) throws NameNotFoundException {
        ActivityInfo info = mPackageManager.getActivityInfo(component, flags);
        info.applicationInfo.nativeLibraryDir = mLibDir;
        return info;
    }

    @Override
    public Drawable getActivityLogo(ComponentName activityName) throws NameNotFoundException {
        return mPackageManager.getActivityLogo(activityName);
    }

    @Override
    public Drawable getActivityLogo(Intent intent) throws NameNotFoundException {
        return mPackageManager.getActivityLogo(intent);
    }

    @Override
    public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
        return mPackageManager.getAllPermissionGroups(flags);
    }

    @Override
    public int getApplicationEnabledSetting(String packageName) {
        return mPackageManager.getApplicationEnabledSetting(packageName);
    }

    @Override
    public Drawable getApplicationIcon(ApplicationInfo info) {
        return mPackageManager.getApplicationIcon(info);
    }

    @Override
    public Drawable getApplicationIcon(String packageName) throws NameNotFoundException {
        return mPackageManager.getApplicationIcon(packageName);
    }

    @Override
    public Drawable getApplicationBanner(ApplicationInfo info) {
        return null;
    }

    @Override
    public Drawable getApplicationBanner(String packageName) throws NameNotFoundException {
        return null;
    }

    @Override
    public ApplicationInfo getApplicationInfo(String packageName, int flags) throws NameNotFoundException {
        return mPackageManager.getApplicationInfo(packageName, flags);
    }

    @Override
    public CharSequence getApplicationLabel(ApplicationInfo info) {
        return mPackageManager.getApplicationLabel(info);
    }

    @Override
    public Drawable getApplicationLogo(ApplicationInfo info) {
        return mPackageManager.getApplicationLogo(info);
    }

    @Override
    public Drawable getApplicationLogo(String packageName) throws NameNotFoundException {
        return mPackageManager.getApplicationLogo(packageName);
    }

    @Override
    public Drawable getUserBadgedIcon(Drawable icon, UserHandle user) {
        return null;
    }

    @Override
    public Drawable getUserBadgedDrawableForDensity(Drawable drawable, UserHandle user, Rect badgeLocation, int badgeDensity) {
        return null;
    }

    @Override
    public CharSequence getUserBadgedLabel(CharSequence label, UserHandle user) {
        return null;
    }

    @Override
    public int getComponentEnabledSetting(ComponentName componentName) {
        return mPackageManager.getComponentEnabledSetting(componentName);
    }

    @Override
    public Drawable getDefaultActivityIcon() {
        return mPackageManager.getDefaultActivityIcon();
    }

    @Override
    public Drawable getDrawable(String packageName, int resid, ApplicationInfo appInfo) {
        return mPackageManager.getDrawable(packageName, resid, appInfo);
    }

    @Override
    public List<ApplicationInfo> getInstalledApplications(int flags) {
        return mPackageManager.getInstalledApplications(flags);
    }

    @Override
    public List<PackageInfo> getInstalledPackages(int flags) {
        return mPackageManager.getInstalledPackages(flags);
    }

    @Override
    public List<PackageInfo> getPackagesHoldingPermissions(String[] permissions, int flags) {
        return null;
    }

    @Override
    public String getInstallerPackageName(String packageName) {
        return mPackageManager.getInstallerPackageName(packageName);
    }

    @Override
    public InstrumentationInfo getInstrumentationInfo(ComponentName className, int flags) throws NameNotFoundException {
        return mPackageManager.getInstrumentationInfo(className, flags);
    }

    @Override
    public Intent getLaunchIntentForPackage(String packageName) {
        return mPackageManager.getLaunchIntentForPackage(packageName);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Intent getLeanbackLaunchIntentForPackage(String packageName) {
        return mPackageManager.getLeanbackLaunchIntentForPackage(packageName);
    }

    @Override
    public String getNameForUid(int uid) {
        return mPackageManager.getNameForUid(uid);
    }

    @Override
    public int[] getPackageGids(String packageName) throws NameNotFoundException {
        return mPackageManager.getPackageGids(packageName);
    }

    @Override
    public PackageInfo getPackageInfo(String packageName, int flags) throws NameNotFoundException {
        return mPackageManager.getPackageInfo(packageName, flags);
    }

    @Override
    public String[] getPackagesForUid(int uid) {
        return mPackageManager.getPackagesForUid(uid);
    }

    @Override
    public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws NameNotFoundException {
        return mPackageManager.getPermissionGroupInfo(name, flags);
    }

    @Override
    public PermissionInfo getPermissionInfo(String name, int flags) throws NameNotFoundException {
        return mPackageManager.getPermissionInfo(name, flags);
    }

    @Override
    public int getPreferredActivities(@NonNull List<IntentFilter> arg0, @NonNull List<ComponentName> arg1, String arg2) {
        return mPackageManager.getPreferredActivities(arg0, arg1, arg2);
    }

    @Override
    public List<PackageInfo> getPreferredPackages(int flags) {
        return mPackageManager.getPreferredPackages(flags);
    }

    @Override
    public ProviderInfo getProviderInfo(ComponentName component, int flags) throws NameNotFoundException {
        return mPackageManager.getProviderInfo(component, flags);
    }

    @Override
    public ActivityInfo getReceiverInfo(ComponentName component, int flags) throws NameNotFoundException {
        return mPackageManager.getReceiverInfo(component, flags);
    }

    @Override
    public Resources getResourcesForActivity(ComponentName activityName) throws NameNotFoundException {
        return mPackageManager.getResourcesForActivity(activityName);
    }

    @Override
    public Resources getResourcesForApplication(ApplicationInfo app) throws NameNotFoundException {
        return mPackageManager.getResourcesForApplication(app);
    }

    @Override
    public Resources getResourcesForApplication(String appPackageName) throws NameNotFoundException {
        return mPackageManager.getResourcesForApplication(appPackageName);
    }

    @Override
    public ServiceInfo getServiceInfo(ComponentName component, int flags) throws NameNotFoundException {
        return mPackageManager.getServiceInfo(component, flags);
    }

    @Override
    public FeatureInfo[] getSystemAvailableFeatures() {
        return mPackageManager.getSystemAvailableFeatures();
    }

    @Override
    public String[] getSystemSharedLibraryNames() {
        return mPackageManager.getSystemSharedLibraryNames();
    }

    @Override
    public CharSequence getText(String packageName, int resid, ApplicationInfo appInfo) {
        return mPackageManager.getText(packageName, resid, appInfo);
    }

    @Override
    public XmlResourceParser getXml(String packageName, int resid, ApplicationInfo appInfo) {
        return mPackageManager.getXml(packageName, resid, appInfo);
    }

    @Override
    public boolean hasSystemFeature(String name) {
        return mPackageManager.hasSystemFeature(name);
    }

    @Override
    public boolean isSafeMode() {
        return mPackageManager.isSafeMode();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public PackageInstaller getPackageInstaller() {
        return mPackageManager.getPackageInstaller();
    }

    @Override
    public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int flags) {
        return mPackageManager.queryBroadcastReceivers(intent, flags);
    }

    @Override
    public List<ProviderInfo> queryContentProviders(String processName, int uid, int flags) {
        return mPackageManager.queryContentProviders(processName, uid, flags);
    }

    @Override
    public List<InstrumentationInfo> queryInstrumentation(String targetPackage, int flags) {
        return mPackageManager.queryInstrumentation(targetPackage, flags);
    }

    @Override
    public List<ResolveInfo> queryIntentActivities(Intent intent, int flags) {
        return mPackageManager.queryIntentActivities(intent, flags);
    }

    @Override
    public List<ResolveInfo> queryIntentActivityOptions(ComponentName caller, Intent[] specifics, Intent intent, int flags) {
        return mPackageManager.queryIntentActivityOptions(caller, specifics, intent, flags);
    }

    @Override
    public List<ResolveInfo> queryIntentServices(Intent intent, int flags) {
        return mPackageManager.queryIntentServices(intent, flags);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public List<ResolveInfo> queryIntentContentProviders(Intent intent, int flags) {
        return mPackageManager.queryIntentContentProviders(intent, flags);
    }

    @Override
    public List<PermissionInfo> queryPermissionsByGroup(String group, int flags) throws NameNotFoundException {
        return mPackageManager.queryPermissionsByGroup(group, flags);
    }

    @Override
    public void removePackageFromPreferred(String packageName) {
        mPackageManager.removePackageFromPreferred(packageName);
    }

    @Override
    public void removePermission(String name) {
        mPackageManager.removePermission(name);
    }

    @Override
    public ResolveInfo resolveActivity(Intent intent, int flags) {
        return mPackageManager.resolveActivity(intent, flags);
    }

    @Override
    public ProviderInfo resolveContentProvider(String name, int flags) {
        return mPackageManager.resolveContentProvider(name, flags);
    }

    @Override
    public ResolveInfo resolveService(Intent intent, int flags) {
        return mPackageManager.resolveService(intent, flags);
    }

    @Override
    public void setApplicationEnabledSetting(String packageName, int newState, int flags) {
        mPackageManager.setApplicationEnabledSetting(packageName, newState, flags);
    }

    @Override
    public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags) {
        mPackageManager.setComponentEnabledSetting(componentName, newState, flags);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void setInstallerPackageName(String targetPackage, String installerPackageName) {
        mPackageManager.setInstallerPackageName(targetPackage, installerPackageName);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void verifyPendingInstall(int id, int verificationCode) {
        mPackageManager.verifyPendingInstall(id, verificationCode);
    }

    @Override
    public void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) {
        mPackageManager.extendVerificationTimeout(id, verificationCodeAtTimeout, millisecondsToDelay);
    }
}
