import FileIO.FileIO;
import FileIO.InputFileData;
import FileIO.OutputFileData;

import java.io.IOException;
import java.util.Queue;

class MergeSortedFiles {

  /**
   * Фунция слияния отсортированных файлов с сохранением порядка сортировки
   *
   * @param filesForMerge - очередь файлов для слияния
   * @param fileOutName - имя файла для выходных данных
   * @param sortType - направление сортировки
   * @throws IOException -
   */
  static void merge(Queue<String> filesForMerge, String fileOutName, boolean sortType)
      throws IOException {
    if (filesForMerge.size() == 0) {
      throw new RuntimeException("Невозможно получить данные.");
    }
    while (filesForMerge.size() > 1) {
      InputFileData source1 = new InputFileData(filesForMerge.poll());
      InputFileData source2 = new InputFileData(filesForMerge.poll());
      OutputFileData tempSource;
      if (filesForMerge.size() == 0) {
        tempSource = new OutputFileData(fileOutName);
      } else {
        tempSource = FileIO.getTempContainer();
      }
      mergePair(source1, source2, tempSource, sortType);
      filesForMerge.offer(tempSource.getFileName());
    }
    filesForMerge.poll();
  }

  /**
   * функция слияния пары отсортированных файлов в третий с учетом направления сортировки
   *
   * @param source1 - первый источник данных
   * @param source2 - второй источник данных
   * @param destination - получатель объединенных данных
   * @param sortType - направление сортировки
   * @throws IOException -
   */
  private static void mergePair(
      InputFileData source1, InputFileData source2, OutputFileData destination, boolean sortType)
      throws IOException {

    Comparable value1 = getNext(source1);
    Comparable value2 = getNext(source2);
    while (value1 != null && value2 != null) {
      if (value1.compareTo(value2) > 0 == sortType) {
        destination.writeLineLn(value2);
        value2 = getNext(source2);
      } else {
        destination.writeLineLn(value1);
        value1 = getNext(source1);
      }
    }
    if (value1 == null) {
      if (value2 != null) destination.writeLine(value2);
      appendFile(source2, destination);
    } else {
      destination.writeLine(value1);
      appendFile(source1, destination);
    }
    source1.deleteSource();
    source2.deleteSource();
    destination.close();
  }

  /**
   * функция получения следующего значения из источника данных
   *
   * @param source - источник данных
   * @return - полученное значение или null при отсутствии следующего
   */
  private static Comparable getNext(InputFileData source) {
    if (!source.hasNext()) return null;
    if (Init.dataType.equals("-i")) {
      return source.readNextInt();
    } else {
      return source.readLine();
    }
  }

  /**
   * функция записи данных из источника в получатель
   *
   * @param source - источник данных
   * @param dest - получатель данных
   * @throws IOException -
   */
  private static void appendFile(InputFileData source, OutputFileData dest) throws IOException {

    while (source.hasNext()) {
      dest.writeLnLine(source.readLine());
    }
  }
}
