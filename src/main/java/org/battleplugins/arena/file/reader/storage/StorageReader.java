package org.battleplugins.arena.file.reader.storage;

/**
 * A reader capable of serializing and deserializing the specified
 * value (T) from the storage format (F).
 *
 * @param <T> the value to read from the storage format
 * @param <F> the format to read from
 *
 * @author Redned
 */
public interface StorageReader<T, F> {

    /**
     * Reads the value from the given storage format
     *
     * @param storageFormat the storage format
     * @return the value from the given storage format
     */
    T read(F storageFormat);

    /**
     * Writes the value to the specified storage format
     *
     * @param value what to write to the storage format
     * @param storageFormat the storage format
     * @return the value to the specified storage format
     */
    boolean write(T value, F storageFormat);
}
