package com.timeWork.core;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Savepoint;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskXml{

	private static final String tasksMark = "TASKS";
	private static final String taskMark = "TASK";

	private Document document;
	private Element racine;
	private Element tasks;

	private String path;

	public TaskXml(String path) throws Exception{
		this.path = path;

		//On crée une instance de SAXBuilder
	    SAXBuilder sxb = new SAXBuilder();

  	   	document = sxb.build(new File(path));
  	   	racine = document.getRootElement();
  	   	tasks = racine.getChild(tasksMark);
	}

	public ObservableList<Timer> getTaskList(){
		ObservableList<Timer> array = FXCollections.observableArrayList();

		for (int i = 0; i < tasks.getChildren(taskMark).size(); i++) {
			Element test = (Element) tasks.getChildren(taskMark).get(i);

			Timer t = new Timer(test.getAttributeValue("id"),
					test.getAttributeValue("title"),
					test.getAttributeValue("customer"),
					test.getAttributeValue("description"),
					test.getAttributeValue("date"),
					Long.parseLong(test.getAttributeValue("time")));
			array.add(t);
		}

		return array;
	}

	public void addTask(Timer timer){
		Element tasks = racine.getChild(tasksMark);

		Element task = new Element(taskMark);
		task.setAttribute("id", timer.getId());
		task.setAttribute("title", timer.getTitleProperty().get());
		task.setAttribute("customer", timer.getConsumerProperty().get());
		task.setAttribute("description", timer.getDescriptionProperty().get());
		task.setAttribute("date", timer.getDateProperty().get());
		task.setAttribute("time", Long.toString(timer.getTime()));

		tasks.addContent(task);

		saveFile();
	}

	public void updateTasks(Timer timer){
		for (int i = 0; i < tasks.getChildren(taskMark).size(); i++) {
			Element task = (Element) tasks.getChildren(taskMark).get(i);
			if(task.getAttributeValue("id").equals(timer.getId())){
				task.setAttribute("id", timer.getId());
				task.setAttribute("title", timer.getTitleProperty().get());
				task.setAttribute("customer", timer.getConsumerProperty().get());
				task.setAttribute("description", timer.getDescriptionProperty().get());
				task.setAttribute("date", timer.getDateProperty().get());
				task.setAttribute("time", Long.toString(timer.getTime()));
				break;
			}
		}
		saveFile();
	}

	public static void createFileXML(String path){
		Element root = new Element("TimeWork");
		Document doc = new Document(root);

		Element e = new Element(tasksMark);
		root.addContent(e);

		try{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	        sortie.output(doc, new FileOutputStream(path));
		}catch (Exception ex) {

		}
	}

	//On enregistre notre nouvelle arborescence dans le fichier
	//d'origine dans un format classique.
	private void saveFile(){
		try{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	        sortie.output(document, new FileOutputStream(path));
		}catch (Exception e) {

		}
	}
}
