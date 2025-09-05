package org.fnf.fnfutil.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.fnf.fnfutil.block.custom.*;

import static org.fnf.fnfutil.fnfmain.MOD_ID;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    public static final RegistryObject<Block> COFFEE_MACHINE = BLOCKS.register("coffee_machine",
            () -> new CoffeeMachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(1.0F)
                    .noOcclusion()));

    public static final RegistryObject<Block> CASSETTE_PLAYER = BLOCKS.register("cassette_player",
            () -> new CassettePlayerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(1.0F)
                    .noOcclusion()));

    public static final RegistryObject<Block> COFFEE = BLOCKS.register("coffee",
            () -> new CoffeeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(0.1F)
                    .noOcclusion()));

    public static final RegistryObject<Block> FIONA = BLOCKS.register("fiona",
            () -> new FionaBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(0.1F)
                    .noOcclusion()));

    public static final RegistryObject<Block> SL33PY_PLUSH = BLOCKS.register("sl33py_plush",
            () -> new Sl33pyPlushBlock());

    public static final RegistryObject<Block> CAUTION = BLOCKS.register("caution",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).sound(SoundType.STONE)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
