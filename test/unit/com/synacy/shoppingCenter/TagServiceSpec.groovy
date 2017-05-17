package com.synacy.shoppingCenter

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TagService)
class TagServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }

    void "fetchById should return the tag with the given id"(){
        given:
            Tag tag = new Tag(name: "Jewelry")
            tag.save()

        when:
            Tag fetchedTag = service.fetchById(tag.id)

        then:
            fetchedTag.id = tag.id
            fetchedTag.name == tag.name
    }

    void "fetchAll should return list of all the tags"(){
        given:
            Tag tag1 = new Tag(name: "Jewelry")
            tag1.save()
            Tag tag2 = new Tag(name: "Clothes")
            tag2.save()

        when:
            List<Tag> fetchedTags = service.fetchAll()

        then:
            fetchedTags[1].id == tag1.id
            fetchedTags[1].name == tag1.name
            fetchedTags[2].id == tag2.id
            fetchedTags[2].name == tag2.name
    }

    void "createTag should create and return new tag with the correct information"(){
        given:
            String tagName = "Jewelry"

        when:
            Tag createdTag = service.createTag(tagName)

        then:
            createdTag.name == tagName
            Tag.exists(createdTag.id)
    }

    void "updateTag should update the tag's information and save it"(){
        given:
            Long tagId = 1L;
            String tagName = "Jewelry"

        when:
            Tag updatedTag = service.updateTag(tagId, tagName)

        then:
            updatedTag.name = tagName
            Tag.exists(updatedTag.id)
    }

    void "deleteTag should delete the tag with the given id"(){
        given:
            Tag tag = new Tag(name: "Jewelry")
            service.fetchById(tag.id) >> tag

        when:
            service.deleteTag(tag.id)

        then:
            !Tag.exists(tag.id)
    }
}
