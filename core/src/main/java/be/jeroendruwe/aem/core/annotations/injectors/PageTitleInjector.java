package be.jeroendruwe.aem.core.annotations.injectors;

import be.jeroendruwe.aem.core.annotations.PageTitle;
import com.day.cq.wcm.api.Page;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;
import org.apache.sling.models.spi.injectorspecific.AbstractInjectAnnotationProcessor2;
import org.apache.sling.models.spi.injectorspecific.InjectAnnotationProcessor2;
import org.apache.sling.models.spi.injectorspecific.StaticInjectAnnotationProcessorFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

@Component(service = {Injector.class, StaticInjectAnnotationProcessorFactory.class}, property = {
        Constants.SERVICE_RANKING + ":Integer=" + 4300
})
public class PageTitleInjector extends AbstractInjector implements Injector, StaticInjectAnnotationProcessorFactory {

    public static final String NAME = "page-title-annotation";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Object getValue(final Object adaptable, final String name, final Type type, final AnnotatedElement element,
                           final DisposalCallbackRegistry callbackRegistry) {

        PageTitle annotation = element.getAnnotation(PageTitle.class);
        if (annotation == null) {
            //If the annotation was not found on the element -> return null -> use another injector
            return null;
        }

        // Only class types are supported
        if (!(type instanceof Class<?>)) {
            return null;
        }

        Class<?> requestedClass = (Class<?>) type;

        if (requestedClass.equals(String.class)) {
            Page page = getResourcePage(adaptable);
            return processTitle(page.getTitle(), annotation.extra());
        }

        return null;
    }

    private String processTitle(String title, String extra) {
        return title + " " + extra;
    }

    @Override
    public InjectAnnotationProcessor2 createAnnotationProcessor(AnnotatedElement annotatedElement) {

        PageTitle annotation = annotatedElement.getAnnotation(PageTitle.class);
        if (annotation != null) {
            return new PageTitleAnnotationProcessor(annotation);
        }
        return null;
    }

    private static class PageTitleAnnotationProcessor extends AbstractInjectAnnotationProcessor2 {

        private final PageTitle annotation;

        PageTitleAnnotationProcessor(final PageTitle annotation) {
            this.annotation = annotation;
        }

        @Override
        public InjectionStrategy getInjectionStrategy() {
            return annotation.injectionStrategy();
        }
    }
}
