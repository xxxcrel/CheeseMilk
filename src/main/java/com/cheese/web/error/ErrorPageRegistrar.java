package com.cheese.web.error;

/**
 * Interface to be implemented by types that register {@link ErrorPage ErrorPages}.
 *
 * @author Phillip Webb
 * @since 2.0.0
 */
@FunctionalInterface
public interface ErrorPageRegistrar {

    /**
     * Register pages as required with the given registry.
     * @param registry the error page registry
     */
    void registerErrorPages(ErrorPageRegistry registry);

}

