package net.theferr95.tutorialmod.thirst;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PlayerThirstProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerThirst> PLAYER_THIRST = CapabilityManager.get(new CapabilityToken<>() {
    });

    private PlayerThirst thirst = null;
    private final LazyOptional<PlayerThirst> optional = LazyOptional.of(this::createPlayerThirst);

    private PlayerThirst createPlayerThirst() {
        if (Objects.isNull(this.thirst)) {
            this.thirst = new PlayerThirst();
        }
        return this.thirst;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (PLAYER_THIRST.equals(cap)) {
            return this.optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        final CompoundTag nbt = new CompoundTag();
        createPlayerThirst().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerThirst().loadNBTData(nbt);
    }
}
