package org.mashup.entertainment.model;

import com.google.common.base.Objects;

public class Movie {
	private String title;
	private String director;
	private String year;
	private String type;
	
	public Movie(String title, String director, String year) {
		this.title = title;
		this.director = director;
		this.year = year;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((director == null) ? 0 : director.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		
		return Objects.equal(director, other.director) 
				&& Objects.equal(title, other.title)
				&& Objects.equal(type, other.type) 
				&& Objects.equal(year, other.year);
	}

	@Override
	public String toString() {
		return "Movie [title=" + title + ", director=" + director + ", year=" + year + ", type=" + type + "]";
	}	
	
}
