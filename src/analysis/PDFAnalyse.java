package analysis;

/**
 * @author 111250241
 * @created 2014-12-30
 * @version 1.0*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class PDFAnalyse {
	private File file;
	private PDDocument document;

	public PDFAnalyse(String path) {
		try {
			file = new File(path);
			document = PDDocument.load(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @method getPageCount
	 * @return page counts in pdf
	 */
	public int getPageCount() {
		return document.getNumberOfPages();
	}

	/**
	 * @method getFonts
	 * @return all fonts used in pdf
	 */
	// @SuppressWarnings("resource")
	// public String getFonts() {
	// Set<String> fontList = new HashSet<String>();
	// BufferedReader reader = null;
	// try {
	// FileInputStream fis = new FileInputStream(file);
	// reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));
	// } catch (FileNotFoundException e1) {
	// e1.printStackTrace();
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	// String line = null;
	// try {
	// while ((line = reader.readLine()) != null) {
	// if (line.contains("/ABCDEE+")) {
	// int prefix = line.indexOf("ABCDEE+");
	// int suffix = line.indexOf("/Encoding");
	// if (suffix < 0) {
	// continue;
	// }
	// String font = line.substring(prefix + 7, suffix);
	// fontList.add(font);
	// }
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return fontList.toString();
	// }

	public String getFonts() {
		Set<String> fontsSet = new HashSet<String>();
		PDPage page = null;

		for (int i = 0; i < document.getNumberOfPages(); i++) {
			page = (PDPage) document.getPrintable(i);
			if (page != null) {
				Map<String, PDFont> fontMap = page.getResources().getFonts();
				for (Entry<String, PDFont> entry : fontMap.entrySet()) {
					try {
						fontsSet.add(new String(entry.getValue().getBaseFont()
								.getBytes("ISO-8859-1"), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return fontsSet.toString();
	}

	public String report() {
		StringBuilder sb = new StringBuilder();
		sb.append("文档页数: " + getPageCount() + "\n");
		sb.append("文档使用的字体：" + getFonts());
		return sb.toString();
	}

}
