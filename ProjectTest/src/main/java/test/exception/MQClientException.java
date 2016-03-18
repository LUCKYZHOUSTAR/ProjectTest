package test.exception;

public class MQClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1653562490895064549L;

	private final int responseCode;
	private final String errorMessage;

	public MQClientException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.responseCode = -1;
		this.errorMessage = errorMessage;

	}

	public MQClientException(int responseCode, String errorMessage) {
		super(Integer.toString(responseCode) + errorMessage);
		this.responseCode = responseCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the responseCode
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}
