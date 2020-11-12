import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //чтение корневой директории
        File dir = new File("C:/test");

        //Используем метод getFiles для получения списков Имён текстовых файлов и их адресов
        ArrayList<ArrayList<String>> list = getFiles(dir);
        ArrayList<String> names = list.get(0);
        ArrayList<String> address = list.get(1);

        //Для сортировки создаем словарь  (Имя файла : адрес файла)
        HashMap<String, String> result = new HashMap<>();
        for (int i = 0; i < names.size(); i++){
            result.put(names.get(i), address.get(i));
        }

        //Сортируем список Имён, после в цикле из словаря по ключу (Имя Файла) получаем pathname и читаем файлы
        Collections.sort(names);
        StringBuilder textResult = new StringBuilder();
        for (String name : names) {
            String path = result.get(name);
            File file = new File(path);
            try {
                textResult.append(readFile(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Записываем в наш новый файл итоговый текст.
        try(FileWriter writer = new FileWriter("C:/test/myFile.txt", false)){
            writer.write(textResult.toString());
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }
    //Рекурсивный метод для получения Имён файлов и их path
    private static ArrayList<ArrayList<String>> getFiles(File dir) {
        ArrayList<String> nameFiles = new ArrayList<>();
        ArrayList<String> addressFiles = new ArrayList<>();
        if (dir.isDirectory()) {
            // получаем все вложенные объекты в каталоге
            for (File item : Objects.requireNonNull(dir.listFiles())) {
                if (item.isFile()) {
                    nameFiles.add(item.getName());
                    addressFiles.add(item.getAbsolutePath());
                } else {
                    //рекурсия
                    ArrayList<ArrayList<String>> tempList = getFiles(item);
                    nameFiles.addAll(tempList.get(0));
                    addressFiles.addAll(tempList.get(1));
                }
            }
        }
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        result.add(nameFiles);
        result.add(addressFiles);
        return result;
    }

    //Метод для чтения файлов
    private static String readFile (File file)throws IOException {
        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }

}
