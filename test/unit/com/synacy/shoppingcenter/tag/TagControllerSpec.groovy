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
	
	void "fetchTag should respond not found status when id not found"() {
		given:
		Long shopId = 2L
		tagService.fetchTagById(shopId) >> null

		when:
		controller.fetchTag(shopId)

		then:
		response.status == HttpStatus.NOT_FOUND.value()
	}

	void "fetchAllTags should respond with the correct tags"() {
		given:
		Tag tag1 = new Tag(id: 1L, name: "Specialty")
		Tag tag2 = new Tag(id: 2L, name: "Restaurant")

		when:
		controller.fetchAllTags()

		then:
		1 * tagService.fetchAllTags() >> [tag1, tag2]
		1 * tagService.fetchTotalNumberOfTags() >> 2
		
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

	void "createTag when missing required request param name should respond HTTP Unprocessable Entity status"() {
		given:
		String name = null
		request.json = []
		
		when:
		controller.createTag()

		then:
		response.status == HttpStatus.UNPROCESSABLE_ENTITY.value()
	}
	
	void "createTag when existing tag with name already exist should respond with HTTP Conflict status"() {
		given:
		String name = "Restaurant"
		request.json = [name: name]
		
		when:
		controller.createTag()

		then:
		1 * tagService.countByName(name) >> 2

		then:
		response.status == HttpStatus.CONFLICT.value()
	}
	
	void "createTag should respond with the newly created tag with the details specified"() {
		given:
		String name = "Restaurant"
		request.json = [name: name]
		Tag tag = new Tag(name: name)
		
		when:
		controller.createTag()

		then:
		1 * tagService.countByName(name) >> 0
		1 * tagService.createNewTag(name) >> tag

		then:
		response.status == HttpStatus.CREATED.value()
		response.json.name == name
	}
	
	void "updateTag when missing required request param name should respond HTTP Unprocessable Entity status"() {
		given:
		Long tagId = 100L
		String name = null
		request.json = []
		
		when:
		controller.updateTag(tagId)

		then:
		response.status == HttpStatus.UNPROCESSABLE_ENTITY.value()
	}
	
	void "updateTag when tag id is not found should respond HTTP Not Found status"() {
		given:
		Long tagId = 100L
		String name = "Restaurant"
		request.json = [name: name]
		
		when:
		controller.updateTag(tagId)

		then:
		1 * tagService.fetchTagById(tagId) >> null
		
		then:
		response.status == HttpStatus.NOT_FOUND.value()
	}
	
	void "updateTag when existing tag with name already exist should respond with HTTP Conflict status"() {
		given:
		Long tagId = 100L
		String name = "Restaurant"
		request.json = [name: name]
		Tag tag = new Tag(name: name)
		
		when:
		controller.updateTag(tagId)

		then:
		1 * tagService.fetchTagById(tagId) >> tag
		1 * tagService.countByName(name) >> 2

		then:
		response.status == HttpStatus.CONFLICT.value()
	}
	
	void "updateTag should respond with the updated tag for the given details and with HTTP OK status"() {
		given:
		Long tagId = 100L
		String name = "Jollibee"
		request.json = [name: name]
		Tag tagToUpdate = new Tag(name: name)
		
		when:
		controller.updateTag(tagId)

		then:
		1 * tagService.fetchTagById(tagId) >> tagToUpdate
		1 * tagService.countByName(name) >> 0
		1 * tagService.updateTag(tagToUpdate, name) >> tagToUpdate

		then:
		response.status == HttpStatus.OK.value()
		response.json.name == name
	}
	
	void "deleteTag should respond with HTTP No Content status"() {
		given:
		Long tagId = 2L
		Tag tagToDelete = new Tag(name: "Barbershop")

		when:
		controller.removeTag(tagId)
		
		then:
		1 * tagService.fetchTagById(tagId) >> tagToDelete
		
		then:
		response.status == HttpStatus.NO_CONTENT.value()
	}
	
}
