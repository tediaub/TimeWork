package com.timeWork.view;

import javafx.scene.Node;

public abstract class ViewController {

	protected static MainController mainControl;
	protected Node node;

	public void setMainController(MainController control){
		this.mainControl = control;
	}

	public MainController getMainController(){
		return mainControl;
	}

	public void setView(Node view){
		this.node = view;
	}

	public Node getView(){
		return node;
	}
}
