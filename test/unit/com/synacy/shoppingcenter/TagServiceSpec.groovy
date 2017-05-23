package com.synacy.shoppingcenter

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TagService)
@Mock([Tag])
class TagServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "fetchAllTags should find all Tags"() {
        given:
        Tag tag1 = new Tag(name: "sample 1")
        Tag tag2 = new Tag(name: "sample 2")
        Tag tag3 = new Tag(name: "sample 3")

        tag1.save()
        tag2.save()
        tag3.save()

        when:
        List<Tag> tag = service.fetchAllTags()

        then:
        tag.size() == 3
        tag.get(0).equals(tag1)
        tag.get(1).equals(tag2)
        tag.get(2).equals(tag3)
    }

    void "createNewTag should save new Tag and return"() {
        when:
        Tag savedTag = service.createNewTag("sample")

        then:
        savedTag.name == "sample"
    }

    void "fetchTagById should fetch target tag and return"() {
        given:

        Tag tag = new Tag(name: "sample")
        tag.save()

        Long tagId = tag.id

        when:
        Tag fetchedTag = service.fetchTagById(tagId)

        then:
        fetchedTag.id == tagId
        fetchedTag.name == "sample"
    }

    void "updateTag should update target tag and return"() {
        given:
        Tag tag = new Tag(name: "sample")
        tag.save()

        Long tagId = tag.id

        when:
        Tag updatedTag = service.updateTag(tagId, "test")

        then:
        updatedTag.id == tagId
        updatedTag.name == "test"
    }

    void "deleteTagById should be able to delete tag"() {
        given:
        Tag tag = new Tag(name: "sample")
        tag.save()

        Long tagId = tag.id

        when:
        service.deleteTagById(tagId)

        then:
        Tag.findById(tagId) == null
    }
}
