package com.example.android.booklisting;

/**
 * Created by tzouanakos on 13/03/2017.
 */

public class Book {

    private String mTitle;
    private String mAuthor;
    private String mInfoLink;

    public Book(String mTitle, String mAuthor, String infoLink) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mInfoLink = infoLink;
    }

    /**
     * Gets the title of a book
     *
     * @return mTitle
     */
    public String getmTitle() {
        return mTitle;
    }

    /**
     * Gets the author of a book
     *
     * @return mAuthor
     */
    public String getmAuthor() {
        return mAuthor;
    }

    /**
     * Gets the info link
     *
     * @return mInfoLink
     */
    public String getmInfoLink() {
        return mInfoLink;
    }
}
