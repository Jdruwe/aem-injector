package be.jeroendruwe.aem.core.annotations.injectors;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public class AbstractInjector {

    protected Resource getResource(Object adaptable) {

        if (adaptable instanceof SlingHttpServletRequest) {
            return ((SlingHttpServletRequest) adaptable).getResource();
        }
        if (adaptable instanceof Resource) {
            return (Resource) adaptable;
        }

        return null;
    }

    protected ResourceResolver getResourceResolver(Object adaptable) {

        if (adaptable instanceof SlingHttpServletRequest) {
            return ((SlingHttpServletRequest) adaptable).getResourceResolver();
        }
        if (adaptable instanceof Resource) {
            return ((Resource) adaptable).getResourceResolver();
        }

        return null;
    }

    protected PageManager getPageManager(Object adaptable) {
        ResourceResolver resolver = getResourceResolver(adaptable);

        if (resolver != null) {
            return resolver.adaptTo(PageManager.class);
        }

        return null;
    }


    protected Page getResourcePage(Object adaptable) {

        PageManager pageManager = getPageManager(adaptable);
        Resource resource = getResource(adaptable);

        if (pageManager != null && resource != null) {
            return pageManager.getContainingPage(resource);
        }

        return null;
    }

}
