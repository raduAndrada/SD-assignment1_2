package ro.utcn.sd.assign.one.business.exceptions;

public class BusinessException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BusinessException(final String message) {
		super(message);

	}

}
