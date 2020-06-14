package org.battleplugins.arena.file.reader.item;

import org.battleplugins.api.inventory.item.ItemStack;
import org.spongepowered.configurate.ConfigurationNode;

import java.util.Optional;

/**
 * A reader which's implementations are capable of
 * reading items from the given inputs.
 *
 * @author Redned
 */
public interface ItemReader {

    /**
     * Reads an {@link ItemStack} from the
     * given {@link ConfigurationNode}
     *
     * @param node the configuration node to read the items tack from
     * @return an item stack from the given configuration node
     */
    Optional<ItemStack> fromNode(ConfigurationNode node);

    /**
     * Reads an {@link ItemStack} from the
     * given {@link ConfigurationNode}
     *
     * @param string the configuration node to read the items tack from
     * @return an item stack from the given configuration node
     */
    Optional<ItemStack> fromString(String string);

    /**
     * Reads an item from the given {@link ConfigurationNode}
     *
     * @param node the config node to read from
     * @return an {@link ItemStack} of the read item
     */
    static Optional<ItemStack> readItem(ConfigurationNode node) {
        // TODO: Add support for more item readers
        Optional<ItemStack> item = GenericItemReader.get().fromNode(node);
        return item;
    }

    /**
     * Reads an item from the given string
     *
     * @param itemStr the item string to read from
     * @return an {@link ItemStack} of the read item
     */
    static Optional<ItemStack> readItem(String itemStr) {
        // TODO: Add support for more item readers
        Optional<ItemStack> item = GenericItemReader.get().fromString(itemStr);
        return item;
    }
}
