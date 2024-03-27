package com.stupidrepo.giveme.client.mixins;

import com.stupidrepo.giveme.client.GiveMeClient;
import net.minecraft.client.gui.components.CommandSuggestions;
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

//    @Shadow private CommandSuggestions commandSuggestions;

//    @Inject(method = "init", at = @At("TAIL"))
//    protected void init(CallbackInfo ci) {
//        this.commandSuggestions = new CommandSuggestions(this.minecraft, this, this.input, this.font, false, false, 1, 10, true, -805306368);
//        this.commandSuggestions.setAllowHiding(false);
//        this.commandSuggestions.updateCommandInfo();
//    }

    @Inject(method = "onEdited", at = @At("TAIL"))
    protected void onEdited(String string, CallbackInfo ci) {
        var split = string.split(" ");
        if(split.length < 1) return;

        var m = Objects.equals(split[0].trim(), "/giveme") ? Integer.MAX_VALUE : GiveMeClient.MAX_MESSAGE_LENGTH;
        this.input.setMaxLength(m);
    }

    @Inject(method = "normalizeChatMessage", at = @At("HEAD"), cancellable = true)
    protected void normalizeChatMessage(String string, CallbackInfoReturnable<String> cir) {
        var split = string.split(" ");
        if(split.length < 1) return;

        if(Objects.equals(split[0].trim(), "/giveme")) {
            cir.cancel();
            cir.setReturnValue(string);
        }
    }
}
