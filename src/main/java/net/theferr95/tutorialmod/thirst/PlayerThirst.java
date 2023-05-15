package net.theferr95.tutorialmod.thirst;

import net.minecraft.nbt.CompoundTag;

public class PlayerThirst {

    public static final String PROPERTY_NAME = "thirst";

    private int thirst;
    private final int MIN_THIRST = 0;
    private final int MAX_THIRST = 10;

    public int getThirst() {
        return thirst;
    }

    public void addThirst(int thirstPoint) {
        this.thirst = Math.min(this.thirst + thirstPoint, MAX_THIRST);
    }

    public void subThirst(int thirstPoint) {
        this.thirst = Math.max(this.thirst - thirstPoint, MIN_THIRST);
    }

    public void copyFrom(PlayerThirst source) {
        this.thirst = source.thirst;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt(PROPERTY_NAME, this.thirst);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.thirst = nbt.getInt(PROPERTY_NAME);
    }
}
