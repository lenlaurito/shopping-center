import com.synacy.shoppingcenter.shop.ShopMarshaller
import com.synacy.shoppingcenter.tag.TagMarshaller

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
