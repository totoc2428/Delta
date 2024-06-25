package exception.system.util;

import exception.system.SystemException;

public class UtilSystemException extends SystemException {

    public UtilSystemException(String systemExceptionCode) {
        super(systemExceptionCode, 1);
    }

}
