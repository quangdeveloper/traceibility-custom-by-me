package vn.vnpt.tracebility_custom.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import vn.vnpt.tracebility_custom.exception.ActionFailException;
import vn.vnpt.tracebility_custom.exception.DataExistsException;
import vn.vnpt.tracebility_custom.exception.GeneralException;
import vn.vnpt.tracebility_custom.exception.NotFoundException;
import vn.vnpt.tracebility_custom.model.response.Response;
import vn.vnpt.tracebility_custom.util.Constant;


@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = {GeneralException.class})
    protected ResponseEntity<Response> generalException(GeneralException ex, WebRequest re){
        final  Response responseDTO = Response.builder()
                .map(ex.getValue())
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        log.error("[FlowerExceptionControlHandler.GeneralException: {}]", ex.getMessage());
        return  new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Response> notFoundException(NotFoundException ex, WebRequest re){
        final  Response responseDTO = Response.builder()
                .map(null)
                .code(Constant.RESPONSE.CODE.C404)
                .message(ex.getMessage())
                .build();
        log.error("[ExceptionControlHandler.NotFoundException: {}]", ex.getMessage());
        return  new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @ExceptionHandler(value = {DataExistsException.class})
    protected ResponseEntity<Response> dataExistsException(DataExistsException ex, WebRequest re){
        final  Response responseDTO = Response.builder()
                .map(null)
                .code(Constant.RESPONSE.CODE.C409)
                .message(ex.getMessage())
                .build();
        log.error("[ExceptionControlHandler.NotFoundException: {}]", ex.getMessage());
        return  new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @ExceptionHandler(value = {ActionFailException.class})
    protected ResponseEntity<Response> actionFailException(ActionFailException ex, WebRequest re){
        final  Response responseDTO = Response.builder()
                .map(null)
                .code(Constant.RESPONSE.CODE.ERROR)
                .message(ex.getMessage())
                .build();
        log.error("[ExceptionControlHandler.NotFoundException: {}]", ex.getMessage());
        return  new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
