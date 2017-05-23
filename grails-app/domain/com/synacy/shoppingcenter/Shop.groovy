package com.synacy.shoppingcenter

class Shop {

	Long id
	String name
	String description
	Location location

	static constraints = {
		description nullable: true
		name maxSize: 255
		description maxSize: 255
		tags maxSize: 5
	}

	static mapping = {
		id generator: 'sequence', params: [sequence: 'shop_sequence']
	}

	static hasMany = [
			tags: Tag
	]
}
