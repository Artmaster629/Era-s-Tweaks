package net.artmaster.era_tweaks.custom.player_classes.wizard.priest.custom.block_entity;

import net.artmaster.era_tweaks.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;


public class TotemBlockEntity extends BlockEntity {

    private UUID owner;

    public TotemBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TOTEM_BE.get(), pos, state);
    }

    public void setOwner(UUID playerId) {
        this.owner = playerId;
        setChanged(); // чтобы данные сохранялись
    }

    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (owner != null) owner = tag.getUUID("owner");
    }


    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        if (owner != null) tag.putUUID("owner", owner);
    }


    public static HitResult getLookHit(Player player, double distance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 end = eyePos.add(look.scale(distance));

        ClipContext ctx = new ClipContext(
                eyePos,
                end,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        );

        return player.level().clip(ctx);
    }





    public void tick() {
        if (level == null || level.isClientSide()) return; // только сервер
        int radius = 20;

//        if (level.getGameTime() % 20 == 0) {
//            System.out.println("Totem tick at " + worldPosition);
//        }

        AABB box = new AABB(worldPosition).inflate(radius);

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, box)) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 2, true, true));
        }
    }

}
