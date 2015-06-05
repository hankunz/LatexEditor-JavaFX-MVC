package ch.latexCreator.main.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * The Images class which extends Sections class create a image object to hold
 * the data of the image. It has a function to print the image into the tmp tex
 * file.
 *
 */
public class Images extends Sections {

	public Images() {
		super(null, null, null);
	}

	public void writeImage(String fn) {
		try {
			// find the file and write into it
			PrintWriter pw = new PrintWriter(new FileOutputStream(new File(fn),
					true /* append = true */));
			pw.println("\\begin{figure}");
			pw.println("\\includegraphics[width = 1.8 in]{" + getsName() + "}");
			pw.println("\\end{figure}\n");

			pw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
