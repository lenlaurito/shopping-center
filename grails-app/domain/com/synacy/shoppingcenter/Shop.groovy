package com.synacy.shoppingcenter

class Shop {

	Long id
	String name
	String description
	Location location

	static constraints = {
		description nullable: true
	}

	static mapping = {
		id generator: 'sequence', params: [sequence: 'shop_sequence']
	}

	static hasMany = [
			tags: Tag
	]
}
