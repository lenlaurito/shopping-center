package com.synacy.shoppingcenter.tag

import com.synacy.shoppingcenter.InvalidRequestException
import com.synacy.shoppingcenter.ResourceNotFoundException

import org.springframework.http.HttpStatus

class TagController {

	static responseFormats = ['json']

	TagService tagService

	def fetchTag(Long tagId) {
		Tag tag = tagService.fetchTagById(tagId)
		respond(tag)
	}
	
	def fetchAllTags() {
		List<Tag> tags = tagService.fetchAllTags()
		Integer tagCount = tagService.fetchTotalNumberOfTags()
		Map<String, Object> tagDetails = [totalTags: tagCount, tags: tags]
		respond(tagDetails)
	}

	def createTag() {
		String name = request.JSON.name ?: null

		if (!name) {
			throw new InvalidRequestException("Name should not be empty.")
		}
		
		if (tagService.hasDuplicates(name)) {
			throw new InvalidRequestException("Existing tag with name '" + name + "' already existed.")
		}
		Tag tag = tagService.createNewTag(name)
		
		respond(tag, [status: HttpStatus.CREATED])
	}

	def updateTag(Long tagId) {
		String name = request.JSON.name ?: null

		if (!name) {
			throw new InvalidRequestException("Invalid request.")
		}
		Tag tag = tagService.fetchTagById(tagId)
		if (!tag) {
			throw new ResourceNotFoundException("Tag with id " + tagId + " not found")
		}
		if (tagService.hasDuplicates(name)) {
			throw new InvalidRequestException("Existing tag with name '" + name + "' already existed.")
		}
		tag = tagService.updateTag(tag, name)
		respond(tag)
	}

	def removeTag(Long tagId) {
		Tag tag = tagService.fetchTagById(tagId)
		tagService.deleteTag(tag)

		render(status: HttpStatus.NO_CONTENT)
	}
	
	def handleResourceNotFoundException(ResourceNotFoundException e) {
		response.status = HttpStatus.NOT_FOUND.value()
		respond([error: e.getMessage()])
	}

	def handleInvalidRequestException(InvalidRequestException e) {
		response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
		respond([error: e.getMessage()])
	}
	
}
