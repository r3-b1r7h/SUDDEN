package biz.sudden.designCoordination.collaborativePlanning;

public class TestController2 {

	// private PersistentFacesState state;
	// private RenderManager manager;
	private int test = 0;

	public int getTest() {
		System.out.println("request render vom Testcontroller ZWEI ");
		test = test + 1;
		return test;
	}

	public void setTest(int test) {
		this.test = test;
	}

	// public RenderManager getManager() {
	// return manager;
	// }
	//
	// public void setManager(RenderManager manager) {
	// this.manager = manager;
	// // this.state = PersistentFacesState.getInstance();
	// // this.manager = manager;
	// // IntervalRenderer renderer =
	// manager.getIntervalRenderer("testrenderer");
	// //
	// // renderer.setInterval(1000);
	// // //logger.debug("Broadcasted "+renderer.isBroadcasted());
	// // renderer.setBroadcasted(false);
	// // renderer.add(this);
	// //
	// // renderer.requestRender();
	// test = 0;
	// }
	//
	// public void setState(PersistentFacesState state) {
	// this.state = state;
	// }

	public TestController2() {
		System.out.println("Constructor for TestController2");
		// renderer.
		// userManager.setRenderer(renderer);
	}

	//	
	//	
	//
	// public PersistentFacesState getState() {
	// // TODO Auto-generated method stub
	// return state;
	// }
	//	
	//	
	// public void renderingException(RenderingException arg0) {
	// // TODO Auto-generated method stub
	//		
	// }

	public int test() {
		System.out.println("request render vom Testcontroller");
		return 0;
	}

}
