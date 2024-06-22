package com.userlogin.Characters;


@SuppressWarnings("serial")
public class ExcelDataLimitExceededException extends RuntimeException 
{
    public ExcelDataLimitExceededException(String message) {
        super(message);
    }
}
