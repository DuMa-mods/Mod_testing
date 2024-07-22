package net.duma.examplemod.item.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;



public class AncientCompassItem extends Item
{
    public AncientCompassItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!pLevel.isClientSide){
            for (Player player : pLevel.players()){
                BlockPos playerPos = player.blockPosition();
                ServerLevel serverWorld = (ServerLevel) pLevel;
                BlockPos strucPos = serverWorld.findNearestMapStructure(StructureTags.SHIPWRECK, playerPos, 100, false );

                if (strucPos != null){
                    pStack.getOrCreateTag().putLong("StructurePos",strucPos.asLong());
                    pStack.getOrCreateTag().putString("StructureDimension",pLevel.dimension().location().toString());
                    pStack.getOrCreateTag().putBoolean("StructureTracked",true);

                    double dx = strucPos.getX() - playerPos.getX();
                    double dz = strucPos.getZ() - playerPos.getZ();
                    double angle = Math.atan2(dz, dx);

                    pStack.getOrCreateTag().putDouble("angle", angle);
                    
                } else {
                    pStack.getOrCreateTag().remove("StructurePos");
                    pStack.getOrCreateTag().remove("StructureDimension");
                    pStack.getOrCreateTag().putBoolean("StructureTracked",false);
                }


            }
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
}
