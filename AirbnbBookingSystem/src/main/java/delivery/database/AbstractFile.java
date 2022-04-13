package delivery.database;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public abstract class AbstractFile{
	protected String fileName;
	protected Scanner fileScanner;
	protected File file;
	protected FileWriter fileWriter;
	protected PrintWriter printWriter;

	public AbstractFile(String fileName) {
		this.fileName = fileName;
	}

	public static String getDELIMITER() {
		return ";";
	}
}
	