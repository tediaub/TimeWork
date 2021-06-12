package com.timeWork.view.anniv;

import com.timeWork.view.ViewController;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnniveController extends ViewController{

	@FXML
	private ImageView image;

    @FXML
	private void initialize(){
    	image.setImage(new Image(getClass().getResourceAsStream("anniv.gif")));
	}
}