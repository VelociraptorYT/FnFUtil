package org.fnf.fnfutil.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.fnf.fnfutil.item.custom.*;
import org.fnf.fnfutil.block.ModBlocks;
import org.fnf.fnfutil.sound.ModSounds;

import static org.fnf.fnfutil.fnfmain.MOD_ID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> BONNIE_EARS = ITEMS.register("bonnie_ears",
            () -> new BonnieEarsItem(ModArmorMaterials.ANIMATRONIC, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> PUPPETS_DISC = ITEMS.register("puppets_disc",
            () -> new RecordItem(4, ModSounds.PUPPETS_MUSIC,
                    new Item.Properties().tab(ModCreativeModeTab.FNFUTIL).stacksTo(1), 1200));
//144000
    // Coffee machine BlockItem
    public static final RegistryObject<Item> COFFEE_MACHINE = ITEMS.register("coffee_machine",
            () -> new CoffeeMachineItem(ModBlocks.COFFEE_MACHINE.get(),
                    new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> FIONA = ITEMS.register("fiona",
            () -> new FionaItem(ModBlocks.FIONA.get(),
                    new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> CASSETTE_PLAYER = ITEMS.register("cassette_player",
            () -> new CassettePlayerItem(ModBlocks.CASSETTE_PLAYER.get(),
                    new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> PHONE = ITEMS.register("phone",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> MONEY = ITEMS.register("money",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> BURGER = ITEMS.register("burger",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> CLEAVER = ITEMS.register("cleaver",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> BLOODY_CLEAVER = ITEMS.register("bloody_cleaver",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee",
            () -> new CoffeeItem(ModBlocks.COFFEE.get(),
                    new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> COMBAT_KNIFE = ITEMS.register("combat_knife",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> FRENCH_FRIES = ITEMS.register("french_fries",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> credit_card = ITEMS.register("credit_card",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    static final RegistryObject<Item> CAUTION = ITEMS.register("caution",
            () -> new BlockItem(ModBlocks.CAUTION.get(),
                    new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> FIRE_AXE = ITEMS.register("fire_axe",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> KABOB = ITEMS.register("kabob",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> TACO = ITEMS.register("taco",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> BANANA = ITEMS.register("banana",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> VEGGIE_PIZZA = ITEMS.register("veggie_pizza",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static final RegistryObject<Item> TOY_GUN = ITEMS.register("toy_gun",
            () -> new ToyGunItem(new Item.Properties().tab(ModCreativeModeTab.FNFUTIL)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
