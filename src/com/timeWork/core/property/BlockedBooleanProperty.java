package com.timeWork.core.property;

import javafx.beans.property.SimpleBooleanProperty;

public class BlockedBooleanProperty extends SimpleBooleanProperty {

	private boolean blocked;
	private boolean lastState;

	public void setPropertyBlocked(boolean blocked){
		if(!blocked){
			super.setValue(lastState);
		}
		this.blocked = blocked;
	}

	public boolean getPropertyBlocked(){
		return blocked;
	}

	@Override
	public void set(boolean newValue) {
		if(!blocked){
			super.set(newValue);
		}
		lastState = newValue;
	}

	@Override
	public void setValue(Boolean v) {
		if(!blocked){
			super.setValue(v);
		}
		lastState = v;
	}
}
