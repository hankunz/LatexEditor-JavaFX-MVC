package ch.latexCreator.main.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Document class which contains all the information for the document. It has
 * methods to create a file and write the information into the file. Also, it
 * has methods to create the final latex file based on the tmp file.
 *
 * @author Hankun Zhang
 */
public class Document {

	private StringProperty fileName;
	private StringProperty title;
	private StringProperty author;
	private ObjectProperty<Sections> newSection;
	private ArrayList<Sections> newSectionList;

	/**
	 * Default constructor.
	 */
	public Document() {
		this(null, null, null);
	}

	/**
	 * Constructor with some initial data.
	 * 
	 */
	public Document(String fileName, String title, String author) {
		this.fileName = new SimpleStringProperty(fileName);
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty("some author");
		this.newSection = new SimpleObjectProperty<Sections>();
	}

	/**
	 * Getters and Setters
	 */
	public String getFileName() {
		return fileName.get();
	}

	public void setFileName(String fileName) {
		this.fileName.set(fileName);
	}

	public StringProperty fileNameProperty() {
		return fileName;
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public StringProperty titleProperty() {
		return title;
	}

	public String getAuthor() {
		return author.get();
	}

	public void setAuthor(String author) {
		this.author.set(author);
	}

	public StringProperty authorProperty() {
		return author;
	}

	public Sections getnewSection() {
		return newSection.get();
	}

	public void setnewSection(Sections newSection) {
		this.newSection.set(newSection);
		// addSection(newSection);

	}

	public void addSection(Sections newSection) {
		newSectionList.add(newSection);
	}

	public ObjectProperty<Sections> newSectionProperty() {
		return newSection;
	}

	/**
	 * create a tmp file and write the document information to it.
	 */
	public void writeFile() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName.get(), "UTF-8");
			writer.println("\\documentclass[]{article}");
			writer.println("\\usepackage{graphicx}");

			writer.println("\\title{" + title.get() + "}");
			writer.println("\\author{" + author.get() + "}");
			writer.println("\\begin{document}");
			writer.println("\\maketitle\n\n");

			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method to merge two files together. Codes are based on online tutorial
	 * (StackOverFlow)
	 */
	public static void mergeFiles(File[] files, File mergedFile) {

		FileWriter fstream = null;
		BufferedWriter out = null;
		try {
			fstream = new FileWriter(mergedFile, true);
			out = new BufferedWriter(fstream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		for (File f : files) {
			System.out.println("merging: " + f.getName());
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						fis));

				String aLine;
				while ((aLine = in.readLine()) != null) {
					out.write(aLine);
					out.newLine();
				}

				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * delete the tmp file and create the file tex file with added format
	 */
	public void finishFile(int bc) {
		String newName = fileName.get() + ".tex";

		File mergedFile = new File(newName);

		try {
			// If at least one bib is added, merge bib and tmp files together to
			// get the final .tex file
			if (bc != 0) {
				File[] files = new File[2];
				files[0] = new File(fileName.get());
				finishBib();
				files[1] = new File("bibliography.tex");
				mergeFiles(files, mergedFile);
				PrintWriter pw = new PrintWriter(new FileOutputStream(new File(
						newName), true /* append = true */));
				pw.println("\\end{document}\n");

				deleteFile();
				pw.close();

			}
			// if no bib is added, rename the tmp file to .tex file
			else {
				System.out.print("heizz");
				PrintWriter pw = new PrintWriter(new FileOutputStream(new File(
						fileName.get()), true /* append = true */));
				pw.println("\\end{document}\n");

				File file = new File(fileName.get());
				File file2 = new File(newName);
				file.renameTo(file2);

				pw.close();

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * add end to the bib section
	 */
	public void finishBib() {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(
					"bibliography.tex"), true /* append = true */));

			pw.println("\\end{thebibliography}");

			pw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * delete the tmp files
	 */
	public void deleteFile() {
		File fl = new File(fileName.get());
		File f2 = new File("bibliography.tex");
		f2.delete();
		fl.delete();
	}
}