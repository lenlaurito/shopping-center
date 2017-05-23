package com.synacy.shoppingCenter

class Shop {

	Long id
	String name
	String description
	Integer location

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
