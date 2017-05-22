package com.synacy.shoppingcenter

class Shop {

	Long id
	String name
	String description

	static constraints = {
		description nullable: true
		name maxSize: 255
		description maxSize: 255
		tags maxSize: 5
		locations maxSize: 1
	}

	static mapping = {
		id generator: 'sequence', params: [sequence: 'shop_sequence']
	}

	static hasMany = [
			tags: Tag,
			locations: Location
	]
}
