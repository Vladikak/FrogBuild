//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.addon;

import com.example.addon.modules.ModuleExample;
import java.lang.invoke.MethodHandles;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddonTemplate extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger(AddonTemplate.class);

    public void onInitialize() {
        LOG.info("Initializing Meteor Addon Template");
        MeteorClient.EVENT_BUS.registerLambdaFactory("com.example.addon.modules.ModuleExample", (lookupInMethod, klass) -> (MethodHandles.Lookup)lookupInMethod.invoke((Object)null, klass, MethodHandles.lookup()));
        Modules.get().add(new ModuleExample("FrogBuild", "AutoBuild for logo of the coolest clan on the server karasique.com"));
    }

    public String getPackage() {
        return "com.example.addon";
    }
}
