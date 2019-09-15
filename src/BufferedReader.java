import FileIO.InputFileData;

import java.util.LinkedList;
import java.util.List;

/** лимитированное чтение данных */
class BufferedReader {
  private static final int stringSize = 38; // размер String без данных в байтах
  private static final int integerSize = 16; // размер Integer в байтах

  /**
   * Читает данные указанного объема
   *
   * @param buffSize - желаемый объем данных
   * @param dataType - ожидаемый тип данных
   * @param source - источник данных
   * @return - массив полученных данных
   */
  static Comparable[] readBuffer(long buffSize, String dataType, InputFileData source) {

    List data = new LinkedList<>();
    int currentSize = 0;
    Comparable string = null;
    while (source.hasNext() && currentSize < buffSize) {
      if (dataType.equals("-s")) {
        string = source.readLine();
        if (string == null) break;
        currentSize +=
            stringSize
                + ((String) string).length() * 2; // объем памяти, занимаемой String. 2 - charSize
      }
      if (dataType.equals("-i")) {
        while (source.hasNext() && string == null) {
          string = source.readNextInt();
        }
        currentSize += integerSize;
      }
      data.add(string);
      string = null;
    }
    return (Comparable[]) data.toArray(new Comparable[0]);
  }
}
