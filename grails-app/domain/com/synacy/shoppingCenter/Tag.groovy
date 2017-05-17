package com.synacy.shoppingCenter

class Tag {

	Long id
	String name

	static constraints = {
	}

	static mapping = {
		id generator: 'sequence', params: [sequence: 'tag_sequence']
	}

	static belongsTo = Shop

	static hasMany = [
			shops: Shop
	]
}
