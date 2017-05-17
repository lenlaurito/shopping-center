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
            throw new ResourceNotFoundException("Tag not found")

        return tag
    }

    public Tag updateTag(Long tagId, String name) {
        Tag tag = findTagById(tagId)

        tag.setName(name)

        return tag.save()
    }

    public void deleteTagById(Long tagId) {
        Tag tag = findTagById(tagId)

        tag.delete()
    }
}
