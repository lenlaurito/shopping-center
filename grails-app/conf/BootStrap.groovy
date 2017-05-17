import com.synacy.shoppingCenter.ShopMarshaller
import com.synacy.shoppingCenter.TagMarshaller

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

