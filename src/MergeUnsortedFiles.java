import FileIO.InputFileData;
import FileIO.OutputFileData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Слияние данных(String или Int) из условно сортированных файлов Если очередные данные не
 * вписываются в порядок сортировки они не обрабатываются.
 */
class MergeUnsortedFiles {

  /**
   * Собственно реализация
   *
   * @param for_merge - очередь имен файлов для слияния
   * @param outFileName - имя выходного файла
   * @param dataType - ожидаемый тип данных
   * @param isSortArc - порядок сортировки
   * @param <Comp> - тип данных должен наследоваться от Comparable
   * @throws IOException -
   */
  static <Comp extends Comparable<Comp>> void merge(
      Queue<String> for_merge, String outFileName, String dataType, boolean isSortArc)
      throws IOException {
    InputFileData[] sources = new InputFileData[for_merge.size()];
    int i = 0;
    ArrayList<Comp> lines = new ArrayList<>();
    for (String fileName : for_merge) {
      try {
        sources[i] = new InputFileData(fileName);
        lines.add(i, getNext(null, sources[i], isSortArc, dataType));
        i++;
      } catch (IOException e) {
        // log cant read
      }
    }
    try (OutputFileData outFile = new OutputFileData(outFileName)) {
      boolean firstLine = true;
      while (true) {
        Integer resId = findNextSortedValueId(lines, isSortArc);
        if (resId == null) break;
        Comp value = lines.get(resId);
        if (value == null) {
          break;
        }
        if (firstLine) {
          outFile.writeLine(value);
          firstLine = false;
        } else {
          outFile.writeLnLine(value);
        }
        lines.set(resId, getNext(value, sources[resId], isSortArc, dataType));
      }
    } catch (IOException e) {
      // log e.printStackTrace();
      throw new IOException("Невозможно сохранить данные в указанном файле");
    }
  }

  /**
   * Поиск индекса следующего значения, отвечающего порядку сортировки.
   *
   * @param lines - массив значений
   * @param isSortArc - порядок сортировки
   * @param <Comp> - тип данных
   * @return - индекс найденного элемента
   */
  private static <Comp extends Comparable<Comp>> Integer findNextSortedValueId(
      List<Comp> lines, boolean isSortArc) {
    int i = 0;
    Integer id = null;
    Comp res = null;
    for (Comp line : lines) {
      i++;
      if (line == null) continue;
      if (res == null) {
        res = line;
        id = i - 1;
      }
      if (res.compareTo(line) >= 0 == isSortArc) {
        res = line;
        id = i - 1;
      }
    }
    return id;
  }

  /**
   * получение из входного файла следующего элемента заданного типа данных, отвечающего порядку сортировки
   *
   * @param previousValue - предыдущее значение из этого файла
   * @param source - файл - источник данных
   * @param isSortArc - направление сортировки
   * @param dataType - ожидаемый тип данных
   * @param <T> - тип данных
   * @return - следующее значение при наличии или null
   */
  private static <T extends Comparable<T>> T getNext(
      T previousValue, InputFileData source, boolean isSortArc, String dataType) {
    String line = null;
    Integer intLine = null;
    while (source.hasNext() && line == null) {
      line = source.readLine();
      if (dataType.equals("-i")) {
        try {
          intLine = Integer.valueOf(line);
          if (previousValue != null && previousValue.compareTo((T) intLine) > 0 == isSortArc) {
            line = null;
            intLine = null;
          }
        } catch (NumberFormatException ignored) {
          line = null;
        }

      } else {
        if (previousValue != null && previousValue.compareTo((T) line) > 0 == isSortArc) {
          line = null;
          intLine = null;
        }
      }
    }
    return dataType.equals("-i") ? (T) intLine : (T) line;
  }
}
