package SimpleTodoList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class MainController extends ListCell implements Initializable {

    @FXML private TextField titleField; // 入力フィールド
    @FXML private ListView listBox;     // リスト一覧
    @FXML private Label label;          // システムコメント

    @FXML private Button addButton;     // 入力内容を追加
    @FXML private Button deleteButton;  // 選択したアイテムを削除
    @FXML private Button saveButton;    // リスト一覧を保存
    @FXML private Button newButton;     // 入力フィールドを空にする（Clearボタン）

    // リスト一覧
    private ObservableList<String> listItems = FXCollections.observableArrayList();

    // リストデータ参照先
    private Path path = Paths.get("todoList.txt");
    private File file = path.toFile();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // イベントの登録
        addButton.setOnAction(e -> addOnClicked());
        deleteButton.setOnAction(e -> deleteOnClicked());
        saveButton.setOnAction(e -> saveOnClicked());
        newButton.setOnAction(e -> newOnClicked());
        listBox.setOnMouseClicked(e -> listOnClicked());

        // TODOリストをlistBoxにセット
        listBox.setItems(listItems);

        // ファイルの読み込み、新規作成
        if (file.exists()){
            fileRead();
        }else try {
            file.createNewFile();
            label.setText(file + "を新規作成しました");
        } catch (IOException e) {
            label.setText("ファイルの読み込み失敗");
        }
    }

    // 入力フィールドのクリア
    private void titleClear(){
        titleField.setText("");
    }
    // addボタンの処理
    @FXML
    private void addOnClicked() {
        // listItems.add(inputField.getText());
        if (titleField.getText() != null) {
            addItems(titleField.getText());
            titleClear();
            label.setText("追加しました");
        }else {
            System.out.println("入力内容がありません");
        }
    }
    // 入力内容を一覧に追加
    private void addItems(String str){
        listItems.add(str);
    }
    // deleteボタンの処理
    @FXML
    private void deleteOnClicked(){
        int selectedItem = listBox.getSelectionModel().getSelectedIndex();
        listItems.remove(selectedItem);
        titleClear();
        label.setText("削除しました");
    }
    // saveボタンの処理
    @FXML
    private void saveOnClicked(){
        saveItems();
    }
    // 保存処理
    private void saveItems(){
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.file), "UTF-8"));

            for (String listItem : listItems) {
                writer.write(listItem);
                writer.newLine();
            }
            System.out.println("saved" + listItems);
            label.setText("保存しました");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // clearボタンの処理
    @FXML
    private void newOnClicked(){
        titleClear();
        label.setText("入力フィールドを空にしました");
    }
    // リストが押された場合の処理
    @FXML
    private void listOnClicked(){
        int selectedItem = listBox.getSelectionModel().getSelectedIndex();
        titleField.setText(listItems.get(selectedItem));
    }
    // ファイルの読み込み、新規作成
    private void fileRead(){
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
                String s;

                while ((s = reader.readLine()) != null) {
                    addItems(s);
                }
                label.setText("ファイルを読み込みました");
                reader.close();
            } catch (IOException e) {
                label.setText("ファイルの読み込み失敗");
            }
        }else try {
            file.createNewFile();
            label.setText("ファイルを新規作成しました");
        } catch (IOException e) {
            label.setText("ファイルの新規作成失敗");
        }
    }
}