package ch.latexCreator.main.view;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import ch.latexCreator.main.model.Abstracts;
import ch.latexCreator.main.model.Bibliography;
import ch.latexCreator.main.model.Document;
import ch.latexCreator.main.model.Images;
import ch.latexCreator.main.model.Sections;

/**
 * The controller class provides the control of the UI. It handles the actions
 * on the UI and call the methods related to the action to create a LaTex file.
 * 
 * @author Hankun Zhang Some codes are based on JavaFX tutorial:
 *         http://code.makery.ch/library/javafx-8-tutorial/
 *
 */
public class GUIController {

	@FXML
	private TextField fileName;
	@FXML
	private TextField title;
	@FXML
	private TextField author;
	@FXML
	private TextField sectionName;
	@FXML
	private TextArea sectionContent;
	@FXML
	private TextField bID;
	@FXML
	private TextField bTitle;
	@FXML
	private TextField bAuthor;
	@FXML
	private TextField bYear;
	@FXML
	private TextField bPub;
	@FXML
	private TextField bURL;
	@FXML
	private Button abstructButton;
	@FXML
	private Button sectionButton;
	@FXML
	private Button imageButton;
	@FXML
	private Button bibButton;
	@FXML
	private Button saveEntryButton;
	@FXML
	private Button saveSecButton;
	@FXML
	private Button saveTexButton;
	@FXML
	private Button disTexButton;

	private boolean createClicked = false;
	private String sType;
	private Document document;
	private int bibCount = 0;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * Crate the document and call methods to write data into a tmp file when
	 * button is Clicked
	 */
	public void createDocument() {

		document = new Document();
		document.setFileName(fileName.getText());
		document.setTitle(title.getText());
		document.setAuthor(author.getText());
		document.writeFile();
	}

	/**
	 * Change the UI when abstract button is clicked, and set type to abstract
	 */
	public void addAbstract() {
		sectionName.disableProperty().set(false);
		sectionContent.disableProperty().set(false);
		saveSecButton.disableProperty().set(false);
		abstructButton.disableProperty().set(false);
		sectionName.disableProperty().set(true);
		bibButton.disableProperty().set(false);
		sectionButton.disableProperty().set(true);
		imageButton.disableProperty().set(true);
		sType = "Abstract";
		sectionContent.clear();

	}

	/**
	 * Change the UI when section button is clicked, and set type to section
	 */
	public void addSection() {
		bibButton.disableProperty().set(false);
		imageButton.disableProperty().set(false);
		sectionName.disableProperty().set(false);
		sectionContent.disableProperty().set(false);
		sectionName.disableProperty().set(false);
		sectionContent.disableProperty().set(false);
		saveSecButton.disableProperty().set(false);
		sType = "Section";
		sectionContent.clear();

	}

