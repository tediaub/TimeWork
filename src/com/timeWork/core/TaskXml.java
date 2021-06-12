package com.timeWork.core;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public class TaskXml{

	private static final String tasksMark = "TASKS";
	private static final String taskMark = "TASK";

	private static final String projectsMark = "PROJECTS";
	private static final String projectMark = "PROJECT";

	private static Document document;
	private static Element racine;

	private static String path;

	public static void init(String path) throws Exception{
		TaskXml.path = path;

		//On crée une instance de SAXBuilder
	    SAXBuilder sxb = new SAXBuilder();

  	   	document = sxb.build(new File(path));
  	   	racine = document.getRootElement();

  	   	Element projects = racine.getChild(projectsMark);
  	   	if(projects == null){
  	   		//update file to v2
	  	   	HashMap<String, Project> tempProjectList = new HashMap<String, Project>();
	  	   	ArrayList<Task> tempTaskList = new ArrayList<Task>();
	  	   	Element tasks = racine.getChild(tasksMark);
	  	   	for (int i = 0; i < tasks.getChildren(taskMark).size(); i++) {
	  	   		Element tempTaskElement = (Element) tasks.getChildren(taskMark).get(i);

	  	   		String projectName = tempTaskElement.getAttributeValue("customer");

	  	   		if(!tempProjectList.containsKey(projectName)){
	  	   			tempProjectList.put(projectName, new Project(projectName, Color.color(Math.random(), Math.random(), Math.random()).toString()));
	  	   		}

	  	   		Task t = new Task(tempTaskElement.getAttributeValue("id"),
	  	   			tempTaskElement.getAttributeValue("title"),
	  	   			tempProjectList.get(projectName),
	  	   			tempTaskElement.getAttributeValue("description"),
					Long.parseLong(tempTaskElement.getAttributeValue("time")));
		  	   	t.setArchived(Boolean.parseBoolean(tempTaskElement.getAttributeValue("archive")));
				t.getDateProperty().setValue(tempTaskElement.getAttributeValue("date"));

				tempTaskList.add(t);
	  	   	}
	  	   	racine.removeContent();

	  	   	Element p = new Element(projectsMark);
	  	   	racine.addContent(p);
	  	   	Iterator<Entry<String, Project>> iterator = tempProjectList.entrySet().iterator();
	        while (iterator.hasNext()) {
	        	Map.Entry<String, Project> mapentry = (Map.Entry<String, Project>) iterator.next();
	        	addProject(mapentry.getValue());
	        }

	        for (Task task : tempTaskList) {
				addTask(task);
			}
  	   	}

	}

	public static void getData(ObservableList<Project> projectList, ObservableList<Task> taskList){
		Element projects = racine.getChild(projectsMark);
		for (int i = 0; i < projects.getChildren(projectMark).size(); i++) {
			Element root = (Element) projects.getChildren(projectMark).get(i);

			Project p = new Project(root.getAttributeValue("id"),
					root.getAttributeValue("title"),
					root.getAttributeValue("color"));
			projectList.add(p);

			Element tasksElement = root.getChild(tasksMark);
			for (int j = 0; j < tasksElement.getChildren(taskMark).size(); j++) {
				Element taskElement = (Element) tasksElement.getChildren(taskMark).get(j);

				taskList.add(new Task(taskElement.getAttributeValue("id"),
						taskElement.getAttributeValue("title"),
						p,
						taskElement.getAttributeValue("description"),
						Long.parseLong(taskElement.getAttributeValue("time")),
						Boolean.parseBoolean(taskElement.getAttributeValue("archive")),
						taskElement.getAttributeValue("date")));
			}
		}
	}

//	public static ObservableList<Project> getProjectList(){
//		ObservableList<Project> array = FXCollections.observableArrayList();
//
//		for (int i = 0; i < projects.getChildren(projectsMark).size(); i++) {
//			Element test = (Element) projects.getChildren(projectsMark).get(i);
//
//			Project p = new Project(test.getAttributeValue("id"),
//					test.getAttributeValue("title"),
//					test.getAttributeValue("color"));
//			array.add(p);
//		}
//
//		return array;
//	}

	public static void addProject(Project projectToAdd){
		Element projectsXml = racine.getChild(projectsMark);

		Element projectXml = new Element(projectMark);
		projectXml.setAttribute("id", projectToAdd.getId());
		projectXml.setAttribute("title", projectToAdd.getTitleProperty().get());
		projectXml.setAttribute("color", projectToAdd.getColorProperty().get());
		projectsXml.addContent(projectXml);

		projectXml.addContent(new Element(tasksMark));

		saveFile();
	}

	public static void addTask(Task taskToAdd){
		Element projectsXml = racine.getChild(projectsMark);
		for (int i = 0; i < projectsXml.getChildren(projectMark).size(); i++) {
			Element projectXml = (Element) projectsXml.getChildren(projectMark).get(i);

			System.out.println(projectXml.getAttributeValue("id"));
			System.out.println(taskToAdd.getProject().getId());

			if(projectXml.getAttributeValue("id").equals(taskToAdd.getProject().getId())){
				Element tasksXml = projectXml.getChild(tasksMark);

				Element taskXml = new Element(taskMark);
				taskXml.setAttribute("id", taskToAdd.getId());
				taskXml.setAttribute("title", taskToAdd.getTitleProperty().get());
				taskXml.setAttribute("description", taskToAdd.getDescriptionProperty().get());
				taskXml.setAttribute("date", taskToAdd.getDateProperty().get());
				taskXml.setAttribute("time", Long.toString(taskToAdd.getTime()));
				taskXml.setAttribute("archive", Boolean.toString(taskToAdd.isArchived()));

				tasksXml.addContent(taskXml);
			}
		}
		saveFile();
	}

	public static void updateTasks(Task taskToUpdate){
		Element projectsXml = racine.getChild(projectsMark);
		for (int i = 0; i < projectsXml.getChildren(projectMark).size(); i++) {
			Element projectXml = (Element) projectsXml.getChildren(projectMark).get(i);

			if(projectXml.getAttributeValue("id").equals(taskToUpdate.getProject().getId())){
				Element tasks = projectXml.getChild(tasksMark);
				for (int j = 0; j < tasks.getChildren(taskMark).size(); j++) {
					Element task = (Element) tasks.getChildren(taskMark).get(j);
					if(task.getAttributeValue("id").equals(taskToUpdate.getId())){
						task.setAttribute("id", taskToUpdate.getId());
						task.setAttribute("title", taskToUpdate.getTitleProperty().get());
						task.setAttribute("customer", taskToUpdate.getProjectProperty().get());
						task.setAttribute("description", taskToUpdate.getDescriptionProperty().get());
						task.setAttribute("date", taskToUpdate.getDateProperty().get());
						task.setAttribute("time", Long.toString(taskToUpdate.getTime()));
						task.setAttribute("archive", Boolean.toString(taskToUpdate.isArchived()));
						break;
					}
				}
			}
		}
		saveFile();
	}

	public static void updateProject(Project projectToUpdate){
		Element projectsXml = racine.getChild(projectsMark);
		for (int i = 0; i < projectsXml.getChildren(projectMark).size(); i++) {
			Element projectXml = (Element) projectsXml.getChildren(projectMark).get(i);

			if(projectXml.getAttributeValue("id").equals(projectToUpdate.getId())){
				projectXml.setAttribute("id", projectToUpdate.getId());
				projectXml.setAttribute("title", projectToUpdate.getTitleProperty().get());
				projectXml.setAttribute("color", projectToUpdate.getColorProperty().get());
				break;
			}
		}
		saveFile();
	}

	public static void removeTasks(Task taskToRemove){
		Element projectsXml = racine.getChild(projectsMark);
		for (int i = 0; i < projectsXml.getChildren(projectMark).size(); i++) {
			Element projectXml = (Element) projectsXml.getChildren(projectMark).get(i);

			if(projectXml.getAttributeValue("id").equals(taskToRemove.getProject().getId())){
				Element tasks = projectXml.getChild(tasksMark);

				for (int j = 0; j < tasks.getChildren(taskMark).size(); j++) {
					Element task = (Element) tasks.getChildren(taskMark).get(j);
					if(task.getAttributeValue("id").equals(taskToRemove.getId())){
						task.getParent().removeContent(task);
						break;
					}
				}
			}
		}
		saveFile();
	}

	public static void createFileXML(String path){
		Element root = new Element("TimeWork");
		Document doc = new Document(root);

		Element projects = new Element(projectsMark);
		root.addContent(projects);

		try{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	        sortie.output(doc, new FileOutputStream(path));
		}catch (Exception ex) {

		}
	}

	//On enregistre notre nouvelle arborescence dans le fichier
	//d'origine dans un format classique.
	private static void saveFile(){
		try{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	        sortie.output(document, new FileOutputStream(path));
		}catch (Exception e) {

		}
	}

	public static void moveTaskProject(Task task, Project oldProject, Project newProject) {
		Element projectsXml = racine.getChild(projectsMark);
		Element taskElement = null, oldProjectElement = null, newProjectElement = null;
		for (int i = 0; i < projectsXml.getChildren(projectMark).size(); i++) {
			Element projectXml = (Element) projectsXml.getChildren(projectMark).get(i);

			if(projectXml.getAttributeValue("id").equals(oldProject.getId())){
				oldProjectElement = projectXml;
				Element tasksElement = projectXml.getChild(tasksMark);
				for (int j = 0; j < tasksElement.getChildren(taskMark).size(); j++) {
					taskElement = (Element) tasksElement.getChildren(taskMark).get(j);
				}
			}else if(projectXml.getAttributeValue("id").equals(newProject.getId())){
				newProjectElement = projectXml;
			}
		}

		if(taskElement!=null && oldProjectElement !=null && newProjectElement !=null){
			taskElement.detach();
			newProjectElement.getChild(tasksMark).addContent(taskElement);
		}
	}
}
