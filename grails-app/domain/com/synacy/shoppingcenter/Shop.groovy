package com.synacy.shoppingcenter

class Shop {

	Long id
	String name
	Location location
	String description


	static constraints = {
		description nullable: true
		tags size: 0..5, nullable: true
	}

	static mapping = {
		id generator: 'sequence', params: [sequence: 'shop_sequence']
	}

	static hasMany = [
			tags: Tag
	]
}
