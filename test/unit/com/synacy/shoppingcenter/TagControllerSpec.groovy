package com.synacy.shoppingcenter

import grails.test.mixin.TestFor
import spock.lang.Specification

import org.springframework.http.HttpStatus

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TagController)
class TagControllerSpec extends Specification {

    TagService tagService = Mock()

    def setup() {
        controller.tagService = tagService
    }

    def cleanup() {
    }

    void "fetchAllTags should respond all given Tags"() {
        given:
        Tag tag1 = new Tag(name: "Tag 1")
        Tag tag2 = new Tag(name: "Tag 2")
        tagService.fetchAllTags() >> [tag1, tag2]

        when:
        controller.fetchAllTags()

        then:
        response.status == HttpStatus.OK.value()
        response.json.size() == 2
        response.json.find {
            it.name == tag1.name
        } != null
        response.json.find {
            it.name == tag2.name
        } != null
    }

    void "createTag should respond the newly created Tag with the specified details"() {
        given:
        String name = "Tag 1"

        request.json = [name: name]

        Tag tag = new Tag(name: name)

        when:
        controller.createTag()

        then:
        1 * tagService.createNewTag(name) >> tag

        then:
        response.status == HttpStatus.CREATED.value()
        response.json.name == name
    }

    void "createTag should throw NullPointerException if name request is null"() {
        given:
        String name = null

        request.json = [name: name]

        when:
        controller.createTag()

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

    void "createTag should throw ExistingResourceException if name request already exist in the database"() {
        given:
        String name = "First Tag"

        request.json = [name: name]

        Tag tag = new Tag(name: name)

        tagService.fetchTagByName(name) >> tag

        when:
        controller.createTag()

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

    void "updateTag should update the Tag which has the specified id"() {
        given:
        Long tagId = 100L
        String name = "First tag"

        request.json = [name: name]

        Tag tag = new Tag(name: name)

        tagService.fetchTagById(tagId) >> tag

        Tag updatedTag = new Tag(name: "Updated Tag")

        when:
        controller.updateTag(tagId)

        then:
        1 * tagService.updateTag(tag, name) >> updatedTag

        then:
        response.status == HttpStatus.OK.value()
        response.json.name == updatedTag.name
    }

    void "updateTag should throw ResourceNotFoundException if no existing Tag is found with the given id"() {
        given:
        Long tagId = 100L
        tagService.fetchTagById(tagId) >> null

        when:
        controller.updateTag()

        then:
        response.status == HttpStatus.NOT_FOUND.value()
    }

    void "updateTag should throw NullPointerException if name request is null"() {
        given:
        Long tagId = 100L
        String name = null

        request.json = [name: name]

        tagService.fetchTagById(tagId) >> new Tag()

        when:
        controller.updateTag(tagId)

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

    void "updateTag should throw ExistingResourceException if name request already exist in the database"() {
        given:
        Long tagId = 100L
        String name = "First Tag"

        request.json = [name: name]

        Tag tag = new Tag(name: name)

        tagService.fetchTagById(tagId) >> tag

        tagService.fetchTagByName(name) >> tag

        when:
        controller.updateTag(tagId)

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

    void "removeTag should remove the Tag with the specified id"() {
        given:
        Long tagId = 200L

        Set shops = []
        Tag tag = new Tag(name: "Tag 1", shops: shops)

        tagService.fetchTagById(tagId) >> tag

        when:
        controller.removeTag(tagId)

        then:
        1 * tagService.deleteTag(tag)

        then:
        response.status == HttpStatus.NO_CONTENT.value()
    }

    void "removeTag should throw ResourceNotFoundException if no tag exist with the specified id"() {
        given:
        Long tagId = 200L

        tagService.fetchTagById(tagId) >> null

        when:
        controller.removeTag(tagId)

        then:
        response.status == HttpStatus.NOT_FOUND.value()
    }

    void "removeTag should throw ExistingResourceException if tag is still used by a shop"() {
        given:
        Long tagId = 100L
        Shop shop = new Shop(name: "Shop", description: "description", location: Location.FIRST_FLOOR, tags: [])

        Set shops = [shop]
        Tag tag = new Tag(name: "Tag 1", shops: shops)

        tagService.fetchTagById(tagId) >> tag

        when:
        controller.removeTag(tagId)

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
    }

}
