package FileIO;

import java.io.File;
import java.io.IOException;

public abstract class FileIO implements AutoCloseable{
    static String fileName;

    @Override
    public abstract void close() throws Exception;

    public String getFileName() {
        return fileName;
    }

    /**
     * Удаление используемого файла и отчистка экземпряра класса
     */

    public void deleteSource(){
        try {
            close();
        } catch (Exception ignored) {}
        if (fileName != null) {
            File thisFile = new File(fileName);
            thisFile.delete();
            fileName = null;
        }
    }

    /**
     * сброс данных во вроеменное хранилище
     *
     * @param data - данные для сохранения
     * @return - имя хранилища
     * @throws IOException -
     */
    public static String dumpData(Comparable[] data) throws IOException {
        OutputFileData tempFile = getTempContainer();
        tempFile.writeData(data);
        tempFile.close();
        return tempFile.getFileName();
    }


    /**
     * Получение временного хранилища для данных
     *
     * @return - временное хранилище
     * @throws IOException -
     */
    public static OutputFileData getTempContainer() throws IOException {
        File tempFile = File.createTempFile("MergeSortTemp", null);
        return new OutputFileData(tempFile.getAbsolutePath());
    }

}


