package main.java.org.example.ejemplotablasagregarquitarfilas;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Arrays;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import static javafx.scene.control.TableView.TableViewSelectionModel;
/**
 * clase que extiende de aplication y genera una pantalla usando los atributos abajo usados, valiendose
 * ademas de las clases Person, y PersonTableUtil
 * 
 */
public class TableViewAddDeleteRows extends Application {
    // Fields to add Person details
    private TextField fNameField;
    private TextField lNameField;
    private DatePicker dobField;
    private TableView<Person> table;
    /**
     * lanza la aplicacion
     * @param args
     * pasado a la main como argumento
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    @SuppressWarnings("unchecked")
    /**
     * este metodo arranca el programa. para ello, declara las variables indicadas arriba,
     * y se vale de la clase PersonTableUtil tambien, añade los elementos a la pantalla y los muestra.
     * declara varios botones con distintas funciones, adewmas de un VBox con un estilo personalizado
     * y muestra la escena por ultimo
     * @param stage
     * la escena a mostrar
     */
    public void start(Stage stage) {
        fNameField = new TextField();
        lNameField = new TextField();
        dobField = new DatePicker();
        table = new TableView<>(PersonTableUtil.getPersonList());
        // Turn on multi-row selection for the TableView
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);
        // Add columns to the TableView
        /**
         * añade todos los elementos a la TableView decalrados anteriormente
         */
        table.getColumns().addAll(PersonTableUtil.getIdColumn(), PersonTableUtil.getFirstNameColumn(), PersonTableUtil.getLastNameColumn(), PersonTableUtil.getBirthDateColumn());
        GridPane newDataPane  = this.getNewPersonDataPane();
        Button restoreBtn = new Button("Restore Rows");
        restoreBtn.setOnAction(e -> restoreRows());
        Button deleteBtn = new Button("Delete Selected Rows");
        deleteBtn.setOnAction(e -> deleteSelectedRows());
        VBox root = new VBox(newDataPane, new HBox(restoreBtn, deleteBtn), table);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Adding/Deleting Rows in a TableViews");
        stage.show();
    }

    /**
     * genera un gridpane, lo personaliza y ajusta, despues genera un boton para añadir contenido
     * @return
     * devuelve el panel creado
     */
    public GridPane getNewPersonDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        pane.addRow(0, new Label("First Name:"), fNameField);
        pane.addRow(1, new Label("Last Name:"), lNameField);
        pane.addRow(2, new Label("Birth Date:"), dobField);
        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> addPerson());
        // Add the "Add" button
        pane.add(addBtn, 2, 0);
        return pane;
    }

    /**
     * permite elegir una fila a eliminar de la pantalla. se pregunta por una fila, y el metodo mete
     * todo el contenido en un array que luego va borrando en un bucle hasta el largo del array
     */
    public void deleteSelectedRows() {
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        if (tsm.isEmpty()) {
            System.out.println("Please select a row to delete.");
            return;
        }
        // Get all selected row indices in an array
        ObservableList<Integer> list = tsm.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);
        // Sort the array
        Arrays.sort(selectedIndices);
        // Delete rows (last to first)
        for(int i = selectedIndices.length - 1; i >= 0; i--) {
            tsm.clearSelection(selectedIndices[i].intValue());
            table.getItems().remove(selectedIndices[i].intValue());
        }
    }
    /**
     * Restaura las filas con la lista de personas
     */
    public void restoreRows() {
        table.getItems().clear();
        table.getItems().addAll(PersonTableUtil.getPersonList());
    }
    /**
     * lee el contenido y crea una objeto Person con el
     * @return
     * la persona con los textos en los campos
     */
    public Person getPerson() {
        return new Person(fNameField.getText(), lNameField.getText(), dobField.getValue());
    }
    /**
     * lee una persona y la añade a la tabla argumento
     */
    public void addPerson() {
        Person p = getPerson();
        table.getItems().add(p);
        clearFields();
    }
    /**
     * limpia los campos de la fila seleccionada, y los setea a nulos
     */
    public void clearFields() {
        fNameField.setText(null);
        lNameField.setText(null);
        dobField.setValue(null);
    }
}