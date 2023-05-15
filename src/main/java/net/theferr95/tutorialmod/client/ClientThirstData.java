package net.theferr95.tutorialmod.client;

public class ClientThirstData {

    private static int playerThirst;

    public static void set(int playerThirst) {
        ClientThirstData.playerThirst = playerThirst;
    }

    public static int getPlayerThirst() {
        return ClientThirstData.playerThirst;
    }

}
