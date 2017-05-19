package com.synacy.shoppingcenter.shop

import com.synacy.shoppingcenter.exception.InvalidRequestException;
import com.synacy.shoppingcenter.exception.ResourceNotFoundException;
import com.synacy.shoppingcenter.shop.Shop
import com.synacy.shoppingcenter.tag.Tag
import com.synacy.shoppingcenter.tag.TagService

import grails.transaction.Transactional

@Transactional
class ShopService {
	
	TagService tagService
	
	public Shop fetchShopById(Long id) {
		Shop shop = Shop.findById(id)
		if (!shop) {
			throw new ResourceNotFoundException("Shop not found")
		}
		return shop
	}

	public List<Shop> fetchShops(Long tagId, Integer offset, Integer max) {
		if(tagId) {
			def tag = tagService.fetchTagById(tagId)
            return Shop.createCriteria().list(offset: offset, max: max) {
				tags {
					eq('id', tag.id)
				}
				order('id', 'asc')
			}
        } else {
            return Shop.list([offset: offset, max: max, sort: "id", order: "asc"])
        }
	}

	public Integer fetchTotalNumberOfShops() {
		return Shop.count()
	}

	public Shop createNewShop(String name, String description, Location location, def tags) {
		Shop shop = new Shop(name: name, description: description, location: location)
		tags.each {
			Tag tag = tagService.fetchTagById(it)
			shop.addToTags(tag)
			tag.addToShops(shop)
		}
		return shop.save()
	}

	public updateShop(Shop shop, String name, String description, Location location, def tags) {
		shop.name = name
		shop.description = description
		shop.location = location
		if (tags) {
			if (tags.size() > 5) {
				throw new InvalidRequestException("Could not tag a shop more than 5 times.")
			}
			
			tags.each {
				Tag tag = tagService.fetchTagById(it)
				shop.addToTags(tag)
				tag.addToShops(shop)
			}
		}
		return shop.save()
	}

	public deleteShop(Shop shop) {
		shop.delete()
	}
	
}
