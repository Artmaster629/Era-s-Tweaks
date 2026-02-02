package net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.block;

import net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.block_entity.TotemBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TotemBlock extends Block implements EntityBlock {

    public TotemBlock(Properties props) {
        super(props);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TotemBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {

        if (level.isClientSide) return null;

        return (lvl, pos, st, be) -> {
            if (be instanceof TotemBlockEntity totem) {
                totem.tick();
            }
        };
    }
}
