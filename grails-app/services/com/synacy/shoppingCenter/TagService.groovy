package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exceptions.EntityAlreadyExistsException
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
        Tag checkTagName = Tag.findByName(tagName)

        if(checkTagName != null){
            throw new EntityAlreadyExistsException("Tag Already Exists")
        }
        else{
            Tag tag = new Tag()
            tag.name = tagName
            tag.save()
            return tag
        }
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
