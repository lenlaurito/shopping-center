package com.synacy.shoppingcenter

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.synacy.shoppingcenter.exceptions.*

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

    void "fetchAllTags should return all Tags"() {
        given:
        Tag tag1 = new Tag(name: "Tag 1")
        Tag tag2 = new Tag(name: "Tag 2")
        tag1.save()
        tag2.save()

        when:
        List<Tag> result = service.fetchAllTags()

        then:
        result.size() == 2
        result.get(0) == tag1
        result.get(1) == tag2
    }

    void "fetchTagById should return the Tag with the specified id"() {
        given:
        Tag tag = new Tag(name: "Tag 1")
        tag.save()

        when:
        Tag resultTag = service.fetchTagById(tag.id)

        then:
        tag == resultTag
        tag.name == resultTag.name
    }

    void "createNewTag should create a new Tag with the given details"() {
        given:
        String name = "My Tag"

        when:
        Tag createdTag = service.createNewTag(name)

        then:
        createdTag.name == name
    }

    void "deleteTag should delete the specified Tag"() {
        given:
        Tag tag = new Tag(name: "Tag 1")
        tag.save()

        when:
        service.deleteTag(tag)

        then:
        Tag.find {
            it == tag
        } == null
    }

    void "updateTag should update the given Tag with the new details"() {
        given:
        Tag tag = new Tag(name: "Tag 1")
        tag.save()

        String newName = "New Tag"

        when:
        Tag updatedTag = service.updateTag(tag, newName)

        then:
        updatedTag.name == newName
    }

    void "checkTags should not throw any exception if all tags ids are existing"() {
        given:
        Tag tag1 = new Tag(name: "Tag 1")
        Tag tag2 = new Tag(name: "Tag 2")
        tag1.save()
        tag2.save()

        List tagIds = [tag1.id, tag2.id]

        when:
        List<Tag> resultList = service.checkTags(tagIds)

        then:
        resultList.contains(tag1)
        resultList.contains(tag2)
    }

    void "checkTags should throw ResourceNotFoundException if there is a non-existing tag id"() {
        given:
        Tag tag1 = new Tag(name: "Tag 1")
        Tag tag2 = new Tag(name: "Tag 2")

        List tagIds = [tag1.id, tag2.id]

        when:
        service.checkTags(tagIds)

        then:
        ResourceNotFoundException exception = thrown()
    }

}
