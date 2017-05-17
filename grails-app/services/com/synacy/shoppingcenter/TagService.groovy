package com.synacy.shoppingcenter

import grails.transaction.Transactional

@Transactional
class TagService {

    public List<Tag> fetchAllTags() {
        return Tag.findAll()
    }

    public Tag createNewTag(String name, Integer version) {
        Tag tag = new Tag()

        tag.setName(name)
        tag.setVersion(version)

        return tag.save()
    }

    public Tag findTagById(Long tagId) {
        Tag tag = Tag.findById(tagId)

        if (!tag)
            throw new ResourceNotFoundException("Tag not found")

        return tag
    }

    public Tag updateTag(Long tagId, String name, Integer version) {
        Tag tag = findTagById(tagId)

        tag.setName(name)
        tag.setVersion(version)

        return tag.save()
    }

    public void deleteTagById(Long tagId) {
        Tag tag = findTagById(tagId)

        tag.delete()
    }
}
