package hallow.vessel.item;

import hallow.vessel.ModComponents;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SoulContractItem extends Item {

    public SoulContractItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient()) {
            player.sendMessage(Text.literal("player clicked wiht uuid: " + player.getUuidAsString()));
            stack.set(ModComponents.SOUL_UUID_TYPE, player.getUuid());
        }
        return TypedActionResult.success(stack, world.isClient());
    }
}