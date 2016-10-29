package business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
