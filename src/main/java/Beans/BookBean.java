package Beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by david on 03/05/2017.
 */
public class BookBean implements Serializable{

    private String publicationType, publicationDate, venues, year, title, pages, journal, url, ee, price, picture;
    private int id;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    private int active;

    private ArrayList<String> author;

    @Override
    public String toString() {
        return "BookBean{" +
                "publicationType='" + publicationType + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", venues='" + venues + '\'' +
                ", year='" + year + '\'' +
                ", title='" + title + '\'' +
                ", pages='" + pages + '\'' +
                ", journal='" + journal + '\'' +
                ", url='" + url + '\'' +
                ", ee='" + ee + '\'' +
                ", price='" + price + '\'' +
                ", picture='" + picture + '\'' +
                ", id=" + id +
                ", author=" + author.toString() +
                '}';
    }

    public ArrayList<String> getAuthor() {
        return author;
    }

    public void setAuthor(ArrayList<String> author) {
        this.author = author;
    }

    public BookBean(){
    }

    public BookBean(String publicationType, String publicationDate, String venues,
                String year, String title, String pages, String journal,
                String url, String ee, String price, String picture, ArrayList<String> author){

        this.publicationType = publicationType;
        this. publicationDate = publicationDate;
        this.venues = venues;
        this.year = year;
        this.title = title;
        this.pages = pages;
        this.journal = journal;
        this.url = url;
        this.ee =  ee;
        this.price =  price;
        this.picture = picture;
        this.author = author;
    }

    public String getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(String publicationType) {
        this.publicationType = publicationType;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getVenues() {
        return venues;
    }

    public void setVenues(String venues) {
        this.venues = venues;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEe() {
        return ee;
    }

    public void setEe(String ee) {
        this.ee = ee;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
