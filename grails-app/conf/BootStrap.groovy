import com.synacy.shoppingcenter.ShopMarshaller
import com.synacy.shoppingcenter.TagMarshaller
import com.synacy.shoppingcenter.ValidationErrorMarshaller

class BootStrap {

    def init = { servletContext ->
        initializeMarshallers()
    }
    def destroy = {
    }

    private void initializeMarshallers() {
        new ShopMarshaller().register()
        new TagMarshaller().register()
        new ValidationErrorMarshaller().register()
    }
}
