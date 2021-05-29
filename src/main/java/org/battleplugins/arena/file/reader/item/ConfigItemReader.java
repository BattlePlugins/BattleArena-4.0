package org.battleplugins.arena.file.reader.item;

import net.kyori.adventure.text.Component;
import org.battleplugins.api.inventory.item.ItemStack;
import org.battleplugins.api.inventory.item.ItemType;
import org.battleplugins.api.inventory.item.ItemTypes;
import org.battleplugins.api.inventory.item.component.*;
import org.battleplugins.api.inventory.item.component.flag.ItemFlag;
import org.battleplugins.api.inventory.item.component.flag.ItemFlags;
import org.battleplugins.api.util.Identifier;
import org.spongepowered.configurate.ConfigurationNode;

import java.awt.Color;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An {@link ItemReader} capable of reading items
 * directly from {@link ConfigurationNode}s.
 *
 * @author Redned
 */
public abstract class ConfigItemReader implements ItemReader {

    @Override
    public Optional<ItemStack> fromNode(ConfigurationNode node) {
        if (node.getNode("type").getValue() == null) {
            return Optional.empty();
        }
        Optional<ItemType> itemType = ItemTypes.getItemFromIdentifier(Identifier.of(node.getNode("type").getString()));
        if (!itemType.isPresent())
            return Optional.empty();

        ItemStack.Builder builder = ItemStack.builder().type(itemType.get());
        builder.quantity(node.getNode("quantity").getInt(1));
        Map<Object, ? extends ConfigurationNode> mapResult = node.getChildrenMap();
        for (Map.Entry<Object, ? extends ConfigurationNode> entry : mapResult.entrySet()) {
            String key = (String) entry.getKey();
            switch (key) {
                case "color":
                    builder.component(ColorComponent.class, Color.getColor(node.getNode(key).getString()));
                    break;
                case "custom-model-data":
                case "customModelData":
                    builder.component(CustomModelDataComponent.class, node.getNode(key).getInt());
                    break;
                case "damage":
                case "durability":
                    builder.component(DamageComponent.class, (short) node.getNode(key).getInt());
                    break;
                case "display-name":
                case "displayName":
                case "name":
                    builder.component(DisplayNameComponent.class, Component.text(node.getNode(key).getString()));
                    break;
                case "enchants":
                case "enchantments":
                    for (ConfigurationNode enchantNode : node.getNode(key).getChildrenList()) {
                        String enchant = enchantNode.getString();
                        String[] split = enchant.split(" ");
                        builder.enchant(split[0], Integer.parseInt(split[1]));
                    }
                    break;
                case "item-flags":
                case "itemFlags":
                    Set<ItemFlag> flags = new HashSet<>();
                    for (ConfigurationNode itemFlagNode : node.getNode(key).getChildrenList()) {
                        String itemFlag = itemFlagNode.getString();
                        for (ItemFlag flag : ItemFlags.values()) {
                            if (flag.getName().equalsIgnoreCase(itemFlag))
                                flags.add(flag);
                        }
                    }
                    builder.component(ItemFlagComponent.class, flags);
                    break;
                case "lore":
                    builder.component(LoreComponent.class, node.getNode(key).getChildrenList().stream().map(str -> Component.text(str.getString())).collect(Collectors.toList()));
                    break;
                case "unbreakable":
                    builder.component(UnbreakableComponent.class, node.getNode(key).getBoolean());
                    break;
            }
        }

        return Optional.of(builder.build());
    }
}
