package ch.latexCreator.main.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Sections super class create a section object which has fileds of name,
 * content, and type. It has getters and setters for all fields, and a write
 * method to write the data into the tmp tex file.
 * 
 *
 */
public class Sections {
	private StringProperty sName;
	private StringProperty sContent;
	private StringProperty sType;

	public Sections() {
		this(null, null, null);
	}

	/**
	 * Constructor with some initial data.
	 * 
	 */
	public Sections(String sName, String sContent, String sType) {
		this.sName = new SimpleStringProperty(sName);
		this.sContent = new SimpleStringProperty(sContent);
		this.sType = new SimpleStringProperty(sType);
	}

	public String getsType() {
		return sType.get();
	}

	public void setsType(String sType) {
		this.sType.set(sType);
	}

	public StringProperty sTypeProperty() {
		return sType;
	}

	public String getsName() {
		return sName.get();
	}

	public void setsName(String sName) {
		this.sName.set(sName);
	}

	public StringProperty sNameProperty() {
		return sName;
	}

	public String getsContent() {
		return sContent.get();
	}

	public void setsContent(String sContent) {
		this.sContent.set(sContent);
	}

	public StringProperty sContentProperty() {
		return sContent;
	}

	/**
	 * Write the section data into the tmp file
	 * 
	 * @param fn
	 */
	public void writeSection(String fn) {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(fn),
					true /* append = true */));
			pw.println("\\section{" + getsName() + "}");
			pw.println(getsContent());
			pw.println("\n");

			pw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
