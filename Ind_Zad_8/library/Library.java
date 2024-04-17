package library;

import java.io.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Library implements Serializable {
	// class release version:
	private static final long serialVersionUID = 1L;
	// areas with prompts:
	String ticketNumber;
	public static final String P_ticketNumber = "TicketNumber";
	String readerName;
	public static final String P_readerName = "ReaderName";
	String issueDate;
	public static final String P_issueDate = "IssueDate";
	int returnDays;
	public static final String P_returnDays = "ReturnDays";
	String author;
	public static final String P_author = "Author";
	String title;
	public static final String P_title = "Title";
	int publicationYear;
	public static final String P_publicationYear = "PublicationYear";
	String publisher;
	public static final String P_publisher = "Publisher";
	double price;
	public static final String P_price = "Price";
	String returnDate;
	public static final String P_returnDate = "ReturnDate";

	public static boolean checkDate(String var0) {
		String var1 = "[ .0-9]+";
		return var0.matches(var1);
	}

	public static boolean checkName(String var0) {
		String var1 = "[ _A-Za-z]+";
		return var0.matches(var1);
	}

	private static GregorianCalendar curCalendar = new GregorianCalendar();

	static Boolean validYear(int year) {
		return year > 0 && year <= curCalendar.get(Calendar.YEAR);
	}

	// validation methods:
	static Boolean validTN(String str) {
		return (str.length() >= 6 && str.length() <= 8);
	}

	public static boolean nextRead(Scanner fin, PrintStream out) {
		return nextRead(P_ticketNumber, fin, out);
	}

	static boolean nextRead(final String prompt, Scanner fin, PrintStream out) {
		out.print(prompt);
		out.print(": ");
		return fin.hasNextLine();
	}

	public static final String readerDel = ",";

	public static Library read(Scanner fin, PrintStream out) throws IOException,
			NumberFormatException {
		String str;
		Library book = new Library();
		book.ticketNumber = fin.nextLine().trim();
		if (Library.validTN(book.ticketNumber) == false) {
			throw new IOException("Invalid TicketNumber: " + book.ticketNumber);
		}
		if (!nextRead(P_readerName, fin, out)) {
			return null;
		}
		book.readerName = fin.nextLine();
		if (!nextRead(P_issueDate, fin, out)) {
			return null;
		}
		str = fin.nextLine().trim();
		book.issueDate = str;
		if (Library.checkDate(book.issueDate) == false) {
			throw new IOException("Invalid issueDate value");
		}
		if (!nextRead(P_returnDays, fin, out)) {
			return null;
		}
		str = fin.nextLine().trim();
		book.returnDays = Integer.parseInt(str);
		if (!nextRead(P_author, fin, out)) {
			return null;
		}
		book.author = fin.nextLine().trim();
		if (Library.checkName(book.author) == false) {
			throw new IOException("Invalid Author value");
		}
		if (!nextRead(P_title, fin, out)) {
			return null;
		}
		book.title = fin.nextLine();
		if (!nextRead(P_publicationYear, fin, out)) {
			return null;
		}
		str = fin.nextLine();
		book.publicationYear = Integer.parseInt(str);
		if (Library.validYear(book.publicationYear) == false) {
			throw new IOException("Invalid PublicationYear value");
		}
		if (!nextRead(P_publisher, fin, out)) {
			return null;
		}
		book.publisher = fin.nextLine();
		if (!nextRead(P_price, fin, out)) {
			return null;
		}
		str = fin.nextLine();
		book.price = Double.parseDouble(str);

		if (!nextRead(P_returnDate, fin, out)) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date issueDate = null;
		try {
			issueDate = dateFormat.parse(book.issueDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar issueCalendar = Calendar.getInstance();
		issueCalendar.setTime(issueDate);
		Calendar returnCalendar = Calendar.getInstance();
		returnCalendar.setTime(issueDate);
		returnCalendar.add(Calendar.DAY_OF_YEAR, book.returnDays);
		book.returnDate = returnCalendar.getTime().toString();
		return book;
	}

	public Library() {
	}

	public Library(String strBook) {
		String [] rows = strBook.split(Library.areaDel);
		if (rows.length != 7) {
			throw new IllegalArgumentException("Illegal source string for Book");
		}
		setTicketNumber(rows[0]);
		setReaderName(rows[1]);
		setIssueDate(rows[2]);
		setReturnDays(Integer.parseInt(rows[3]));
		setTitle(rows[4]);
		setPublicationYear(rows[5]);
		setPublisher(rows[6]);
		setPrice(rows[7]);
		setReturnDate(rows[8]);
	}

	public static final String areaDel = "\n";

	public String toString() {
		return new String(
			ticketNumber + areaDel +
			readerName + areaDel +
			issueDate + areaDel +
			returnDays + areaDel +
			author + areaDel +
			title + areaDel +
			publicationYear + areaDel +
			publisher + areaDel +
			price + areaDel +
			returnDate
			);
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public final void setTicketNumber(String strTicketNumber) {
		if (!validTN(strTicketNumber)) {
			throw new IllegalArgumentException("Illegal Ticket Number");
		}
		this.ticketNumber = strTicketNumber;
	}

	public String getReaderName() {
		return readerName;
	}

	public final void setReaderName(String readerName) {
		if (readerName == null || readerName.isEmpty()) {
			throw new IllegalArgumentException("Illegal Reader Name");
		}
		this.readerName = readerName;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public final void setIssueDate(String issueDate) {
		if (issueDate == null || issueDate.isEmpty() || !Library.checkDate(issueDate)) {
			throw new IllegalArgumentException("Illegal Issue Date");
		}
		this.issueDate = issueDate;
	}

	public int getReturnDays() {
		return returnDays;
	}

	public final void setReturnDays(int returnDays) {
		if (returnDays < 0) {
			throw new IllegalArgumentException("Illegal return days");
		}
		this.returnDays = returnDays;
	}

	public String getAuthor() {
		return author;
	}

	public final void setAuthor(String author) {
		if (author == null || author.isEmpty() || !Library.checkName(author)) {
			throw new IllegalArgumentException("Illegal author");
		}
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("Illegal title");
		}
		this.title = title;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public final void setPublicationYear(String publicationYear) {
		boolean isError = false;
		int y = 0;
		try {
			y = Integer.parseInt(publicationYear);
		} catch (Error | Exception e) {
			isError = true;
		}
		if (isError || !validYear(y)) {
			throw new IllegalArgumentException("Illegal publication year");
		}
		this.publicationYear = y;
	}

	public String getPublisher() {
		return publisher;
	}

	public final void setPublisher(String publisher) {
		if (publisher == null || publisher.isEmpty()) {
			throw new IllegalArgumentException("Illegal publisher");
		}
		this.publisher = publisher;
	}

	public double getPrice() {
		return price;
	}

	public final void setPrice(String strPrice) {
		boolean isError = false;
		double p = 0;
		try {
			p = Double.parseDouble(strPrice);
		} catch (Error | Exception e) {
			isError = true;
		}
		if (isError || p <= 0) {
			throw new IllegalArgumentException("Illegal price");
		}
		this.price = p;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public final void setReturnDate(String returnDate) {
		//if (returnDate == null || returnDate.isEmpty()) {
		//	throw new IllegalArgumentException("Illegal return date");
		//}
		this.returnDate = returnDate;
	}
}
