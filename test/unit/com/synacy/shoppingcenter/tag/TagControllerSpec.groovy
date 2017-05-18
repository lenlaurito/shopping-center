package com.synacy.shoppingcenter.tag

import grails.test.mixin.TestFor
import org.springframework.http.HttpStatus
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TagController)
class TagControllerSpec extends Specification {

	TagService tagService = Mock()

	def setup() {
		controller.tagService = tagService
	}
	
	void "fetchTag should respond with the tag which has the specified id"() {
		given:
		Long tagId = 2L
		Tag tag = new Tag(name: "Restaurant")
		tagService.fetchTagById(tagId) >> tag

		when:
		controller.fetchTag(tagId)

		then:
		response.status == HttpStatus.OK.value()
		response.json.name == "Restaurant"
	}

	void "fetchAllTags should respond with the correct tags"() {
		given:
		Tag tag1 = new Tag(id: 1L, name: "Specialty")
		Tag tag2 = new Tag(id: 2L, name: "Restaurant")
		tagService.fetchAllTags() >> [tag1, tag2]
		tagService.fetchTotalNumberOfTags() >> 2

		when:
		controller.fetchAllTags()

		then:
		response.status == HttpStatus.OK.value()
		response.json.size() == 2
		response.json.tags.find {
			it.name == "Specialty"
		} != null
		response.json.tags.find {
			it.name == "Restaurant"
		} != null
	}

	void "createTag should respond with the newly created tag with the details specified"() {
		given:
		String name = "Restaurant"

		request.json = [name: name]

		Tag tag = new Tag(name: name)
		
//		Tag.countByName(name) >> 1

		when:
		controller.createTag()

		then:
		1 * tagService.createNewTag(name) >> tag

		then:
		response.status == HttpStatus.CREATED.value()
		response.json.name == name
	}
	
	
	
	void "updateTag should respond with the updated tag for the given details"() {
		given:
		Long tagId = 2L
		String name = "Jollibee"

		request.json = [name: name]

		Tag tagToUpdate = new Tag(name: name)
		tagService.fetchTagById(tagId) >> tagToUpdate

		when:
		controller.updateTag(tagId)

		then:
		1 * tagService.updateTag(tagToUpdate, name) >> tagToUpdate

		then:
		response.status == HttpStatus.OK.value()
		response.json.name == name
	}
	
	void "deleteTag should delete tag"() {
		given:
		Long tagId = 2L
		Tag tagToDelete = new Tag(name: "Barbershop")
		tagService.fetchById(tagId) >> tagToDelete

		when:
		controller.removeTag(tagId)
		
		then:
		response.status == HttpStatus.NO_CONTENT.value()
	}
	
}
