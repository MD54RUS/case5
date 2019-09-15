import java.util.LinkedList;
import java.util.Queue;

// todo dataType по-хорошему ENUM

class Init {
  static boolean isSortAsc = true;
  static String dataType = null;
  static String outFileName = null;
  static Queue<String> inputFileNames = new LinkedList<>();
  static Boolean extend = false;

  /**
   * Парсер переданных параметров
   *
   * @param args массив переданных параметров
   */
  private static void initArgs(String[] args) {
    for (String arg : args) {
      if (arg.equals("-a") || arg.equals("-d")) isSortAsc = arg.equals("-a");
      else if (arg.equals("-s") || arg.equals("-i")) dataType = arg;
      else if (arg.equals("-e")) extend = true;
      else if (outFileName == null) outFileName = arg;
      else inputFileNames.add(arg);
    }
  }

  /**
   * Валидатор переданных параметров
   *
   * @param args массив переданных параметров
   */
  static void validate(String[] args) {
    initArgs(args);
    if (dataType == null) {
      throw new RuntimeException("Data type not specified.");
    }
    if (outFileName == null) {
      throw new RuntimeException("No output file specified.");
    }
    if (inputFileNames.isEmpty()) {
      throw new RuntimeException("List of input files not specified.");
    }
  }
}
