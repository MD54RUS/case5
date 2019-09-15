class MergeSortComparableData {

  /**
   * Алгоритм сортировки слиянием.
   *
   * @param origArray массив Comparable элементов.
   */
  static void sort(Comparable[] origArray, boolean isSortArc) {
    Comparable[] tmpArray = new Comparable[origArray.length];
    sort(origArray, tmpArray, 0, origArray.length - 1, isSortArc);
  }

  /**
   * Метод рекурсивного вызова.
   *
   * @param origArray массив Comparable элементов.
   * @param tmpArray результирующий массив.
   * @param left инлекс первого элемента подмассива.
   * @param right индекс последнего элемента подмассиива.
   */
  private static void sort(
      Comparable[] origArray, Comparable[] tmpArray, int left, int right, boolean isSortArc) {
    if (left < right) {
      int center = (left + right) / 2;
      sort(origArray, tmpArray, left, center, isSortArc);
      sort(origArray, tmpArray, center + 1, right, isSortArc);
      merge(origArray, tmpArray, left, center + 1, right, isSortArc);
    }
  }

  /**
   * Метод слияния двух отсортированных подмассивов.
   *
   * @param origArray массив Comparable элементов.
   * @param tmpArray результирующий массив.
   * @param leftPos индекс первого элемента подмассива.
   * @param rightPos индекс первого элемента второго подмассива.
   * @param rightEnd индекс последнего элемента подмассива.
   */
  private static void merge(
      Comparable[] origArray,
      Comparable[] tmpArray,
      int leftPos,
      int rightPos,
      int rightEnd,
      boolean isSortArc) {
    int leftEnd = rightPos - 1;
    int tmpPos = leftPos;
    int numElements = rightEnd - leftPos + 1;

    while (leftPos <= leftEnd && rightPos <= rightEnd)
      if (origArray[leftPos].compareTo(origArray[rightPos]) <= 0 == isSortArc) tmpArray[tmpPos++] = origArray[leftPos++];
      else tmpArray[tmpPos++] = origArray[rightPos++];

    while (leftPos <= leftEnd) // копирование оставшихся элементов первого подмассива
    tmpArray[tmpPos++] = origArray[leftPos++];

    while (rightPos <= rightEnd) // копирование оставшихся элементов второго подмассива
    tmpArray[tmpPos++] = origArray[rightPos++];

    // Копирование результата в оригинальный массив
    for (int i = 0; i < numElements; i++, rightEnd--) origArray[rightEnd] = tmpArray[rightEnd];
  }
}
