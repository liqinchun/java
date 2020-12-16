package com.example.java.yangtools;

public interface YangClassLoaderRegistry {

    /**
     * Get the class loader registered for the given model id.
     *
     * @param modelId model identifier
     * @return class loader registered for the model
     */
    ClassLoader getClassLoader(String modelId);

    /**
     * Register the class loader for the specified model.
     *
     * @param modelId     model identifier
     * @param classLoader class loader
     */
    void registerClassLoader(String modelId, ClassLoader classLoader);

    /**
     * Register the class loader for the specified model.
     *
     * @param modelId model identifier
     */
    void unregisterClassLoader(String modelId);
}
