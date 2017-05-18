package com.synacy.shoppingcenter.tag

import com.synacy.shoppingcenter.shop.Shop

class Tag {

	Long id
	String name

	static constraints = {
		name(unique: true)
	}

	static mapping = {
		id generator: 'sequence', params: [sequence: 'tag_sequence']
	}

	static belongsTo = Shop

	static hasMany = [
		shops: Shop
	]

}
