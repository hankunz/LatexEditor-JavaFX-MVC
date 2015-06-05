package ch.latexCreator.main.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This Bibliography class which extends the Sections class create a bib object
 * with six fields. It has setters and getters for each filed, a write method to
 * create a new bib tmp file, and a append method to append the bib objects data
 * to the existing bib tmp file.
 */

public class Bibliography extends Sections {
	private StringProperty bAuthor;
	private StringProperty bTitle;
	private StringProperty bYear;
	private StringProperty bID;
	private StringProperty publisher;
	private StringProperty URL;

	public Bibliography() {
		this(null, null, null, null, null, null);
	}

	/**
	 * Constructor with some initial data.
	 * 
	 */
	public Bibliography(String a, String t, String y, String id, String pb,
			String U) {
		this.bAuthor = new SimpleStringProperty(a);
		this.bTitle = new SimpleStringProperty(t);
		this.bYear = new SimpleStringProperty(y);
		this.bID = new SimpleStringProperty(id);
		this.publisher = new SimpleStringProperty(pb);
		this.URL = new SimpleStringProperty(U);

	}

	/**
	 * Getters and Setters
	 */
	public String getbAuthor() {
		return bAuthor.get();
	}

	public void setbAuthor(String bAuthor) {
		this.bAuthor.set(bAuthor);
	}

	public StringProperty bAuthorProperty() {
		return bAuthor;
	}

	public String getbTitle() {
		return bTitle.get();
	}

	public void setbTitle(String bTitle) {
		this.bTitle.set(bTitle);
	}

	public StringProperty bTitleProperty() {
		return bTitle;
	}

	public String getbYear() {
		return bYear.get();
	}

	public void setbYear(String bYear) {
		this.bYear.set(bYear);
	}

	public StringProperty bYearProperty() {
		return bYear;
	}

	public String getbID() {
		return bID.get();
	}

	public void setbID(String bID) {
		this.bID.set(bID);
	}

	public StringProperty bIDProperty() {
		return bID;
	}

	public String getbPub() {
		return publisher.get();
	}

	public void setbPub(String bAuthor) {
		this.publisher.set(bAuthor);
	}

	public String getbURL() {
		return URL.get();
	}

	public void setbURL(String bAuthor) {
		this.URL.set(bAuthor);
	}

	/**
	 * This method create a new bib tmp file to store all bib data with latex
	 * format
	 */
	public void writeBib() {
		PrintWriter pw;
		try {
			// create the file
			pw = new PrintWriter("bibliography.tex", "UTF-8");

			// write information into the file
			pw.println("\\begin{thebibliography}{99}\n");
			pw.println("\\bibitem{reference" + getbID() + "}");
			pw.println(getbAuthor() + ",");
			pw.println("\\emph{" + getbTitle() + "}");
			pw.println(getbPub());
			pw.println("\\url{" + getbURL() + "}," + getbYear() + "\n");

			pw.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method append the bib information into the existing bib tmp file.
	 */
	public void appendBib() {
		try {
			// create a file and write data into it
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(
					"bibliography.tex"), true /* append = true */));
			pw.println("\\bibitem{reference" + getbID() + "}");

			pw.println(getbAuthor() + ",");
			pw.println("\\emph{" + getbTitle() + "}");
			pw.println(getbPub());
			pw.println("\\url{" + getbURL() + "}," + getbYear() + "\n");

			pw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}