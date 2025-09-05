package org.fnf.fnfutil.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.fnf.fnfutil.block.ModBlocks;
import org.fnf.fnfutil.block.entity.custom.*;
import org.fnf.fnfutil.fnfmain;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, fnfmain.MOD_ID);

    public static final RegistryObject<BlockEntityType<CoffeeMachineBlockEntity>> COFFEE_MACHINE_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("coffee_machine_block_entity", () ->
                            BlockEntityType.Builder.of(CoffeeMachineBlockEntity::new,
                            ModBlocks.COFFEE_MACHINE.get()).build(null));

    public static final RegistryObject<BlockEntityType<FionaBlockEntity>> FIONA_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("fiona_block_entity", () ->
                    BlockEntityType.Builder.of(FionaBlockEntity::new,
                            ModBlocks.FIONA.get()).build(null));

    public static final RegistryObject<BlockEntityType<CassettePlayerBlockEntity>> CASSETTE_PLAYER_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("cassette_player_block_entity", () ->
                    BlockEntityType.Builder.of(CassettePlayerBlockEntity::new,
                            ModBlocks.CASSETTE_PLAYER.get()).build(null));

    public static final RegistryObject<BlockEntityType<CoffeeBlockEntity>> COFFEE_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("coffee_block_entity", () ->
                    BlockEntityType.Builder.of(CoffeeBlockEntity::new,
                            ModBlocks.COFFEE.get()).build(null));

    public static final RegistryObject<BlockEntityType<Sl33pyPlushBlockEntity>> SL33PY_PLUSH_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("sl33py_plush_entity", () ->
                    BlockEntityType.Builder.of(Sl33pyPlushBlockEntity::new,
                            ModBlocks.SL33PY_PLUSH.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
