package FileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Класс для чтения данных из Файла
 */
public class InputFileData extends FileIO {
  private Scanner stream;

    /**
     * Конструктор
     *
     * @param fName - имя файла
     * @throws FileNotFoundException - при отсутствии файла
     */

  public InputFileData(String fName) throws FileNotFoundException {
    this.stream = new Scanner(new File(fName));
    fileName = fName;
  }

    /**
     * Прочесть следующую строку с данными
     *
     * @return - полученная строка
     */
  public String readLine() {
    return stream.nextLine();
  }

    /**
     * Получить следующее число из изсточника дпнных
     *
     * @return - полученное число при наличии или null
     */
  public Integer readNextInt() {
    String line;
    Integer result = null;
    while (result == null && hasNext()) {
      try {
        line = stream.nextLine();
        result = Integer.valueOf(line);
      } catch (NumberFormatException ignored) {
      }
    }
    return result;
  }

  /**
   * Проверка наличия неполученных данных в источнике
   *
   * @return - true\false
   */
  public boolean hasNext() {
    return stream.hasNext();
  }

    /**
     * Закрытие потока
     */
  @Override
  public void close() {
    stream.close();
    stream = null;
    fileName = null;
  }
}
