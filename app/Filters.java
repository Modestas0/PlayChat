import javax.inject.*;
import play.mvc.EssentialFilter;
import play.http.HttpFilters;

@Singleton
public class Filters implements HttpFilters {

    @Override
    public EssentialFilter[] filters() {
        return new EssentialFilter[] {};
    }

}
