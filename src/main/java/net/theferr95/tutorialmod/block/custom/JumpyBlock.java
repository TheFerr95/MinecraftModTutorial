package net.theferr95.tutorialmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class JumpyBlock extends Block {
    public JumpyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(
            BlockState blockState,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand interactionHand,
            BlockHitResult blockHitResult
    ) {
        //Tutorial
        // Called 4 times
        // 2 - Server: Main Hand & Off Hand
        // 2 - Client: Main Hand & Off Hand

        if(level.isClientSide && InteractionHand.MAIN_HAND.equals(interactionHand)){
            player.sendSystemMessage(Component.literal("Right Clicked this!"));
        }

        return super.use(blockState, level, pos, player, interactionHand, blockHitResult);
    }

    @Override
    public void stepOn(
            Level level,
            BlockPos pos,
            BlockState blockState,
            Entity entity
    ) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.JUMP, 200));
        }
        super.stepOn(level, pos, blockState, entity);
    }
}
