package biz.sudden.baseAndUtility.web.controller.impl;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import biz.sudden.baseAndUtility.domain.PrimitiveType;
import biz.sudden.baseAndUtility.service.PrimitiveValueService;
import biz.sudden.baseAndUtility.web.controller.RootLinkController;
import biz.sudden.baseAndUtility.web.controller.domain.JsfLink;

import com.icesoft.faces.component.ext.HtmlCommandButton;
import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.panelpopup.PanelPopup;

public class PrimitiveValueController {

	private PrimitiveValueService primitiveValueService;

	public PrimitiveValueService getPrimitiveValueService() {
		return primitiveValueService;
	}

	public void setPrimitiveValueService(
			PrimitiveValueService primitiveValueService) {
		this.primitiveValueService = primitiveValueService;
	}

	public PanelPopup getLinkableTypeSelectionPopup(final JsfLink jsfLink,
			RootLinkController rootLinkController) {

		System.out
				.println("called getLinkableTypeSelectionPopup in PrimitiveValue...");

		final PanelPopup myPopup = new PanelPopup();

		myPopup.setDraggable("true");
		myPopup.setStyle("width:400px;height:300px;position:absolute;top:"
				+ (150) + "px;left:" + (500) + "px;");
		myPopup.setClientOnly(true);
		myPopup.setId("myPopup");

		// Close Button in Header
		HtmlCommandButton closeButton = (HtmlCommandButton) FacesContext
				.getCurrentInstance().getApplication().createComponent(
						HtmlCommandButton.COMPONENT_TYPE);
		closeButton.setType("submit");
		closeButton.setValue("Close");
		closeButton.setId("closeButton");

		closeButton.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {
				// TODO Auto-generated method stub
				myPopup.setVisible(false);
				myPopup.getParent().getChildren().remove(myPopup);
			}
		});

		HtmlPanelGroup headerGroup = new HtmlPanelGroup();
		headerGroup.getChildren().add(closeButton);

		myPopup.getFacets().put("header", headerGroup);

		HtmlPanelGroup bodyGroup = new HtmlPanelGroup();

		final HtmlInputText name = new HtmlInputText();
		final HtmlInputText value = new HtmlInputText();

		HtmlPanelGroup group = new HtmlPanelGroup();

		group.getChildren().add(name);
		group.getChildren().add(value);

		// Close Button in Header
		HtmlCommandButton submitButton = (HtmlCommandButton) FacesContext
				.getCurrentInstance().getApplication().createComponent(
						HtmlCommandButton.COMPONENT_TYPE);
		submitButton.setType("submit");
		submitButton.setValue("Absenden");
		submitButton.setId("submitButton");

		group.getChildren().add(submitButton);

		final RootLinkController finalRootLinkContr = rootLinkController;

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void processAction(ActionEvent arg0)
					throws AbortProcessingException {

				try {

					PrimitiveType newPrimitiveObject = new PrimitiveType();
					newPrimitiveObject.setName((String) name.getValue());
					newPrimitiveObject.setValue((String) value.getValue());

					Long id = primitiveValueService.create(newPrimitiveObject);

					myPopup.setVisible(false);
					myPopup.getParent().getChildren().remove(myPopup);

					finalRootLinkContr.linkTogether(newPrimitiveObject, id,
							jsfLink);

					jsfLink.setDomainId(id.toString());

					// Object object =
					// attachedObject.getValue(FacesContext.getCurrentInstance().getELContext());
					// Long id =
					// (Long)object.getClass().getMethod("getId").invoke(object);
					// //jsfLink.getParameterValuesPairs().get(0).setParameterValue(id.toString());
					// jsfLink.setDomainId(id.toString());
					// myPopup.setVisible(false);
					// myPopup.getParent().getChildren().remove(myPopup);
					// finalRootLinkContr.linkTogether(object, id, jsfLink);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		bodyGroup.getChildren().add(group);

		// myPopup.getFacets().put("body", bodyGroup);
		myPopup.getFacets().put("body", bodyGroup);

		return myPopup;

	}

}
