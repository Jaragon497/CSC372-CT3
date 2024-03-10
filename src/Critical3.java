import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.Random;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Critical3 extends Application {

    private TextArea textBox;
    private VBox vertLayout;
    private MenuItem menuChangeBg;
    private final Random random = new Random();

    // launch the application
    public void start(Stage stage)
    {
        // set title for the stage
        stage.setTitle("MenuBar App");
        Menu mainMenu = new Menu("Menu");

        // Create menu items
        MenuItem menuDateTime = new MenuItem("Print Date/Time");
        MenuItem menuLog = new MenuItem("Write to `log.txt`");
        menuChangeBg = new MenuItem("Change Background Color");
        MenuItem menuExit = new MenuItem("Exit");

        // Construct menu
        mainMenu.getItems().add(menuDateTime);
        mainMenu.getItems().add(menuLog);
        mainMenu.getItems().add(menuChangeBg);
        mainMenu.getItems().add(menuExit);

        // Determine behavior of each menu item
        menuDateTime.setOnAction(event -> showDateTime());
        menuLog.setOnAction(event -> saveToLog());
        menuChangeBg.setOnAction(event -> setBgColor());
        menuExit.setOnAction(event -> stage.close());

        // Create the menubar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(mainMenu);

        textBox = new TextArea();
        textBox.setMaxSize(400, 30);
        textBox.setEditable(false);
        textBox.setPadding(new Insets(0, 70, 0, 70));

        // Create layout for main window
        vertLayout = new VBox(menuBar, textBox);

        Scene scene = new Scene(vertLayout, 400, 250);
        stage.setScene(scene);

        stage.show();
    }

    private void showDateTime() {
        // Get current time information
        LocalDateTime now = LocalDateTime.now();
        // Format time in human-readable format, convert to string
        // Set text in text box to string
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy | HH:mm:ss");
        String formatted = now.format(timeFormatter);
        textBox.setText(formatted);
    }

    // Function to save to log
    private void saveToLog() {
        try {
            // Create file, write info in textBox to `log.txt`
            FileWriter writer = new FileWriter("./log.txt");
            String textBoxText = textBox.getText();
            writer.write(textBoxText);
            // Close writer and output success msg to console
            writer.close();
            System.out.println("Successfully wrote to `./log.txt`");

        } catch (Exception e) {
            // If error, log msg to console
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void setBgColor(){
        int randomGreenHue = random.nextInt(180); // Generate random green hue (0-179)
            Color newColor = Color.hsb(randomGreenHue, 1f, 1f);
            Background bg = new Background(new BackgroundFill(newColor, CornerRadii.EMPTY, Insets.EMPTY));
            vertLayout.setBackground(bg);
            menuChangeBg.setText("Change Background Color (Green: " + randomGreenHue + ")"); // Update menu item text with chosen hue
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
