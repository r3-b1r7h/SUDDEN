//package biz.sudden;
//
//import jade.core.LifeCycle;
//
//import javax.faces.event.PhaseEvent;
//import javax.faces.event.PhaseId;
//import javax.faces.event.PhaseListener;
//import javax.faces.lifecycle.Lifecycle;
//
//import com.sun.faces.lifecycle.LifecycleImpl;
//
//public class SuddenPhaseListener implements PhaseListener {
//
//	@Override
//	public void afterPhase(PhaseEvent arg0) {
//		// TODO Auto-generated method stub
//		Lifecycle cycle;
//		
//		System.out.println("After Phase "+(LifecycleImpl)arg0.getSource());
//		
//	}
//
//	@Override
//	public void beforePhase(PhaseEvent arg0) {
//		// TODO Auto-generated method stub
//		System.out.println("Before Phase "+arg0);
//		arg0.getPhaseId();
//	}
//
//	@Override
//	public PhaseId getPhaseId() {
//		// TODO Auto-generated method stub
//		return PhaseId.RENDER_RESPONSE;
//		//return null;
//	}
//
//	
//	
//}
