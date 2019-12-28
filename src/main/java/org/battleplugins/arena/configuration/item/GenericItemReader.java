package org.battleplugins.arena.configuration.item;

import org.battleplugins.api.configuration.ConfigurationNode;
import org.battleplugins.api.inventory.item.ItemStack;
import org.battleplugins.api.inventory.item.ItemType;
import org.battleplugins.api.inventory.item.ItemTypes;
import org.battleplugins.api.inventory.item.component.*;
import org.battleplugins.api.inventory.item.component.flag.ItemFlag;
import org.battleplugins.api.inventory.item.component.flag.ItemFlags;
import org.battleplugins.api.message.MessageStyle;
import org.battleplugins.api.util.NamespacedKey;

import java.awt.Color;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * An {@link ItemReader} capable of reading items
 * directly from {@link ConfigurationNode}s and
 * current BattleArena item strings.
 *
 * @author Redned
 */
public class GenericItemReader extends ConfigItemReader {

    @Override
    public Optional<ItemStack> fromString(String string) {
        String[] split = string.split("\\{");
        Optional<ItemType> itemType = ItemTypes.getItemFromKey(NamespacedKey.minecraft(split[0]));
        if (!itemType.isPresent())
            return Optional.empty();

        ItemStack.Builder builder = ItemStack.builder().type(itemType.get());
        builder.type(itemType.get());
        if (split.length == 1)
            return Optional.of(builder.quantity(1).build());

        String data = split[1].replace("\\}", "");
        for (String meta : data.split(";")) {
            String[] option = meta.split("=");
            switch (option[0]) {
                case "color":
                    builder.component(ColorComponent.class, Color.getColor(option[1]));
                    break;
                case "custom-model-data":
                case "customModelData":
                    builder.component(CustomModelDataComponent.class, Integer.parseInt(option[1]));
                    break;
                case "damage":
                case "durability":
                    builder.component(DamageComponent.class, Short.parseShort(option[1]));
                    break;
                case "display-name":
                case "displayName":
                case "name":
                    builder.component(DisplayNameComponent.class, MessageStyle.translateAlternateColorCodes('&', option[1]));
                    break;
                case "enchants":
                case "enchantments":
                    for (String enchant : getList(meta)) {
                        String[] del = enchant.split(":");
                        builder.enchant(del[0], Integer.parseInt(del[1]));
                    }
                    break;
                case "item-flags":
                case "itemFlags":
                    Set<ItemFlag> flags = new HashSet<>();
                    for (String itemFlag : getList(meta)) {
                        for (ItemFlag flag : ItemFlags.values()) {
                            if (flag.getName().equalsIgnoreCase(itemFlag))
                                flags.add(flag);
                        }
                    }
                    builder.component(ItemFlagComponent.class, flags);
                    break;
                case "lore":
                    builder.component(LoreComponent.class, getList(meta));
                    break;
                case "quantity":
                    builder.quantity(Integer.parseInt(option[1]));
                    break;
                case "unbreakable":
                    builder.component(UnbreakableComponent.class, Boolean.parseBoolean(option[1]));
                    break;
            }
        }
        return Optional.of(builder.build());
    }

    private List<String> getList(String value) {
        return Arrays.asList(value.split("=")[1].replace("[", "")
                .replace("]", "").split(","));
    }
}
