package com.synacy.shoppingcenter

import grails.transaction.Transactional

@Transactional
class TagService {

    public List<Tag> fetchAllTags() {
        return Tag.findAll()
    }

    public Tag createNewTag(String name) {
        Tag tag = new Tag()

        tag.setName(name)

        return tag.save()
    }

    public Tag fetchTagById(Long tagId) {
        Tag tag = Tag.findById(tagId)

        if (!tag)
            throw new ResourceNotFoundException("Tag with id '" + tagId + " ' does not exists.")

        return tag
    }

    public Tag updateTag(Long tagId, String name) {
        Tag tag = fetchTagById(tagId)

        tag.setName(name)

        return tag.save()
    }

    public void deleteTagById(Long tagId) {
        Tag tag = fetchTagById(tagId)

        tag.delete()
    }
}
