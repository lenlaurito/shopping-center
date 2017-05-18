package com.synacy.shoppingcenter

class Shop {

	Long id
	String name
	String description

	static constraints = {
		description nullable: true
	}

	static mapping = {
		id generator: 'sequence', params: [sequence: 'shop_sequence']
	}

	static hasMany = [
			tags: Tag,
			locations: Location
	]
}
