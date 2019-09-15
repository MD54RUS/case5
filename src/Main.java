import FileIO.FileIO;
import FileIO.InputFileData;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Программа сортировки файлов слиянием
 *
 * <p>Принимаемые аргументы: ключ типа данных "-i" для обработки целочисленных значений или "-s" -
 * для строк, ключ направления сортировки "-a"(значение по умолчанию) - по возрастанию "-d" - по
 * убыванию, имя файла для записи отсортированного (программа стирает содержимое указанного
 * выходного файла!), список имен файлов для сортировки. При наличии ключа "-е" сортирует содержимое
 * файлов и записывает в выходной файл, При его отсутствии пытается объеденить содержимое исходных
 * файлов в ыходном, без нарушений порядка сортировки. Невалидные данные пропускает.
 */
public class Main {

  private static int fixed_memory_sizeMB =
      300; // максимальный размер блока данных, обрабатываемых за раз, в Мб.
  private static long mem_size = 1024 * 1024 * fixed_memory_sizeMB; // аналогично в байтах

  public static void main(String[] args) {

    try {
      Init.validate(args);
      if (!Init.extend) { // работа без ключа "-е"
        MergeUnsortedFiles.merge(
            Init.inputFileNames, Init.outFileName, Init.dataType, Init.isSortAsc);
        return;
      }
      // выполняется при наличии ключа "-е"
      Queue<String> filesForMerge = new LinkedList<>();
      for (String inputFileName : Init.inputFileNames) {
        try (InputFileData source =
            new InputFileData(inputFileName)) { // формирование списка источников данных
          while (source.hasNext()) {
            Comparable[] data =
                BufferedReader.readBuffer(
                    mem_size,
                    Init.dataType,
                    source); // чтение в буфер лимитированного колличества данных заданного типа
            MergeSortComparableData.sort(data, Init.isSortAsc); // сортировка буфера
            String sortedDump = FileIO.dumpData(data); // запись буфера во временное хранилище
            filesForMerge.offer(sortedDump); // добавление хранилища в очередь на слияние
          }
        }
      }
      MergeSortedFiles.merge(
          filesForMerge, Init.outFileName, Init.isSortAsc); // слияние очереди в выходной файл

    } catch (Exception ex) {
      if (ex.getMessage() != null) System.out.println(ex.getMessage());
      else System.out.println("An unexpected error has occurred.");
      // log ex.printStackTrace();
      System.exit(-1);
    }
  }
}
