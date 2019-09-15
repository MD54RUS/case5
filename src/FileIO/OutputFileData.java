package FileIO;

import java.io.IOException;

/**
 * Класс для записи данных в файл
 */
public class OutputFileData extends FileIO {
  private java.io.FileWriter stream;
  private boolean isFileEmpty;

    /**
     * Конструктор
     *
     * @param fName - имя файла
     * @throws IOException - ошибка открытия на запись
     */

  public OutputFileData(String fName) throws IOException {
    this.stream = new java.io.FileWriter(fName);
    fileName = fName;
    isFileEmpty = true;
  }

    /**
     * Запись блока данных
     *
     * @param data - блок данных
     * @throws IOException - ошибка записи
     */

  void writeData(Comparable[] data) throws IOException {
    for (Comparable datum : data) {
      if (!isFileEmpty) writeLnLine(datum);
      else writeLine(datum);
      isFileEmpty = false;
    }
  }

    /**
     * Запись строки
     *
     * @param o - объект с данными
     * @throws IOException -
     */

  public void writeLine(Object o) throws IOException {
    stream.write(o.toString());
  }

  /**
   * запись в файл строки и перевода каретки
   *
   * @param o объект с данными
   * @throws IOException -
   */
  public void writeLineLn(Object o) throws IOException {
    writeLine(o);
    writeLine(System.lineSeparator());
  }

  /**
   * запись в файл переданных данных с новой строки
   *
   * @param o объект с данными
   * @throws IOException -
   */
  public void writeLnLine(Object o) throws IOException {
    writeLine(System.lineSeparator());
    writeLine(o);
  }

    /**
     * Закрытие потока
     */

  @Override
  public void close() {
    try {
      stream.close();
      stream = null;
      fileName = null;
    } catch (IOException ignored) {
    }
  }
}
