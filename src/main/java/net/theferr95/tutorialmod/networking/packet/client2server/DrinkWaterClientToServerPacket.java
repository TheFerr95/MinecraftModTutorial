package net.theferr95.tutorialmod.networking.packet.client2server;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;
import net.theferr95.tutorialmod.networking.ModMessages;
import net.theferr95.tutorialmod.networking.packet.MessagePacket;
import net.theferr95.tutorialmod.networking.packet.server2client.ThirstDataSyncServerToClientPacket;
import net.theferr95.tutorialmod.thirst.PlayerThirstProvider;

import java.util.function.Supplier;

public class DrinkWaterClientToServerPacket implements MessagePacket {

    private static final String MESSAGE_DRINK_WATER = "message.tutorialmod.drink_water";
    private static final String MESSAGE_NO_WATER = "message.tutorialmod.no_water";

    public DrinkWaterClientToServerPacket() {
    }

    public DrinkWaterClientToServerPacket(FriendlyByteBuf buf) {

    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            final ServerPlayer player = context.getSender();
            final ServerLevel level = player.getLevel();

            if (hasWaterAroundThem(player, level, 2)) {
                // notify the player that water has been drunk
                player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_WATER).withStyle(ChatFormatting.DARK_AQUA));

                // play the drinking sound
                level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS,
                        0.5F, level.random.nextFloat() * 0.1F + 0.9F);

                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(playerThirst -> {
                    // increase the water level / thirst level of player
                    playerThirst.addThirst(1);

                    // output the current thirst level
                    //player.sendSystemMessage(Component.literal("Current Thirst " + playerThirst.getThirst()).withStyle(ChatFormatting.AQUA));
                    ModMessages.sendToPlayer(new ThirstDataSyncServerToClientPacket(playerThirst.getThirst()), player);
                });

            } else {
                // notify the player that there is no water to drink
                player.sendSystemMessage(Component.translatable(MESSAGE_NO_WATER).withStyle(ChatFormatting.RED));
                // output the current thirst level

                player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(playerThirst -> {
                    //player.sendSystemMessage(Component.literal("Current Thirst " + playerThirst.getThirst()).withStyle(ChatFormatting.AQUA));
                    ModMessages.sendToPlayer(new ThirstDataSyncServerToClientPacket(playerThirst.getThirst()), player);
                });

            }

        });
        return true;
    }

    //TODO potrebbe essere spostato
    private boolean hasWaterAroundThem(ServerPlayer player, ServerLevel level, int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(blockState -> blockState.is(Blocks.WATER)).toArray().length > 0;
    }
}
