//
// Created by cheesy on 1/8/16.
// Borked by joe on 1/24/16.
//

#include <string.h>
#include <jni.h>
#include <android/log.h>

#include "Substrate.h"
#include "cheeselauncher_structs.h"

#define LOG_TAG "CheeseLauncherNative"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

static JavaVM* java_vm;

bool hasLocalFlyHackEnabled = false;
bool hasSniperFovViewEnabled = false;

static float (*LocalPlayer$getFOVModifier_real)(Player*);
static float LocalPlayer$getFOVModifier_hook(Player* localplayer) {
	if (hasSniperFovViewEnabled) {
		return 10.0F;
	}
	else {
		LOGI("Default Field of View Modifier: %d", LocalPlayer$getFOVModifier_real(localplayer));
		return LocalPlayer$getFOVModifier_real(localplayer)
	}
}

static void (*LocalPlayer$normalTick_real)(Player*);
static void LocalPlayer$normalTick_hook(Player* localplayer) {
	if (hasLocalFlyHackEnabled) {
		// TODO: Enable flyhack and do
		// Level::isClientSide to
		// verify the player's playing
		// in a local world
	}
	LocalPlayer$normalTick_real(localplayer);
}

jint JNI_OnLoad(JavaVM* vm, void* what) {
    LOGI("Ello! We're riding on the chocolate bar!");
    LOGI(" *** CheeseLauncherNative has been started. ***");
	
    // Get the global java VM
    java_vm = vm;
	
	/* <===== Hooks =====> */
	void* LocalPlayer$getFOVModifier = dlsym(RTLD_DEFAULT, "_ZN11LocalPlayer22getFieldOfViewModifierEv");
	MSHookFunction(LocalPlayer$getFOVModifier, (void*) &LocalPlayer$getFOVModifier_hook, (void**) &LocalPlayer$getFOVModifier_real);
	
	void* LocalPlayer$normalTick = dlsym(RTLD_DEFAULT, "_ZN11LocalPlayer10normalTickEv");
	MSHookFunction(LocalPlayer$normalTick, (void*) &LocalPlayer$normalTick_hook, (void**) &LocalPlayer$normalTick_real);
	
	/* <===== Functions =====> */
	Level$getAdventureSettings = (AdventureSettings(*)(void*)) dlsym(RTLD_DEFAULT, "_ZN5Level20getAdventureSettingsEv");
    return JNI_VERSION_1_6;
}
