package com.cheese.web.error;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Provides access to error attributes which can be logged or presented to the user.
 *
 * @author Phillip Webb
 * @since 2.0.0
 * @see DefaultErrorAttributes
 */
public interface ErrorAttributes {

    /**
     * Returns a {@link Map} of the error attributes. The map can be used as the model of
     * an error page {@link ModelAndView}, or returned as a
     * {@link ResponseBody @ResponseBody}.
     * @param webRequest the source request
     * @param includeStackTrace if stack trace elements should be included
     * @return a map of error attributes
     */
    Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace);

    /**
     * Return the underlying cause of the error or {@code null} if the error cannot be
     * extracted.
     * @param webRequest the source request
     * @return the {@link Exception} that caused the error or {@code null}
     */
    Throwable getError(WebRequest webRequest);

}

