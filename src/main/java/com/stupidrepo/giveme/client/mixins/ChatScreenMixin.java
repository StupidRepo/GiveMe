package com.stupidrepo.giveme.client.mixins;

import com.stupidrepo.giveme.client.GiveMeClient;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

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
        var m = Objects.equals(string.split(" ")[0].trim(), "/giveme") ? Integer.MAX_VALUE : GiveMeClient.MAX_MESSAGE_LENGTH;
        this.input.setMaxLength(m);
    }

    @Inject(method = "normalizeChatMessage", at = @At("HEAD"), cancellable = true)
    protected void normalizeChatMessage(String string, CallbackInfoReturnable<String> cir) {
        if(Objects.equals(string.split(" ")[0].trim(), "/giveme")) {
            cir.cancel();
            cir.setReturnValue(string);
        }
    }
}
