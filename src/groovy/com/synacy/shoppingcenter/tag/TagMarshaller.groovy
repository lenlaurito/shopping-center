package com.synacy.shoppingcenter.tag

import grails.converters.JSON

class TagMarshaller {

	void register() {
		JSON.registerObjectMarshaller(Tag) { Tag tag ->

			return [
					id      : tag.id,
					name    : tag.name,
					//shops    : tag.shops.id
			]
		}
	}

}
