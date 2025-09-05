package org.fnf.fnfutil.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.fnf.fnfutil.fnfmain;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, fnfmain.MOD_ID);

    public static RegistryObject<SoundEvent> ENDO_ATTACH = registerSoundEvent("endo_attach");
    public static RegistryObject<SoundEvent> ENDO_DETACH = registerSoundEvent("endo_detach");
    public static RegistryObject<SoundEvent> PUPPETS_MUSIC = registerSoundEvent("puppets_music");
    public static RegistryObject<SoundEvent> TOY_GUN_FIRE = registerSoundEvent("toy_gun_fire");
    public static RegistryObject<SoundEvent> PLUSH_HONK = registerSoundEvent("plush_honk");
    public static RegistryObject<SoundEvent> GUN_FIRE = registerSoundEvent("gun_fire");



    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(fnfmain.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
