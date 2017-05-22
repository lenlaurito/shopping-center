package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exception.DataConflictException
import com.synacy.shoppingCenter.exception.EntityAlreadyExistsException
import grails.transaction.Transactional

@Transactional
class TagService {

    Tag fetchTagById(Long tagId){
        return Tag.findById(tagId)
    }

    List<Tag> fetchAllTag(){
        List<Tag> tagList = Tag.findAll()
        return tagList
    }

    Tag createTag(String tagName){
        Tag checkTagName = Tag.findByName(tagName)
        if(checkTagName){throw new EntityAlreadyExistsException("Tag Name Already Exists")}

        Tag tag = new Tag()
        tag.name = tagName
        tag.shops = []
        tag.save()
        return tag
    }

    Tag updateTag(Long tagId, String tagName){
        Tag checkTagName = Tag.findByName(tagName)
        if(checkTagName){throw new EntityAlreadyExistsException("Tag Name Already Exists")}

        Tag tagToBeUpdated = fetchTagById(tagId)
        tagToBeUpdated.name = tagName
        return tagToBeUpdated.save()
    }

    void deleteTag(Long tagId){
        Tag tagToBeDeleted = fetchTagById(tagId)
        if(tagToBeDeleted){
            if(tagToBeDeleted.shops){throw new DataConflictException("Tag still in use")}
            tagToBeDeleted.delete()
        }
    }

}
