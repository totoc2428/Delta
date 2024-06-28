package exception.system.model.manager;

import exception.system.SystemException;

public abstract class ManagerSystemException extends SystemException {

    public ManagerSystemException(String systemExceptionCode) {
        super(systemExceptionCode, 2);
    }

}
