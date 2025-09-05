package org.fnf.fnfutil.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.fnf.fnfutil.block.entity.custom.CassettePlayerBlockEntity;
import org.fnf.fnfutil.block.entity.ModBlockEntities;

import javax.annotation.Nullable;

public class CassettePlayerBlock extends BaseEntityBlock {
    public static final IntegerProperty ANIMATION = IntegerProperty.create("animation", 0, 1);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public CassettePlayerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && player.isShiftKeyDown() && player.getItemInHand(hand).isEmpty()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (!(be instanceof CassettePlayerBlockEntity cassetteEntity)) return InteractionResult.PASS;

            // Create item stack and copy cassette data
            ItemStack stack = new ItemStack(this);
            CompoundTag tag = new CompoundTag();
            if (cassetteEntity.hasCassette()) {
                tag.put("Cassette", cassetteEntity.getCassette().save(new CompoundTag()));
                tag.putBoolean("Playing", cassetteEntity.isPlaying());
            }
            stack.setTag(tag);

            // Give to player and remove block
            player.setItemInHand(hand, stack);
            level.removeBlock(pos, false);
            return InteractionResult.SUCCESS;
        }

        // Normal cassette insert/eject logic
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof CassettePlayerBlockEntity cassetteEntity)) return InteractionResult.PASS;

        ItemStack heldItem = player.getItemInHand(hand);

        if (!cassetteEntity.hasCassette() && heldItem.getItem() instanceof RecordItem) {
            cassetteEntity.insertCassette(heldItem.split(1));
            return InteractionResult.CONSUME;
        } else if (cassetteEntity.hasCassette()) {
            ItemStack ejected = cassetteEntity.ejectCassette();
            if (!player.getInventory().add(ejected)) {
                player.drop(ejected, false);
            }
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }


    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos,
                         BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof CassettePlayerBlockEntity cassetteEntity && cassetteEntity.hasCassette()) {
                ItemStack cassette = cassetteEntity.ejectCassette();
                cassetteEntity.stopPlaying();
                cassetteEntity.setChanged();
                if (!level.isClientSide) playerDrop(level, pos, cassette);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    private void playerDrop(Level level, BlockPos pos, ItemStack stack) {
        if (!stack.isEmpty()) {
            net.minecraft.world.entity.item.ItemEntity itemEntity = new net.minecraft.world.entity.item.ItemEntity(
                    level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction dir = state.getValue(FACING);
        switch (dir) {
            case NORTH:
                // Original: box(5, 0, 5, 13, 1.6, 11)
                return Block.box(5.0D, 0.0D, 3.0D, 11.0D, 1.6D, 11.0D);
            case EAST:
                // Original: box(5, 0, 5, 11, 1.6, 13)
                return Block.box(5.0D, 0.0D, 5.0D, 13.0D, 1.6D, 11.0D);
            case WEST:
                // Original: box(5, 0, 3, 11, 1.6, 11)
                return Block.box(3.0D, 0.0D, 5.0D, 11.0D, 1.6D, 11.0D);
            default: // SOUTH
                // Original: box(3, 0, 5, 11, 1.6, 11)
                return Block.box(5.0D, 0.0D, 5.0D, 11.0D, 1.6D, 13.0D);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ANIMATION, FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        CassettePlayerBlockEntity entity = new CassettePlayerBlockEntity(pos, state);

        // Restore cassette and playback from item NBT
        ItemStack stack = entity.getBlockState().getBlock() instanceof CassettePlayerBlock ? new ItemStack(this) : ItemStack.EMPTY;
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("Cassette")) {
            ItemStack cassette = ItemStack.of(tag.getCompound("Cassette"));
            entity.insertCassette(cassette);
            if (tag.getBoolean("Playing")) {
                entity.startPlaying();
            }
        }

        return entity;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return createTickerHelper(type,
                ModBlockEntities.CASSETTE_PLAYER_BLOCK_ENTITY.get(),
                CassettePlayerBlockEntity::tick);
    }
}
