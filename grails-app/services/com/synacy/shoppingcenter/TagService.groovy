package com.synacy.shoppingcenter

import grails.transaction.Transactional
import com.synacy.shoppingcenter.exceptions.*
import org.codehaus.groovy.grails.web.json.JSONObject

@Transactional
class TagService {

    public List<Tag> fetchAllTags() {
       return Tag.findAll()
    }

    public Tag createNewTag(String name) {
        Tag tag = new Tag()
        tag.name = name
        tag.shops = []
        return tag.save()
    }

    public Tag fetchTagById(Long tagId) {
        return Tag.findById(tagId)
    }

    public Tag updateTag(Tag tag, String name) {
        tag.name = name
        return tag.save()
    }

    public void deleteTag(Tag tag) {
        tag.delete()
    }

    public List<Tag> checkTags(List tagIds) {
        List<Tag> existingTags = []

        for(int i = 0; i < tagIds.size(); i++) {

            if(tagIds.get(i).equals(JSONObject.NULL))
                throw new NullPointerException("Tag Id should not be null")

             Tag tag = fetchTagById(tagIds.get(i))

             if(tag != null)
                 existingTags.add(tag)
             else
                 throw new ResourceNotFoundException("Tag with an id of " + tagIds.get(i) + " doesn't exist.")
         }

        return existingTags
    }

    public Tag fetchTagByName(String name) {
        return Tag.findByName(name)
    }


}