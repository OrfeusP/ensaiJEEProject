package business;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class RProgram {
	@Id
	@GeneratedValue
	long id;

	String filename;
	
	@Column(length=2000)
	String programresult;
	
	String author;
	String programcode;

	String lastModified;
	String dateCreated;


	@PreUpdate
	@PrePersist
	public void updateTimeStamps() {
		SimpleDateFormat sdfr = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		lastModified = sdfr.format( new Date() );
		if (dateCreated==null) {
			dateCreated = sdfr.format(new Date());
		}
	}
	public String getLastModified(){ return this.lastModified; }
	public String getDateCreated(){ return this.dateCreated; }

	
	public String getResult() {
		return programresult;
	}
	public void setResult(String result) {
		this.programresult = result;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return filename;
	}
	public void setName(String name) {
		this.filename = name;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getProgramCode() {
		return programcode;
	}
	public void setProgram(String program) {
		this.programcode = program;
	}
	

}
