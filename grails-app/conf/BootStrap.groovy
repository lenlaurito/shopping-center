import com.synacy.shoppingCenter.marshaller.ShopMarshaller
import com.synacy.shoppingCenter.marshaller.TagMarshaller

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

