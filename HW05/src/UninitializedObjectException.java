
public class UninitializedObjectException extends Exception {

	UninitializedObjectException() {
		super();
	}
	
	UninitializedObjectException(String message) {
		super(message);
	}
	
	UninitializedObjectException(String message, Throwable cause) {
		super(message, cause);
	}
	
	UninitializedObjectException(Throwable cause) {
		super(cause);
	}
}
