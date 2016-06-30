package com.androidexample.moviemanager;

public class Movie {
	
	private String title;
	private String link;
	private String image;
	private String subtitle;
	private String pubDate;
	private String director;
	private String actor;
	private float userRating;//3.5 이렇게 넘어오므로
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public float getUserRating() {
		return userRating;
	}
	public void setUserRating(Float userRating) {
		this.userRating = userRating;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}

	@Override
	public String toString() {//어떤 것을 출력해야할지 모를 때 toString 을 호출한다.
		return String.format("[%s][%s][%s][%f]", title, subtitle, director, userRating);
	}
}
