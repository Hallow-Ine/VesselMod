package hallow.vessel.item;

import java.util.List;

import hallow.vessel.block.ModBlocks;
import hallow.vessel.component.ModComponents;
import hallow.vessel.soul.SoulContractScreen;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
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

        if(world.isClient()) {
            MinecraftClient.getInstance().setScreen(new SoulContractScreen(player, stack, hand));
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        
        ItemStack stack = context.getStack();

        if(stack.getOrDefault(ModComponents.ACTIVE, false)) return ActionResult.PASS;

        World world = context.getWorld();
        
        BlockState block = world.getBlockState(context.getBlockPos());

        if(!block.isOf(ModBlocks.ALTAR_BLOCK)) return ActionResult.PASS;

        if (!world.isClient()) stack.set(ModComponents.ACTIVE, true);

        return ActionResult.SUCCESS;
    }
    //

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getOrDefault(ModComponents.ACTIVE, false) || super.hasGlint(stack);
    }

    @Override
    public Text getName(ItemStack stack) {
        if(stack.contains(ModComponents.SOUL_UUID)) return Text.translatable("item.vessel.soul_contract").formatted(Formatting.DARK_PURPLE);
        
        return super.getName(stack);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {

        if(!itemStack.contains(ModComponents.SOUL_UUID)) {
            tooltip.add(Text.translatable("item.vessel.soul_contract_tooltip.default"));
            return;
        }

        String victimName = itemStack.getOrDefault(ModComponents.SOUL_NAME, "Jeb_");

        if(!itemStack.getOrDefault(ModComponents.ACTIVE, false)) {
            
            tooltip.add(Text.translatable("item.vessel.soul_contract_tooltip.bound", Text.literal(victimName).formatted(Formatting.RED)));
            return;
        }

        tooltip.add(Text.translatable("item.vessel.soul_contract_tooltip.active", Text.literal(victimName).formatted(Formatting.RED)));
    }
    
}