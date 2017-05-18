package com.synacy.shoppingCenter

import com.synacy.shoppingCenter.exception.EntityAlreadyExistsException
import com.synacy.shoppingCenter.exception.NoContentFoundException
import grails.transaction.Transactional

@Transactional
class TagService{

    Tag fetchTagById(Long tagId){
        Tag tag = Tag.findById(tagId)
        if(!tag){ throw new NoContentFoundException("No Tag with the Id found")}
        return tag
    }

    List<Tag> fetchAllTag(){
        List<Tag> tagList = Tag.findAll()
        if(!tagList){ throw new NoContentFoundException("No Tags found")}
        return tagList
    }

    Tag createTag(String tagName){
        Tag checkTagName = Tag.findByName(tagName)
        if(checkTagName){throw new EntityAlreadyExistsException("Tag Already Exists")}

        Tag tag = new Tag()
        tag.name = tagName
        tag.save()
        return tag

    }

    Tag updateTag(Long tagId, String tagName){
        Tag checkTagName = Tag.findByName(tagName)
        if(checkTagName){throw new EntityAlreadyExistsException("Tag Already Exists")}

        Tag tagToBeUpdated = fetchTagById(tagId)
        tagToBeUpdated.name = tagName
        return tagToBeUpdated.save()
    }

    void deleteTag(Long tagId){
        Tag tagToBeDeleted = fetchTagById(tagId)
        tagToBeDeleted.delete()
    }

}