	/**
	 * Pops up a window asking user for a image name in order to add the image
	 * into the document.
	 */
	public void addImage() {

		// create a dialog asking for the image name
		TextInputDialog dialog = new TextInputDialog("XXX.jpg");
		dialog.setTitle("IMAGE");
		dialog.setHeaderText("Add a Image");
		dialog.setContentText("Please enter the image name:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		// crate a new image
		Images newSection = new Images();
		newSection.setsType("Image");
		newSection.setsName(result.get());

		// append the image to the text area
		sectionContent.appendText("\n\\begin{figure}\n");
		sectionContent.appendText("\\centering\n");

		sectionContent.appendText("\\includegraphics[width = 1.8 in]{"
				+ newSection.getsName() + "}\n");
		sectionContent.appendText("\\label{"+newSection.getsName()+"}\n");

		sectionContent.appendText("\\end{figure}\n");

		// add new section into the document object
		document.setnewSection(newSection);

	}

	/**
	 * Change the UI when bib button is clicked to make it enable
	 */
	public void addBib() {
		bID.disableProperty().set(false);
		bYear.disableProperty().set(false);
		bTitle.disableProperty().set(false);
		bAuthor.disableProperty().set(false);
		bPub.disableProperty().set(false);
		bURL.disableProperty().set(false);
		bibButton.disableProperty().set(false);
		saveEntryButton.disableProperty().set(false);

	}

	/**
	 * Create a new bib file if it's the first bib. Append the bib the the
	 * existing file if it's not the first bib.
	 * 
	 */
	public void appendBib() {

		// check if the input is valid
		if (isInputBibValid()) {

			// create a new bib object and set the values from the input
			Bibliography newSection = new Bibliography();
			newSection.setbID(bID.getText());
			newSection.setbYear(bYear.getText());
			newSection.setbTitle(bTitle.getText());
			newSection.setbAuthor(bAuthor.getText());
			newSection.setbPub(bPub.getText());
			newSection.setbURL(bURL.getText());
			newSection.setsType("Bibliography");

			// add the bib into document object
			document.setnewSection(newSection);

			// check if it's the first bib
			if (bibCount == 0)
				newSection.writeBib();

			else
				newSection.appendBib();

			// add bib ID to text area
			sectionContent.appendText("\\cite{" + newSection.getbID() + "}");
			bibCount++;

			// clear the input when successfully added the bib
			bID.clear();
			bAuthor.clear();
			bTitle.clear();
			bYear.clear();
			bURL.clear();
			bPub.clear();
			bID.disableProperty().set(true);
			bYear.disableProperty().set(true);
			bTitle.disableProperty().set(true);
			bAuthor.disableProperty().set(true);
			bPub.disableProperty().set(true);
			bURL.disableProperty().set(true);
			saveEntryButton.disableProperty().set(true);

		}

	}

	/**
	 * Append the new section (or a abstract) to the file
	 */
	public void appendSection() {

		if (sType.equals("Abstract")) {
			Abstracts newSection = new Abstracts();
			newSection.setsType(sType);
			newSection.setsContent(sectionContent.getText());
			document.setnewSection(newSection);
			newSection.writeAdstracts(document.getFileName());
			abstructButton.disableProperty().set(true);

		} else if (sType.equals("Section")) {
			Sections newSection = new Sections();
			newSection.setsType(sType);
			newSection.setsName(sectionName.getText());
			newSection.setsContent(sectionContent.getText());
			document.setnewSection(newSection);
			newSection.writeSection(document.getFileName());

		}

		else
			System.out.print("Wrong Section type!");

		// clear the input and change the UI
		sectionContent.clear();
		sectionName.clear();
		bibButton.disableProperty().set(false);
		imageButton.disableProperty().set(true);

		sectionName.disableProperty().set(true);
		sectionContent.disableProperty().set(true);
		saveSecButton.disableProperty().set(true);
		sectionButton.disableProperty().set(false);

	}

	// call method to finish the tex file then SAVE button is clicked
	public void finishTex() {

		document.finishFile(bibCount);

	}

	// call method to delete the tex when DISCARD button is clicked
	public void deleteTex() {
		document.deleteFile();
	}

	// make buttons enable after create clicked
	public void enableButtons() {
		bibButton.disableProperty().set(false);
		abstructButton.disableProperty().set(false);
		imageButton.disableProperty().set(false);

		saveTexButton.disableProperty().set(false);
		disTexButton.disableProperty().set(false);
		sectionButton.disableProperty().set(false);

	}

	/**
	 * Returns true if the user clicked create, false otherwise.
	 * 
	 * @return
	 */
	public boolean isCreateClicked() {
		return createClicked;
	}

	/**
	 * Called when the user clicks Create. Call methods to create the tmp file
	 * and enable buttons
	 */
	@FXML
	private void handleCreate() {
		if (isInputValid()) {
			createDocument();
			enableButtons();
			createClicked = true;
		}
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (fileName.getText() == null || fileName.getText().length() == 0) {
			errorMessage += "No valid file name!\n";
		}

		if (title.getText() == null || title.getText().length() == 0) {
			errorMessage += "No valid title!\n";
		}

		if (author.getText() == null || author.getText().length() == 0) {
			errorMessage += "No valid author!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

	/**
	 * Validates the user input in the bib text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputBibValid() {
		String errorMessage = "";

		if (bID.getText() == null || bID.getText().length() == 0) {
			errorMessage += "No valid ID!\n";
		}

		if (bAuthor.getText() == null || bAuthor.getText().length() == 0) {
			errorMessage += "No valid Author!\n";
		}

		if (bTitle.getText() == null || bTitle.getText().length() == 0) {
			errorMessage += "No valid Title!\n";
		}
		if (bYear.getText() == null || bYear.getText().length() == 0) {
			errorMessage += "No valid Year!\n";
		}
		if (bPub.getText() == null || bPub.getText().length() == 0) {
			errorMessage += "No valid Publisher!\n";
		}
		if (bURL.getText() == null || bURL.getText().length() == 0) {
			errorMessage += "No valid URL!\n";
		}
		if (errorMessage.length() == 0) {
			return true;

		} else {

			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

}