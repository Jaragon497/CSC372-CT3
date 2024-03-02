import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Critical3 extends Application {

    private TextArea textBox;
    private VBox vertLayout;
    private MenuItem menuChangeBg;

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
        String randomGreen = randomGreen();
        vertLayout.setStyle("-fx-background-color: " + randomGreen);
    }

    private String randomGreen(){
        // Create placeholder values
        double rVal;
        double gVal;
        double bVal;

        // Randomly generate values, ensuring that color will be green
        do {
            rVal = Math.random();
            gVal = Math.random();
            bVal = Math.random();
        } while (gVal < 0.3 || rVal > gVal - 0.15 || bVal > gVal - 0.15);

        int hue = getHue(rVal, gVal, bVal);

        // Convert random values into hex values
        int r = (int) (rVal * 255);
        int g = (int) (gVal * 255);
        int b = (int) (bVal * 255);

        // Update menu item (bullet #3 in instructions)
        menuChangeBg.setText("Change Background Color (Hue: " + hue + ")");
        return String.format("#%02x%02x%02x", r, g, b);
    }

    // Algo to calculate hue
    private int getHue(double r, double g, double b){
        double min = Math.min(r, b);
        double hueRaw = 2.0 + (b - r)/(g - min);

        int hue = (int) (hueRaw*60);
        if (hue < 0){
            hue += 360;
        }
        // Output for debugging purposes
        System.out.println("Current hue: " + hue);
        return hue;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
