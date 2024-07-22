package net.duma.examplemod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.duma.examplemod.item.custom.AncientCompassItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AncientCompassRenderer extends ItemRenderer {
    public AncientCompassRenderer (Minecraft minecraft){
        super(minecraft, minecraft.getTextureManager(), minecraft.getItemRenderer().getItemModelShaper().getModelManager(), minecraft.getItemColors() ,null);
    }

    @Override
    public void render(ItemStack pItemStack, ItemDisplayContext pDisplayContext, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, int pCombinedOverlay, BakedModel pModel) {
        if (pItemStack.getItem() instanceof AncientCompassItem){
            Player player = Minecraft.getInstance().player;
            Level level = player.level();

            if (pItemStack.hasTag() && pItemStack.getTag().getBoolean("StructureTracked")){
                double angle = pItemStack.getTag().getDouble("StructureAngle");

                pPoseStack.pushPose();
                pPoseStack.mulPose(Axis.YP.rotationDegrees((float)Math.toDegrees(angle)));
                super.render(pItemStack, pDisplayContext, pLeftHand, pPoseStack, pBuffer, pCombinedLight, pCombinedOverlay, pModel);
                pPoseStack.popPose();
            } else {
                super.render(pItemStack, pDisplayContext, pLeftHand, pPoseStack, pBuffer, pCombinedLight, pCombinedOverlay, pModel);
            }
        }else {
            super.render(pItemStack, pDisplayContext, pLeftHand, pPoseStack, pBuffer, pCombinedLight, pCombinedOverlay, pModel);
        }
    }
}

