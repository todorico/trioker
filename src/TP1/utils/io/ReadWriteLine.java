package TP1.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import TP1.utils.go.Line;
import TP1.utils.go.Point;

/** Assumes UTF-8 encoding. JDK 7+. */
public class ReadWriteLine {
	File rf;
	ArrayList<String> textToWrite;
	private final static Charset ENCODING = StandardCharsets.UTF_8;

	public ReadWriteLine(String aFileName) {
		rf = new File(aFileName);
		textToWrite = new ArrayList<String>();
	}

	public ArrayList<Line> read() throws IOException {
		ArrayList<Line> segments = new ArrayList<Line>();
		try (Scanner scanner = new Scanner(rf, ENCODING.name())) {
			int i = 0;
			while (scanner.hasNextLine()) {
				segments.add(readLine(scanner.nextLine(), i++));
			}
		}
		System.out.println(segments.size() + " segments lus");
		return segments;
	}

	// suppose que le fichier contient des paquets de 4 lignes de coordonn�es...
	Line readLine(String aLine, int i) {
		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter(";");
		Point gauche, droite;
		String xg, xd, yg, yd;

		Line s = null;
		if (scanner.hasNext()) {
			// assumes the line has a certain structure
			xg = scanner.next();
			yg = scanner.next();
			gauche = new Point(Integer.parseInt(xg), Integer.parseInt(yg));
			xd = scanner.next();
			yd = scanner.next();
			droite = new Point(Integer.parseInt(xd), Integer.parseInt(yd));
			s = new Line(gauche, droite);
			//s.setLabel("s " + i);
		}
		scanner.close();
		return s;
	}

	public void write() {
		PrintWriter pw;
		try {
			pw = new PrintWriter(rf);
			for (String s : textToWrite) {
				pw.println(s);
				pw.flush();
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void add(String s) {
		textToWrite.add(s);
	}

}
