package com.synacy.shoppingCenter

import grails.transaction.Transactional

@Transactional
class TagService {

    def serviceMethod() {

    }

    Tag fetchTagById(Long tagId){
        return Tag.findById(tagId)
    }

    List<Tag> fetchAllTag(){
        return Tag.findAll()
    }

    Tag createTag(String tagName){
        Tag tag = new Tag()
        tag.name = tagName
        return tag.save()
    }

    Tag updateTag(Long tagId, String tagName){
        Tag tagToBeUpdated = fetchTagById(tagId)
        tagToBeUpdated.name = tagName
        return tagToBeUpdated.save()
    }

    void deleteTag(Long tagId){
        Tag tagToBeDeleted = fetchTagById(tagId)
        tagToBeDeleted.delete()
    }

}
