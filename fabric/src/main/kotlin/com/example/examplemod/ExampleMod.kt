package com.example.examplemod

import com.example.examplemod.CommonClass.init
import net.fabricmc.api.ModInitializer

class ExampleMod : ModInitializer {
    override fun onInitialize() {
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.

        Constants.LOG!!.info("Hello Fabric world!")
        init()
    }
}
