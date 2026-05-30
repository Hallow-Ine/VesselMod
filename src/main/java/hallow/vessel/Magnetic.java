package hallow.vessel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface Magnetic {

    double RANGE = 4.0;
    double STRENGTH = 0.08;

    private void attractEntity(Entity entity, Vec3d center, double strengthMultiplier) {
        Vec3d dir = center.subtract(entity.getPos());

        double dist = dir.length();

        if (dist < 0.5) return;

        Vec3d force = dir.normalize()
                .multiply(strengthMultiplier*getStrength() / (dist * dist));

        entity.setVelocity(entity.getVelocity().add(force));

        entity.velocityModified = true;
    }

    default void attractItems(World world, BlockPos pos) {
        if (world.isClient) return;

        if(!isMagnetActive(world, pos)) return;

        Vec3d center = Vec3d.ofCenter(pos);

        Box searchBox = new Box(pos).expand(getRange());

        world.getEntitiesByClass(
                ItemEntity.class,
                searchBox,
                item -> true
        ).forEach(item -> attractEntity(item, center, 1));

        world.getEntitiesByClass(
            LivingEntity.class,
            searchBox,
            entity -> (numberOfWornIron(entity) > 0)
        ).forEach(entity -> attractEntity(entity, center, 0.15*(numberOfWornIron(entity)) + 0.30));
    }

    default int numberOfWornIron(LivingEntity entity) {
        
        int number = 0;

        for (ItemStack stack : entity.getArmorItems()) {

            if (stack.getItem() instanceof ArmorItem armor) {

                ArmorMaterial material = armor.getMaterial().value();

                if (material == ArmorMaterials.IRON.value()) {
                    number++;
                }
            }
        }

        return number;
    }

    default boolean isMagnetActive(World world, BlockPos pos) {
        return true;
    }

    default double getRange() { return RANGE; }

    default double getStrength() { return STRENGTH; }
}
