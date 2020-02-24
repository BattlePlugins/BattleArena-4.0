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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * An {@link ItemReader} capable of reading items
 * directly from {@link ConfigurationNode}s.
 *
 * @author Redned
 */
public abstract class ConfigItemReader implements ItemReader {

    @Override
    public Optional<ItemStack> fromNode(ConfigurationNode node) {
        if (!node.hasNode("type"))
            return Optional.empty();

        Optional<ItemType> itemType = ItemTypes.getItemFromKey(NamespacedKey.of(node.getNode("type").getValue(String.class)));
        if (!itemType.isPresent())
            return Optional.empty();

        ItemStack.Builder builder = ItemStack.builder().type(itemType.get());
        builder.quantity(node.getNode("quantity").getValue(1));
        for (String key : node.getCollectionValue(String.class)) {
            switch (key) {
                case "color":
                    builder.component(ColorComponent.class, Color.getColor(node.getNode(key).getValue(String.class)));
                    break;
                case "custom-model-data":
                case "customModelData":
                    builder.component(CustomModelDataComponent.class, node.getNode(key).getValue(int.class));
                    break;
                case "damage":
                case "durability":
                    builder.component(DamageComponent.class, node.getNode(key).getValue(short.class));
                    break;
                case "display-name":
                case "displayName":
                case "name":
                    builder.component(DisplayNameComponent.class, MessageStyle.translateAlternateColorCodes('&', node.getNode(key).getValue(String.class)));
                    break;
                case "enchants":
                case "enchantments":
                    for (String enchant : node.getNode(key).getCollectionValue(String.class)) {
                        String[] split = enchant.split(" ");
                        builder.enchant(split[0], Integer.parseInt(split[1]));
                    }
                    break;
                case "item-flags":
                case "itemFlags":
                    Set<ItemFlag> flags = new HashSet<>();
                    for (String itemFlag : node.getNode(key).getCollectionValue(String.class)) {
                        for (ItemFlag flag : ItemFlags.values()) {
                            if (flag.getName().equalsIgnoreCase(itemFlag))
                                flags.add(flag);
                        }
                    }
                    builder.component(ItemFlagComponent.class, flags);
                    break;
                case "lore":
                    builder.component(LoreComponent.class, new ArrayList<>(node.getNode(key).getCollectionValue(String.class)));
                    break;
                case "unbreakable":
                    builder.component(UnbreakableComponent.class, Boolean.parseBoolean(node.getNode(key).getValue(String.class)));
                    break;
            }
        }

        return Optional.of(builder.build());
    }
}
