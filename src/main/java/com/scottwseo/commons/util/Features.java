package com.scottwseo.commons.util;

import com.scottwseo.commons.togglz.Issue;
import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.InfoLink;
import org.togglz.core.annotation.Label;
import org.togglz.core.annotation.Owner;
import org.togglz.core.context.FeatureContext;

/**
 * Created by seos on 12/18/14.
 */
public enum Features implements Feature {

    @EnabledByDefault
    @InfoLink("https://github.com/")
    @Owner("Scott Seo")
    @Issue("Issue")
    @Label("New Issue")
    NEW_FEATURE;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

}