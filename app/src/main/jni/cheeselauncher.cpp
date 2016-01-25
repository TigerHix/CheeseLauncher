//
// Created by cheesy on 1/8/16.
// Borked by joe on 1/24/16.
//

#include <string.h>
#include <jni.h>
#include <android/log.h>
#include <stdbool.h>
#include <dlfcn.h>
#include <minecraftpe/entity/player/LocalPlayer.h>
#include <minecraftpe/client/Minecraft.h>
#include <minecraftpe/client/MinecraftClient.h>
#include "substrate.h"
#include "cheeselauncher_structs.h"

#define LOG_TAG "CheeseLauncherNative"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

static JavaVM* java_vm;
static MinecraftClient* minecraftClient;

//bool hasLocalFlyHackEnabled = false;
//bool hasSniperFovViewEnabled = false;
//
//static float (*LocalPlayer$getFOVModifier_real)(void*);
//static float LocalPlayer$getFOVModifier_hook(void* localplayer) {
//	if (hasSniperFovViewEnabled) {
//		return 10.0F;
//	}
//	else {
//		LOGI("Default Field of View Modifier: %d", LocalPlayer$getFOVModifier_real(localplayer));
//		return LocalPlayer$getFOVModifier_real(localplayer);
//	}
//}

bool leet_flyhack = true;

static void (*LocalPlayer$normalTick_real)(LocalPlayer*);
static void LocalPlayer$normalTick_hook(LocalPlayer* localplayer) {
//    LOGI("IS CLIENT SIDE: %d", minecraftClient->getServer()->getLevel()->isClientSide());
//	if (leet_flyhack && minecraftClient->getServer()->getLevel()->isClientSide() == 0) {
//		// TODO: Enable flyhack and do
//		// Level::isClientSide to
//		// verify the player's playing
//		// in a local world
//        LOGI("Giving the player leet powers.");
//        localplayer->abilities.mayfly = true;
//	}
	LocalPlayer$normalTick_real(localplayer);
}

static void (*MinecraftClient$init_real)(MinecraftClient*);
static void MinecraftClient$init_hook(MinecraftClient* client) {
    LOGI("MinecraftClient$init called.");
    minecraftClient = client;
    MinecraftClient$init_real(client);
}

jint JNI_OnLoad(JavaVM* vm, void* what) {
    LOGI("Ello! We're riding on the chocolate bar!");
    LOGI(" *** CheeseLauncherNative has been started. ***");
	
    // Get the global java VM
    java_vm = vm;

//	void* LocalPlayer$getFOVModifier = dlsym(RTLD_DEFAULT, "_ZN11LocalPlayer22getFieldOfViewModifierEv");
//	MSHookFunction(LocalPlayer$getFOVModifier, (void*) &LocalPlayer$getFOVModifier_hook, (void**) &LocalPlayer$getFOVModifier_real);
////
    MSHookFunction((void*) &MinecraftClient::init, (void*) &MinecraftClient$init_hook, (void**) &MinecraftClient$init_real);
    // HOOKING THIS FUNCTION (LOCALPLAYER::NORMALTICK) CAUSES A CRASH (SIGILL)...
//	MSHookFunction((void*) &LocalPlayer::normalTick, (void*) &LocalPlayer$normalTick_hook, (void**) &LocalPlayer$normalTick_real);

//	void* Level$getAdventureSettings = (AdventureSettings(*)(void*)) dlsym(RTLD_DEFAULT, "_ZN5Level20getAdventureSettingsEv");
    return JNI_VERSION_1_6;
}
