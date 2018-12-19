package dk.aau.cs.ds303e18.p3warehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such product")
public class ProductNotFoundException extends RuntimeException {}
