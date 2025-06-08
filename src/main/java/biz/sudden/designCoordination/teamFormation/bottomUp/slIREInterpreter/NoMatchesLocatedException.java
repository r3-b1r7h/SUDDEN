package biz.sudden.designCoordination.teamFormation.bottomUp.slIREInterpreter;

@SuppressWarnings("serial")
public class NoMatchesLocatedException extends CompiledIREException {

	public NoMatchesLocatedException() {
		super(" No matches located for the given IRE");
	}

}
