import com.synacy.shoppingcenter.ShopMarshaller
import com.synacy.shoppingcenter.TagMarshaller

class BootStrap {

    def init = { servletContext ->
        initializeMarshallers()
    }
    def destroy = {
    }

    private void initializeMarshallers() {
        new ShopMarshaller().register()
        new TagMarshaller().register()
    }
}
