package com.synacy.shoppingcenter.trait

import com.synacy.shoppingcenter.exception.NoContentException
import com.synacy.shoppingcenter.exception.InvalidFieldException
import com.synacy.shoppingcenter.exception.InvalidUriException
import com.synacy.shoppingcenter.exception.InvalidParameterException
import org.springframework.http.HttpStatus

trait ErrorHandler {

    def handleNoContentException(NoContentException e) {
        response.status = HttpStatus.NOT_FOUND.value()
        respond([error: e.getMessage()])
    }

    def handleInvalidFieldException(InvalidFieldException e) {
        response.status = HttpStatus.BAD_REQUEST.value()
        respond([error: e.getMessage()])
    }

    def handleInvalidUriException(InvalidUriException e) {
        response.status = HttpStatus.BAD_REQUEST.value()
        respond([error: e.getMessage()])
    }

    def handleInvalidParameterException(InvalidParameterException e) {
        response.status = HttpStatus.BAD_REQUEST.value()
        respond([error: e.getMessage()])
    }
}
