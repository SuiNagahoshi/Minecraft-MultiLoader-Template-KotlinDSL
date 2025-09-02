package com.example.examplemod.mixin

import com.example.examplemod.Constants
import net.minecraft.client.Minecraft
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(Minecraft::class)
class MixinMinecraft {
    @Inject(at = [At("TAIL")], method = ["<init>"])
    private fun init(info: CallbackInfo?) {
        Constants.LOG?.info("This line is printed by an example mod common mixin!")
        Constants.LOG?.info("MC Version: {}", Minecraft.getInstance().getVersionType())
    }
}