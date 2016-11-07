package com.scottwseo.commons.util;

import com.scottwseo.commons.rest.featureflag.Issue;
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
    @InfoLink("https://github.com/scott-seo/dvdstore-api/issues/1")
    @Owner("Scott Seo")
    @Issue("Issue-1")
    @Label("Inventory Update")
    INVENTORY_UPDATE;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

}