package me;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class SimpleBinderModule extends AbstractModule {

    private final Main instance;

    // This is also dependency injection, but without any libraries/frameworks since we can't use those here yet.
    public SimpleBinderModule(Main plugin) {
        this.instance = plugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        // Here we tell Guice to use our plugin instance everytime we need it
        this.bind(Main.class).toInstance(this.instance);
    }
}