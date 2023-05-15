package net.theferr95.tutorialmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.theferr95.tutorialmod.TutorialMod;

public class ThirstHudOverlay {

    private static final ResourceLocation FILLED_THIRST = new ResourceLocation(TutorialMod.MOD_ID,
            "textures/thirst/filled_thirst.png");
    private static final ResourceLocation EMPTY_THIRST = new ResourceLocation(TutorialMod.MOD_ID,
            "textures/thirst/empty_thirst.png");

    private static final int THIRST_HUD_SIZE = 10;

    public static final IGuiOverlay HUD_THIRST = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_THIRST);

        for (int i = 0; i < 10; i++) { //Tutorial: 10 icone di bottiglia
            GuiComponent.blit(poseStack,
                    x - 94 + (i * 9),   // coordinata x
                    y - 54,                     // coordinata y
                    0,                          // offset x
                    0,                          // offset y
                    THIRST_HUD_SIZE,                         // size
                    THIRST_HUD_SIZE,                         // size
                    THIRST_HUD_SIZE,                         // size
                    THIRST_HUD_SIZE                          // size
            );
        }

        RenderSystem.setShaderTexture(0, FILLED_THIRST);
        for (int i = 0; i < 10; i++) { //Tutorial: 10 icone di bottiglia
            if (ClientThirstData.getPlayerThirst() > i) {
                GuiComponent.blit(poseStack,
                        x - 94 + (i * 9),
                        y - 54,
                        0,
                        0,
                        THIRST_HUD_SIZE, THIRST_HUD_SIZE,
                        THIRST_HUD_SIZE, THIRST_HUD_SIZE
                );
            } else {
                break;
            }
        }
    });

}
