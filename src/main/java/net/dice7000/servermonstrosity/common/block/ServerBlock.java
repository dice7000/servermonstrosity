package net.dice7000.servermonstrosity.common.block;

import net.dice7000.servermonstrosity.common.registry.SMEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class ServerBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ServerBlock() {
        super(BlockBehaviour.Properties.of());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        int stackedAsInt = isThreeStacked(p_60567_, p_60568_);
        if (stackedAsInt > 0 && stackedAsInt < 4) {
            BlockPos spawnPos;
            if (stackedAsInt == 1) {
                spawnPos = p_60568_;
            } else if (stackedAsInt == 2) {
                spawnPos = p_60568_.below(1);
            } else {
                spawnPos = p_60568_.below(2);
            }
            Direction facing = p_60566_.getValue(BlockStateProperties.HORIZONTAL_FACING);
            spawnBoss(p_60567_, spawnPos, facingToYaw(facing));
        }

        super.onPlace(p_60566_, p_60567_, p_60568_, p_60569_, p_60570_);
    }

    private int isThreeStacked(Level level, BlockPos pos) {
        Block block = this;

        BlockPos pos1 = pos.above(2);
        BlockPos pos2 = pos.above(1);
        BlockPos pos3 = pos.below(1);
        BlockPos pos4 = pos.below(2);

        boolean above2 = level.getBlockState(pos1).getBlock() == block &&
                level.getBlockState(pos2).getBlock() == block;
        boolean aboveBelow = level.getBlockState(pos2).getBlock() == block &&
                level.getBlockState(pos3).getBlock() == block;
        boolean below2 = level.getBlockState(pos3).getBlock() == block &&
                level.getBlockState(pos4).getBlock() == block;
        if (above2) return 1; else if (aboveBelow) return 2; else if (below2) return 3; else return 0;
    }

    private float facingToYaw(Direction facing) {
        return switch (facing) {
            case NORTH -> 180f;
            case SOUTH -> 0f;
            case WEST  -> 90f;
            case EAST  -> -90f;
            default -> 0f;
        };
    }

    private void spawnBoss(Level level, BlockPos pos, float yaw) {
        EntityType<?> type = SMEntity.SERVER_MONOLITH.get();

        Entity entity = type.create(level);
        if (entity == null) return;

        level.destroyBlock(pos.above(2), false, entity);
        level.destroyBlock(pos.above(1), false, entity);
        level.destroyBlock(pos, false, entity);

        entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, yaw, 0);
        level.addFreshEntity(entity);
    }
}
