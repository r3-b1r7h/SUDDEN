//package biz.sudden;
//
//import org.apache.catalina.LifecycleEvent;
//import org.apache.catalina.LifecycleListener;
//
//public class TomcatListener implements LifecycleListener {
//
//	@Override
//	public void lifecycleEvent(LifecycleEvent event) {
//		if (event.getLifecycle().equals(org.apache.catalina.Lifecycle.BEFORE_STOP_EVENT)) {
//			try {
//				Thread.currentThread().sleep(20000);
//			} catch (Exception ex) {
//				System.out.println(ex);
//			}
//		}
//
//	}
//
//}
