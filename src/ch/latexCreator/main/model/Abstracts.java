package ch.latexCreator.main.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

/**
 * The abstracts class which extends Sections class creates a abstract section
 * object. It has method to write the abstract into the tmp file with latex
 * format.
 */
public class Abstracts extends Sections {

	public Abstracts() {
		super(null, null, null);
	}

	/**
	 * This method writes the data in abstract object into the tmp file
	 */
	public void writeAdstracts(String fn) {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(fn),
					true /* append = true */));
			pw.println("\\begin{abstract}");
			pw.println(getsContent());
			pw.println("\\end{abstract}\n");

			pw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
