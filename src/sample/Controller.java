package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import static sample.Main.bubbleSort;
import static sample.Main.insertionSort;
import static sample.Main.mergeSort;

public class Controller {
    File file;
    @FXML
    public void openAction(ActionEvent actionEvent) {
        Stage mainStage = new Stage();
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(mainStage);//Указываем текущую сцену CodeNote.mainStage
        if (file != null) { //Open
            System.out.println("Процесс открытия файла");
        }
//        JFileChooser fileopen = new JFileChooser();
//        int ret = fileopen.showDialog(null, "Открыть файл");
//        if (ret == JFileChooser.APPROVE_OPTION) {
//            file = fileopen.getSelectedFile();
//        }
    }

    public void sortAction(ActionEvent actionEvent) {
        ArrayList<String> stringList = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader((new FileReader(file)))) {
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

            long mergeStartTime = System.currentTimeMillis();
            int[] mergeSortedArray = mergeSort(arrayNumbers);
            long mergeFinishTime = System.currentTimeMillis();
            long mergeTimeSpent = mergeFinishTime - mergeStartTime;

            long insertionStartTime = System.currentTimeMillis();
            int[] insertionSortedArray = insertionSort(arrayNumbers);
            long insertionFinishTime = System.currentTimeMillis();
            long insertionTimeSpent = insertionFinishTime - insertionStartTime;

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
}
