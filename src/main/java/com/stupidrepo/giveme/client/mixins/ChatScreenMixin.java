package com.stupidrepo.giveme.client.mixins;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen {
    protected ChatScreenMixin(Component component) {
        super(component);
    }

    @Shadow
    protected EditBox input;

//    @Inject(method = "init", at = @At("TAIL"))
//    protected void init(CallbackInfo ci) {
//        this.input.setMaxLength(Integer.MAX_VALUE);
//    }

    @Inject(method = "onEdited", at = @At("TAIL"))
    protected void onEdited(String string, CallbackInfo ci) {
        if(string.startsWith("/giveme")) {
            this.input.setMaxLength(Integer.MAX_VALUE);
        } else {
            this.input.setMaxLength(256);
        }
    }

//    @Inject(method = "normalizeChatMessage", at = @At("HEAD"), cancellable = true)
//    protected void normalizeChatMessage(String string, CallbackInfoReturnable<String> cir) {
//        if(string.length() > 256) {
//            if(string.startsWith("/giveme")) {
//                cir.setReturnValue(string);
//            } else {
//                cir.setReturnValue(StringUtil.trimChatMessage(StringUtils.normalizeSpace(string.trim())));
//            }
//        }
//    }
}
