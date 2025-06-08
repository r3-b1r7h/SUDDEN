package biz.sudden.baseAndUtility.domain.exception;

public class AmbiguousActorException extends RuntimeException {

	public AmbiguousActorException() {
		super();
	}

	public AmbiguousActorException(String message) {
		super(message);
	}

}
