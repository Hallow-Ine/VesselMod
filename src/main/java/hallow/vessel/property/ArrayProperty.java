package hallow.vessel.property;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.state.property.Property;

import java.util.*;

public class ArrayProperty extends Property<Integer> {
    private final ImmutableSet<Integer[]> values;
    int[] min;
    int[] max;

    protected ArrayProperty(String name, int[] min, int[] max) {
        super(name, Integer.class);
        if (min[0] < 0 || min[1] < 0 || min[2] < 0) {
            throw new IllegalArgumentException("Min values of " + name + " must be 0 or greater");
        } else if (max[0] <= min[0] || max[1] <= min[1] || max[2] <= min[2]) {
            throw new IllegalArgumentException("Max values of " + name + " must be greater than min (" + Arrays.toString(min) + ")");
        } else {
            this.min = min;
            this.max = max;
            Set<Integer[]> set = Sets.<Integer[]>newHashSet();

            for (int i = min[2]; i <= max[2]+1; i++) {
                for (int j = min[1]; j < max[1]+1; j++) {
                    for (int k = min[0]; k < max[0]+1; k++) {
                        set.add(new Integer[]{i, j, k});
                    }

                }
            }

            this.values = ImmutableSet.copyOf(set);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Collection getValues() {
        return this.values;
    }

    @Override
    public String name(Integer value) {
        return "blegh";
    }

    public static ArrayProperty of(String name, int[] min, int[] max) {
        return new ArrayProperty(name, min, max);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Optional parse(String name) {
        return Optional.empty();
    }
}
