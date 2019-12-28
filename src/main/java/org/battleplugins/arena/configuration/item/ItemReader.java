package org.battleplugins.arena.configuration.item;

import org.battleplugins.api.configuration.ConfigurationNode;
import org.battleplugins.api.inventory.item.ItemStack;

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
}
