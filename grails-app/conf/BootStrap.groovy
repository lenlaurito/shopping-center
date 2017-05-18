import com.synacy.shoppingcenter.marshaller.ShopMarshaller
import com.synacy.shoppingcenter.marshaller.TagMarshaller

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
