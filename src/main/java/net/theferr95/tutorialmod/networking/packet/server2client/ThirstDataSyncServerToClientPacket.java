package net.theferr95.tutorialmod.networking.packet.server2client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.theferr95.tutorialmod.client.ClientThirstData;
import net.theferr95.tutorialmod.networking.packet.MessagePacket;

import java.util.function.Supplier;

public class ThirstDataSyncServerToClientPacket implements MessagePacket {

    private final int thirst;

    public ThirstDataSyncServerToClientPacket(int thirst) {
        this.thirst = thirst;
    }

    public ThirstDataSyncServerToClientPacket(FriendlyByteBuf buf) {
        this.thirst = buf.readInt(); //Tutorial in
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(thirst); //Tutorial out
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientThirstData.set(thirst);
        });
        return true;
    }

}
