package com.synacy.shoppingcenter

import grails.transaction.Transactional
import com.synacy.shoppingcenter.exception.NoContentException
import com.synacy.shoppingcenter.trait.ErrorHandler

@Transactional
class TagService implements ErrorHandler{

     def fetchAllTags() {

         return Tag.findAll()
    }

    def fetchTagById(Long tagId) {

         return Tag.findById(tagId)
    }

    def createTag(String name) {

         Tag tag = new Tag(name: name)

         return tag.save()
    }

    def updateTag(Tag tag, String name) {

         tag.name = name

         return tag.save()
    }

    def deleteTag(Tag tag) {

         tag.delete()
    }

    def fetchTagsFromIds(def tagIds) {

         List<Tag> tags = []
         def tag

         tagIds.each {
             tag = fetchTagById(it)
             if(!tag) {
                 throw new NoContentException("Tag or tags don't exist.")
             } else {
                 tags << tag
             }
         }

         return tags
    }
}
