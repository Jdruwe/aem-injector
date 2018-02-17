package be.jeroendruwe.aem.core.models;

import be.jeroendruwe.aem.core.annotations.PageTitle;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;

@Model(adaptables = Resource.class)
public class HelloWorldModel {

    @PageTitle(extra = "Super Awesome Annotation", injectionStrategy = InjectionStrategy.OPTIONAL)
    private String awesomePageTitle;

    public String getAwesomePageTitle() {
        return awesomePageTitle;
    }
}
