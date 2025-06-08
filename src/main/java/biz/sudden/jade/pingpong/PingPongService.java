/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.sudden.jade.pingpong;

import biz.sudden.designCoordination.teamFormation.service.JadeService;

/**
 * 
 * @author gweich
 */
public interface PingPongService extends JadeService {

	public void playAnotherRound(String agentshortname, boolean play);

}
