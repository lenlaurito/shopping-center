package com.synacy.shoppingcenter

import grails.transaction.Transactional
import org.hibernate.criterion.CriteriaSpecification

@Transactional
class ShopService {
    TagService tagService

    public List<Shop> fetchAllShops(Integer offset, Integer max, Long tagId) {

        if(tagId) {
            def tag = tagService.fetchTagById(tagId)

            if(!tag) {
                throw new ResourceNotFoundException("This tag does not exist.")
            }else {
                return Shop.createCriteria().list(max: max, offset: offset) {
                    tags {
                        eq('id', tag.id)
                    }
                    order('id', 'asc')
                }
            }
        }else {
            return Shop.list([offset: offset, max: max, sort: "id", order: "asc"])
        }
    }

    public Integer fetchTotalNumberOfShops() {
        return Shop.count()
    }

    public Shop createShop(String name, String description, List<Long> tags, List<Location> locations) {
        Shop shop = new Shop(name: name, description: description)

        shop.tags = []
        shop.locations = []

        tags?.each {
            shop.addToTags(tagService.fetchTagById(it))
        }

        locations?.each {
            def location = Location.valueOfLocation(it)
            if (location)
                shop.addToLocations(location)
            else
                throw new InvalidRequestException("invalid location: " + it)
        }

        return shop.save()
    }

    public Shop fetchShopById(Long shopId) {
        Shop shop = Shop.findById(shopId)

        if (!shop) {
            throw new ResourceNotFoundException("Shop not found")
        }

        return shop
    }

    public Shop updateShop(Long shopId, String name, String description, List<Long> tags, List<Location> locations) {
        Shop shop = fetchShopById(shopId)

        shop.name = name
        shop.description = description

        shop.tags = []
        shop.locations = []

        tags?.each {
            shop.addToTags(tagService.fetchTagById(it))
        }

        locations?.each {
            def location = Location.valueOfLocation(it)
            if (location)
                shop.addToLocations(location)
            else
                throw new InvalidRequestException("invalid location: " + it)
        }

        return shop.save()
    }

    public void deleteShopById(Long shopId)  {
        Shop shop = fetchShopById(shopId)

        shop.delete()
    }
}
