package biz.sudden.userInterface.gui.web.controller;

import biz.sudden.baseAndUtility.domain.CaseFile;

public interface TeamFormationController {

	public CaseFile getSelectedCaseFile();

	public String selectCaseFile();

	public String inviteTeam();

	public void updateCaseFile(CaseFile cf);

}
