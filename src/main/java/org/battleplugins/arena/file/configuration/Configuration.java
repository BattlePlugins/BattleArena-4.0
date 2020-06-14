package org.battleplugins.arena.file.configuration;

import com.google.common.reflect.TypeToken;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.ValueType;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A holder class for a configuration containing both the
 * {@link ConfigurationLoader} and the {@link ConfigurationNode}.
 * Serves as a wrapper around the root node of the configuration
 * to easily allow for modification and reduce additional calls.
 *
 * @author Redned
 */
public class Configuration implements ConfigurationNode {

    private final ConfigurationLoader<?> loader;
    private ConfigurationNode rootNode;

    public Configuration(ConfigurationLoader<?> loader, ConfigurationNode rootNode) {
        this.loader = loader;
        this.rootNode = rootNode;
    }

    /**
     * Returns the {@link ConfigurationLoader} for this configuration
     *
     * @return the configuration loader for this configuration
     */
    public ConfigurationLoader<?> getLoader() {
        return this.loader;
    }

    /**
     * Saves this configuration
     *
     * @throws IOException if the configuration could not be saved
     */
    public void save() throws IOException {
        this.loader.save(this.rootNode);
    }

    /**
     * Reloads this configuration
     */
    public void reload() throws IOException {
        this.rootNode = this.loader.load();
    }

    @Override
    public Object getKey() {
        return this.rootNode.getKey();
    }

    @Override
    public Object[] getPath() {
        return this.rootNode.getPath();
    }

    @Override
    public ConfigurationNode getParent() {
        return this.rootNode.getParent();
    }

    @Override
    public ConfigurationNode getNode(Object... path) {
        return this.rootNode.getNode(path);
    }

    @Override
    public boolean isVirtual() {
        return this.rootNode.isVirtual();
    }

    @Override
    public ConfigurationOptions getOptions() {
        return this.rootNode.getOptions();
    }

    @Override
    public ValueType getValueType() {
        return this.rootNode.getValueType();
    }

    @Override
    public List<? extends ConfigurationNode> getChildrenList() {
        return this.rootNode.getChildrenList();
    }

    @Override
    public Map<Object, ? extends ConfigurationNode> getChildrenMap() {
        return this.rootNode.getChildrenMap();
    }

    @Override
    public Object getValue(Object def) {
        return this.rootNode.getValue(def);
    }

    @Override
    public Object getValue(Supplier<Object> defSupplier) {
        return this.rootNode.getValue(defSupplier);
    }

    @Override
    public <T> T getValue(Function<Object, T> transformer, T def) {
        return this.rootNode.getValue(transformer, def);
    }

    @Override
    public <T> T getValue(Function<Object, T> transformer, Supplier<T> defSupplier) {
        return this.rootNode.getValue(transformer, defSupplier);
    }

    @Override
    public <T> List<T> getList(Function<Object, T> transformer) {
        return this.rootNode.getList(transformer);
    }

    @Override
    public <T> List<T> getList(Function<Object, T> transformer, List<T> def) {
        return this.rootNode.getList(transformer, def);
    }

    @Override
    public <T> List<T> getList(Function<Object, T> transformer, Supplier<List<T>> defSupplier) {
        return this.rootNode.getList(transformer, defSupplier);
    }

    @Override
    public <T> List<T> getList(TypeToken<T> type, List<T> def) throws ObjectMappingException {
        return this.rootNode.getList(type, def);
    }

    @Override
    public <T> List<T> getList(TypeToken<T> type, Supplier<List<T>> defSupplier) throws ObjectMappingException {
        return this.rootNode.getList(type, defSupplier);
    }

    @Override
    public <T> T getValue(TypeToken<T> type, T def) throws ObjectMappingException {
        return this.rootNode.getValue(type, def);
    }

    @Override
    public <T> T getValue(TypeToken<T> type, Supplier<T> defSupplier) throws ObjectMappingException {
        return this.rootNode.getValue(type, defSupplier);
    }

    @Override
    public ConfigurationNode setValue(Object value) {
        return this.rootNode.setValue(value);
    }

    @Override
    public ConfigurationNode mergeValuesFrom(ConfigurationNode other) {
        return this.rootNode.mergeValuesFrom(other);
    }

    @Override
    public boolean removeChild(Object key) {
        return this.rootNode.removeChild(key);
    }

    @Override
    public ConfigurationNode getAppendedNode() {
        return this.rootNode.getAppendedNode();
    }

    @Override
    public ConfigurationNode copy() {
        return this.rootNode.copy();
    }
}
