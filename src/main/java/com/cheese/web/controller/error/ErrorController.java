package com.cheese.web.controller.error;

import org.springframework.stereotype.Controller;

/**
 * Marker interface used to indicate that a {@link Controller @Controller} is used to
 * render errors. Primarily used to know the error paths that will not need to be secured.
 *
 * @author Phillip Webb
 * @since 2.0.0
 */
@FunctionalInterface
public interface ErrorController {

    /**
     * Returns the path of the error page.
     * @return the error path
     */
    String getErrorPath();

}
