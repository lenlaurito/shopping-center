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

    // void "fetchTags should respond with all tags"() {
    //     given:
    //         Tag tag1 = new Tag(name: "Cosmetics")
    //         Tag tag2 = new Tag(name: "Clothing")
    //         tagService.fetchAllTags() >> [tag1, tag2]
    //
    //     when:
    //         controller.fetchTags()
    //
    //     then:
    //         response.status == HttpStatus.OK.value()
    //         response.json.size() == 2
    //         response.json.find {
    //             it.name == "Cosmetics"
    //         }
    //         response.json.find {
    //             it.name == "Clothing"
    //         }
    // }

    // void "fetchTag should respond with the tag with the given id"() {
    //     given:
    //         Long tagId = 1L
    //         Tag tag = new Tag(name: "Cosmetics")
    //         tagService.fetchTagById(tagId) >> tag
    //
    //     when:
    //         controller.fetchTag(tagId)
    //
    //     then:
    //         response.status == HttpStatus.OK.value()
    //         response.json.name == "Cosmetics"
    // }

    // void "createTag should with the newly created shop with the given details"() {
    //     given:
    //         String name = "Cosmetics"
    //
    //         request.json = [name: name]
    //
    //         Tag tag = new Tag(name: name)
    //
    //     when:
    //         controller.createTag()
    //
    //     then:
    //         1 * tagService.createTag(name) >> tag
    //
    //     then:
    //         response.status == HttpStatus.CREATED.value()
    //         response.json.name == name
    // }

    // void "updateTag should respond with updated tag by given id"() {
    //     given:
    //         Long tagId = 1L
    //         Tag tag = new Tag(name: "Cosmetics")
    //         tagService.fetchTagById(tagId) >> tag
    //
    //         String name = "Clothing"
    //
    //         Tag updatedTag = new Tag(name: name)
    //
    //         request.json = [name: name]
    //
    //
    //     when:
    //         controller.updateTag(tagId)
    //
    //     then:
    //         1 * tagService.updateTag(tag, name) >> updatedTag
    //
    //     then:
    //         response.status == HttpStatus.OK.value()
    //         response.json.name == name
    // }

    // void "deleteTag should respond with no content"() {
    //     given:
    //         Long tagId = 1L
    //
    //     when:
    //         controller.deleteTag(tagId)
    //
    //     then:
    //         response.status == HttpStatus.NO_CONTENT.value()
    // }
}
