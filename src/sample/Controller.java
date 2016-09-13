package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

import static sample.Main.*;

public class Controller {

    @FXML
    private Button btnOpen;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnSave;

    @FXML
    private Label labelBubble;

    @FXML
    private Label labelMerge;

    @FXML
    private Label labelInsertion;

    private File inputFile;
    private File outputFile;
    private Stage mainStage = new Stage();
    private int[] insertionSortedArray;

    @FXML
    public void openAction(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        inputFile = fileChooser.showOpenDialog(mainStage);//Указываем текущую сцену mainStage
        if (inputFile != null) { //Open
            btnStart.setDisable(false);
        }
    }

    @FXML
    public void sortAction(ActionEvent actionEvent) {

        ArrayList<String> stringList = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader((new FileReader(inputFile)))) {
            String s;
            while ((s = reader.readLine()) != null) {
                stringList.add(s);
            }
            String[] stringArray = stringList.toArray(new String[stringList.size()]);
            int[] arrayNumbers = new int[stringArray.length];
            try {
                for (int i = 0; i < arrayNumbers.length ; i++) {
                    arrayNumbers[i] = Integer.parseInt(stringArray[i]);
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод данных. Выполнение программы невозможно.");
                System.exit(1);
            }
            long bubbleStartTime = System.currentTimeMillis();
            int[] bubbleSortedArray = bubbleSort(arrayNumbers);
            long bubbleFinishTime = System.currentTimeMillis();
            long bubbleTimeSpent = bubbleFinishTime - bubbleStartTime;
            labelBubble.setText("Bubble: " + bubbleTimeSpent + " ms");

            long mergeStartTime = System.currentTimeMillis();
            int[] mergeSortedArray = mergeSort(arrayNumbers);
            long mergeFinishTime = System.currentTimeMillis();
            long mergeTimeSpent = mergeFinishTime - mergeStartTime;
            labelMerge.setText("Merge: " + mergeTimeSpent + " ms");

            long insertionStartTime = System.currentTimeMillis();
            insertionSortedArray = insertionSort(arrayNumbers);
            long insertionFinishTime = System.currentTimeMillis();
            long insertionTimeSpent = insertionFinishTime - insertionStartTime;
            labelInsertion.setText("Insertion: " + bubbleTimeSpent + " ms");

            btnSave.setDisable(false);

            System.out.println(bubbleTimeSpent);
            System.out.println(mergeTimeSpent);
            System.out.println(insertionTimeSpent);

        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден. Выполнение программы невозможно.");
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Неизвестная ошибка. Выполнение программы невозможно.");
            System.exit(1);
        }
    }

    @FXML
    public void saveAction(ActionEvent actionEvent) throws Exception {
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Save Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =  new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        outputFile = fileChooser.showSaveDialog(mainStage);//Указываем текущую сцену mainStage
        if (outputFile != null) {
            //Save
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                for (int i = 0; i < insertionSortedArray.length; i++) {
                    writer.write(insertionSortedArray[i] + System.getProperty("line.separator"));
                }
            } catch (Exception e) {
                System.out.println("Ошибка при записи данных");
                System.exit(1);
            }
        }
    }
}
