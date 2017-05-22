package com.synacy.shoppingcenter

import grails.transaction.Transactional
import com.synacy.shoppingcenter.trait.ErrorHandler
import com.synacy.shoppingcenter.exception.InvalidFieldException
import com.synacy.shoppingcenter.exception.NoContentException
import com.synacy.shoppingcenter.exception.InvalidParameterException
import grails.validation.ValidationException

@Transactional
class ShopService implements ErrorHandler{

    TagService tagService

    def fetchAllShops(Integer max, Integer offset, String tagName) {

        if(max || offset){
            if(max < 0 || offset < 0) {
                throw new InvalidParameterException("Invalid parameters entered.")
            }
        }

        if(!tagName) {
            return Shop.list([offset: offset, max: max, sort: "id", order: "asc"])
            }else {
                def tag = tagService.fetchTagByName(tagName)

                if(!tag) {
                    throw new NoContentException("This tag does not exist.")
                }else {
                    return Shop.createCriteria().list(max: max, offset: offset) {
                        tags {
                            eq('id', tag.id)
                        }
                        order('id', 'asc')
                    }
                }
            }
    }

    def fetchShopById(Long shopId) {

         return Shop.findById(shopId)
    }

    def createShop(String name, Location location, String description, List<Tag> tags) {

         def setOfTags = tags.toSet()
         List<Tag> listOfTags = new ArrayList<Tag>(setOfTags)

         Shop shop = new Shop(name: name, location: location, description: description, tags: listOfTags)

         return shop.save()
    }

    def updateShop(Shop shop, String name, Location location, String description, List<Tag> tags) {

         def setOfTags = tags.toSet()
         tags.addAll(setOfTags)

         shop.name = name
         shop.location = location
         shop.description = description
         shop.tags = tags

         return shop.save()
    }

    def deleteShop(Shop shop) {

        shop.delete()
    }

    def fetchTotalNumberOfShops() {

         return Shop.count()
    }

    def fetchAllShopsByTag(Tag tag) {

        return Shop.findAllByTag(tag)
    }
}
