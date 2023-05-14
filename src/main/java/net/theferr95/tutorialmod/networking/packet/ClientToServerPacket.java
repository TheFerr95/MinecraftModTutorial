package net.theferr95.tutorialmod.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface ClientToServerPacket {

    default void toBytes(FriendlyByteBuf buf) {

    }

    boolean handle(Supplier<NetworkEvent.Context> supplier);

}
