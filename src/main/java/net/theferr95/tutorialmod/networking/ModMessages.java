package net.theferr95.tutorialmod.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.theferr95.tutorialmod.TutorialMod;
import net.theferr95.tutorialmod.networking.packet.client2server.DrinkWaterClientToServerPacket;
import net.theferr95.tutorialmod.networking.packet.client2server.ExampleClientToServerPacket;
import net.theferr95.tutorialmod.networking.packet.server2client.ThirstDataSyncServerToClientPacket;

public class ModMessages {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(TutorialMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ExampleClientToServerPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ExampleClientToServerPacket::new)
                .encoder(ExampleClientToServerPacket::toBytes)
                .consumerMainThread(ExampleClientToServerPacket::handle)
                .add();

        net.messageBuilder(DrinkWaterClientToServerPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DrinkWaterClientToServerPacket::new)
                .encoder(DrinkWaterClientToServerPacket::toBytes)
                .consumerMainThread(DrinkWaterClientToServerPacket::handle)
                .add();

        net.messageBuilder(ThirstDataSyncServerToClientPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ThirstDataSyncServerToClientPacket::new)
                .encoder(ThirstDataSyncServerToClientPacket::toBytes)
                .consumerMainThread(ThirstDataSyncServerToClientPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
