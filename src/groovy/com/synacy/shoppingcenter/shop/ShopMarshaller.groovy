package com.synacy.shoppingcenter.shop

import grails.converters.JSON

class ShopMarshaller {

	void register() {
		JSON.registerObjectMarshaller(Shop) { Shop shop ->

			return [
					id       	: shop.id,
					name     	: shop.name,
					description : shop.description,
					tags		: shop.tags
					
			]
		}
	}

}
