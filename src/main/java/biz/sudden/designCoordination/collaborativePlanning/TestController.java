package biz.sudden.designCoordination.collaborativePlanning;

import com.icesoft.faces.async.render.RenderManager;
import com.icesoft.faces.async.render.Renderable;
import com.icesoft.faces.webapp.xmlhttp.PersistentFacesState;
import com.icesoft.faces.webapp.xmlhttp.RenderingException;

public class TestController implements Renderable {

	private PersistentFacesState state;
	private RenderManager manager;
	private int test = 0;

	public int getTest() {
		System.out.println("request render vom Testcontroller");
		test = test + 1;
		return test;
	}

	public void setTest(int test) {
		this.test = test;
	}

	public RenderManager getManager() {
		return manager;
	}

	public void setManager(final RenderManager manager) {
		this.manager = manager;
		this.state = PersistentFacesState.getInstance();
		this.manager = manager;

		final TestController myself;

		myself = this;

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						System.out
								.println("Request render in my own Thread !!!");
						Thread.sleep(3000);
						manager.requestRender(myself);
					}
				} catch (InterruptedException ex) {

				}

			}
		};

		new Thread(runnable).start();
		//
		// IntervalRenderer renderer =
		// manager.getIntervalRenderer("testrenderer");
		//
		// renderer.setInterval(1000);
		// // logger.debug("Broadcasted "+renderer.isBroadcasted());
		// renderer.setBroadcasted(false);
		// renderer.add(this);
		//
		// renderer.requestRender();
		// test = 0;
	}

	public void setState(PersistentFacesState state) {
		this.state = state;
	}

	public TestController() {

		// renderer.
		// userManager.setRenderer(renderer);
	}

	public PersistentFacesState getState() {
		// TODO Auto-generated method stub
		return state;
	}

	public void renderingException(RenderingException arg0) {
		// TODO Auto-generated method stub

	}

	public int test() {
		System.out.println("request render vom Testcontroller");
		return 0;
	}

}
