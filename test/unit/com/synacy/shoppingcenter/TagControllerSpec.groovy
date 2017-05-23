package com.synacy.shoppingcenter

import grails.test.mixin.TestFor
import spock.lang.Specification
import org.springframework.http.HttpStatus

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TagController)
class TagControllerSpec extends Specification {

    TagService tagService = Mock();

    def setup() {
        controller.tagService = tagService
    }

    def cleanup() {
    }

    void "fetchAllTags should respond with all tags"() {
        given:

        Tag tag1 = new Tag(name: "tag 1")
        Tag tag2 = new Tag(name: "tag 2")

        when:
        controller.fetchAllTags()

        then:
        1 * tagService.fetchAllTags() >> [tag1, tag2]

        then:
        response.status == HttpStatus.OK.value()
        response.json.find {
            it.name == "tag 1"
        } != null
        response.json.find {
            it.name == "tag 2"
        } != null
    }

    void "createTag should respond with new created tag"() {
        given:
        String tagName = "sample"

        request.json = [name: tagName]

        Tag tag1 = new Tag(name: tagName)

        when:
        controller.createTag()

        then:
        1 * tagService.createNewTag(tagName) >> tag1

        then:
        response.status == HttpStatus.CREATED.value()
        response.json.name == tagName
    }

    void "viewTag should respond with the target tag to be fetched"() {
        given:
        Long idToFind = 1L

        Tag tag = new Tag(id: idToFind, name: "sample")

        when:
        controller.viewTag(idToFind)

        then:
        1 * tagService.fetchTagById(idToFind) >> tag

        then:
        response.status == HttpStatus.OK.value()
        response.json.name == "sample"
    }

    void "updateTag should respond with updated tag"() {
        given:
        Long idToFind = 1L
        String nameToUpdate = "sample"

        request.json = [name: nameToUpdate]

        Tag tag = new Tag(name: nameToUpdate)

        when:
        controller.updateTag(idToFind)

        then:
        1 * tagService.updateTag(idToFind, nameToUpdate) >> tag

        then:
        response.status == HttpStatus.OK.value()
        response.json.name == "sample"
    }

    void "deleteTag should delete tag by id"() {
        given:
        Long idToFind = 1L

        when:
        controller.deleteTag(idToFind)

        then:
        1 * tagService.deleteTagById(idToFind)

        then:
        response.status == HttpStatus.NO_CONTENT.value()
    }
}
