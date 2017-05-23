package com.synacy.shoppingcenter

class Tag {

	Long id
	String name

	static constraints = {
		name maxSize: 255
	}

	static mapping = {
		id generator: 'sequence', params: [sequence: 'tag_sequence']
	}

	static belongsTo = Shop

	static hasMany = [
			shops: Shop
	]
}
