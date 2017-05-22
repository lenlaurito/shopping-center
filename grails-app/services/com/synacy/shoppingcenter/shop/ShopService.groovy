package com.synacy.shoppingcenter.shop

import com.synacy.shoppingcenter.exception.*
import com.synacy.shoppingcenter.tag.Tag
import com.synacy.shoppingcenter.tag.TagService

import grails.transaction.Transactional

@Transactional
class ShopService {

	TagService tagService

	public Shop fetchShopById(Long id) {
		Shop shop = Shop.findById(id)
		if (!shop) {
			throw new ResourceNotFoundException("Shop with id " + id + " does not exist.")
		}
		return shop
	}

	public List<Shop> fetchShops(Tag tag, Integer offset, Integer max) {
		if (!tag) {
			return Shop.list([offset: offset, max: max, sort: "id", order: "asc"])
		}
		else {
			return Shop.withCriteria(offset: offset, max: max, sort: "id", order: "asc") {
				tags {
					eq('id', tag.id)
				}
			}
		}
	}

	public Shop createNewShop(String name, String description, Location location, List<Tag> tags) {
		Shop shop = new Shop(name: name, description: description, location: location, tags: tags)
		return shop.save()
	}

	public updateShop(Shop shop, String name, String description, Location location, List<Tag> tags) {
		shop.name = name
		shop.description = description
		shop.location = location
		shop.tags = tags
		return shop.save()
	}

	public deleteShop(Shop shop) {
		shop.delete()
	}
	
	public Integer fetchTotalNumberOfShops() {
		return Shop.count()
	}

}
