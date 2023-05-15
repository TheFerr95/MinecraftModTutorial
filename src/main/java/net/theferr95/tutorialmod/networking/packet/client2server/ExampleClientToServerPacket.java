package net.theferr95.tutorialmod.networking.packet.client2server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.network.NetworkEvent;
import net.theferr95.tutorialmod.networking.packet.MessagePacket;

import java.util.function.Supplier;

public class ExampleClientToServerPacket implements MessagePacket {

    public ExampleClientToServerPacket() {
    }

    public ExampleClientToServerPacket(FriendlyByteBuf buf) {

    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //Tutorial HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            EntityType.COW.spawn(
                    level,
                    null,
                    null,
                    player.blockPosition(),
                    MobSpawnType.COMMAND,
                    true,
                    false
            );
        });
        return true;
    }
}
