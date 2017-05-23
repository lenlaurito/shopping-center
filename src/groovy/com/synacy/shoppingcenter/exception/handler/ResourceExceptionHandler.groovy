package com.synacy.shoppingcenter.exception.handler

import com.synacy.shoppingcenter.exception.*

import org.postgresql.util.PSQLException
import org.springframework.http.HttpStatus

import java.sql.BatchUpdateException
import java.sql.SQLException

trait ResourceExceptionHandler {
	
	def handleResourceNotFoundException(ResourceNotFoundException e) {
		response.status = HttpStatus.NOT_FOUND.value()
		respond([error: e.getMessage()])
	}

	def handleInvalidRequestException(InvalidRequestException e) {
		response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
		respond([error: e.getMessage()])
	}
	
}
