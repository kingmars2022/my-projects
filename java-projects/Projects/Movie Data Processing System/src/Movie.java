import java.io.Serializable;
import java.util.Objects;

import Exceptions.*;

/**
 * This class represents a Movie object with various attributes such as year, title, duration, genres, rating, score, director, and actors.
 * It implements the Serializable interface to allow objects of this class to be serialized and deserialized.
 */

public class Movie implements Serializable {
	/**
	 * Enum representing different movie genres.
	 */
	public enum Genre {
		MUSICAL,
		COMEDY,
		ANIMATION,
		ADVENTURE,
		DRAMA,
		CRIME,
		BIOGRAPHY,
		HORROR,
		ACTION,
		DOCUMENTARY,
		FANTASY,
		MYSTERY,
		SCI_FI,
		FAMILY,
		ROMANCE,
		THRILLER,
		WESTERN
	}

	// Instance variables for each movie feature
	private int year;
	private String title;
	private int duration; // in minutes
	private Genre genres;
	private String rating;
	private double score;
	private String director;
	private String actor1;
	private String actor2;
	private String actor3;

	/**
	 * Default constructor.
	 * Initializes all instance variables with default values.
	 */
	public Movie() {
		this.year = 0;
		this.title = "";
		this.duration = 0;
		this.genres = null;
		this.rating = "";
		this.score = 0.0;
		this.director = "";
		this.actor1 = "";
		this.actor2 = "";
		this.actor3 = "";
	}
	/**
	 * Constructor with parameters.
	 * Initializes instance variables with the provided values.
	 *
	 * @param year     The release year of the movie.
	 * @param title    The title of the movie.
	 * @param duration The duration of the movie in minutes.
	 * @param genres   The genre of the movie.
	 * @param rating   The rating of the movie.
	 * @param score    The score of the movie.
	 * @param director The director of the movie.
	 * @param actor1   The first actor of the movie.
	 * @param actor2   The second actor of the movie.
	 * @param actor3   The third actor of the movie.
	 * @throws BadYearException      If the provided year is invalid.
	 * @throws BadTitleException     If the provided title is empty or null.
	 * @throws BadDurationException  If the provided duration is invalid.
	 * @throws BadGenreException     If the provided genre is invalid.
	 * @throws BadRatingException    If the provided rating is empty or null.
	 * @throws BadScoreException     If the provided score is invalid.
	 * @throws BadNameException      If the provided director or actor names are empty or null.
	 */
	public Movie(int year, String title, int duration, String genres, String rating,
				 double score, String director, String actor1, String actor2, String actor3) throws BadYearException,
			BadTitleException, BadDurationException, BadGenreException, BadRatingException, BadScoreException, BadNameException {
		this.setYear(year);
		this.setTitle(title);
		this.setDuration(duration);
		this.setGenres(Genre.valueOf(genres.toUpperCase()));
		this.setRating(rating);
		this.setScore(score);
		this.setDirector(director);
		this.setActor1(actor1);
		this.setActor2(actor2);
		this.setActor3(actor3);
	}
	/**
	 * Constructor to initialize from a String array.
	 * Initializes instance variables from the provided String array containing movie attributes.
	 *
	 * @param values The array of strings containing movie attributes.
	 * @throws BadSemanticException If any attribute is missing or invalid.
	 */
	public Movie(String[] values) throws BadSemanticException {

		// invalid year && missing year
		int year ;

		boolean hasError = false;
		String errorInfo = "";

		try {

			if (values[0] == null || values[0].trim().isEmpty()) {
				throw new BadYearException("missing year (" + values[0] + ")");
			} else {
				try {
					year = Integer.parseInt(values[0]);
				} catch (NumberFormatException e) {
					throw new BadYearException("invalid year (" + values[0] + ")");
				}
				this.setYear(year);
			}

		} catch (BadYearException e) {

			hasError = true;
			errorInfo += e.getLocalMessage() + ";" ;
		}

		// missing title
		String title = values[1];
		try {

			if (values[1] == null || values[1].trim().isEmpty()) {

				throw new BadTitleException("missing title (" + values[1] + ")");

			} else this.setTitle(title);

		} catch (BadTitleException e) {

			hasError = true;
			errorInfo += e.getLocalMessage() + ";" ;
		}

		// missing duration && invalid duration
		int duration ;

		try {
			if (values[2] == null || values[2].trim().isEmpty()) {
				throw new BadDurationException("missing duration (" + values[2] + ")");
			} else {

				try {
					duration = Integer.parseInt(values[2]);
				} catch (NumberFormatException e) {
					throw new BadDurationException("missing duration (" + values[2] + ")");
				}

				this.setDuration(duration);
			}

		} catch (BadDurationException e) {

			hasError = true;
			errorInfo += e.getLocalMessage() + ";" ;
		}

		// missing genres && invalid genres
		String genres = values[3];

		try {
			if (values[3] == null || values[3].trim().isEmpty()) {

				hasError = true;
				errorInfo += "; missing Genre (" + values[3] + ")";

				throw new BadGenreException("missing Genre (" + values[3] + ")");

			} else {
				try {
					if (genres.equalsIgnoreCase("sci-fi")) {
						this.setGenres(Genre.SCI_FI);
					} else this.setGenres(Genre.valueOf(genres.toUpperCase()));

				} catch (IllegalArgumentException e) {

					throw new BadGenreException("invalid Genre (" + values[3] + ")");
				}
			}
		} catch (BadGenreException e) {

			hasError = true;
			errorInfo += e.getLocalMessage() + ";" ;
		}

		// missing rating && invalid rating
		String rating = values[4];
		try {
			if (values[4] == null || values[4].trim().isEmpty()) {

				throw new BadRatingException("missing rating (" + values[4] + ")");

			} else this.setRating(rating);

		} catch (BadRatingException e) {

			hasError = true;
			errorInfo += e.getLocalMessage() + ";" ;
		}

		// missing score && invalid score
		double score ;
		try {
			if (values[5] == null || values[5].trim().isEmpty()) {

				throw new BadScoreException("missing score (" + values[5] + ")");

			} else {
				try {
					score = Double.parseDouble(values[5]);
				} catch (NumberFormatException e) {

					throw new BadScoreException("invalid score (" + values[5] + ")");
				}
				this.setScore(score);
			}
		} catch (BadScoreException e) {

			hasError = true;
			errorInfo += e.getLocalMessage() + ";" ;
		}

		// missing director's name
		String director = values[6];
		try {
			if (values[6] == null || values[6].trim().isEmpty()) {

				throw new BadNameException("missing director's name (" + values[6] + ")");

			} else this.setDirector(director);

		} catch (BadNameException e) {

			hasError = true;
			errorInfo += e.getLocalMessage() + ";" ;
		}

		// missing actor1's name
		String actor1 = values[7];
		try {
			if (values[7] == null || values[7].trim().isEmpty()) {

				throw new BadNameException("missing actor1's name (" + values[7] + ")");

			} else this.setActor1(actor1);
		} catch (BadNameException e) {
			hasError = true;
			errorInfo += e.getLocalMessage() + ";" ;
		}

		// missing actor2's name
		String actor2 = values[8];
		try {
			if (values[8] == null || values[8].trim().isEmpty()) {

				throw new BadNameException("missing actor2's name (" + values[8] + ")");

			} else this.setActor2(actor2);
		} catch (BadNameException e) {
			hasError = true;
			errorInfo += e.getLocalMessage() + ";" ;
		}

		// missing actor3's name
		String actor3 = values[9];
		try {
			if (values[9] == null || values[9].trim().isEmpty()) {

				throw new BadNameException("missing actor3's name (" + values[9] + ")");

			} else this.setActor3(actor3);
		} catch (BadNameException e) {
			hasError = true;
			errorInfo += e.getLocalMessage() + ";" ;
		}

		if (hasError) {

			throw new BadSemanticException(errorInfo);

		}

	}

	// Accessor and mutator methods for each variable

	/**
	 * Gets the year of the movie.
	 *
	 * @return The year of the movie.
	 */

	public int getYear() {
		return year;
	}

	/**
	 * Sets the year of the movie.
	 *
	 * @param year The year of the movie.
	 * @throws BadYearException
	 */

	public void setYear(int year) throws BadYearException {

		if (year < 1990 || year > 1999) {
			throw new BadYearException("invalid year: " + year);
		}

		this.year = year;
	}

	/**
	 * Gets the title of the movie.
	 *
	 * @return The title of the movie.
	 */

	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the movie.
	 *
	 * @param title The title of the movie.
	 * @throws BadTitleException
	 */

	public void setTitle(String title) throws BadTitleException {

		this.title = title;
	}

	/**
	 * Gets the duration of the movie.
	 *
	 * @return The duration of the movie.
	 */

	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration of the movie.
	 *
	 * @param duration The duration of the movie.
	 * @throws BadDurationException
	 */

	public void setDuration(int duration) throws BadDurationException {
		if (duration > 300 || duration < 30) {
			throw new BadDurationException("invalid duration: " + duration);
		}

		this.duration = duration;
	}

	/**
	 * Gets the genres of the movie.
	 *
	 * @return The genres of the movie.
	 */

	public Genre getGenres() {
		return genres;
	}

	/**
	 * Sets the genres of the movie.
	 *
	 * @param genres The genres of the movie.
	 * @throws BadGenreException
	 */

	public void setGenres(Genre genres) throws BadGenreException {
		this.genres = genres;
	}

	/**
	 * Gets the rating of the movie.
	 *
	 * @return The rating of the movie.
	 */

	public String getRating() {
		return rating;
	}

	/**
	 * Sets the rating of the movie.
	 *
	 * @param rating The rating of the movie.
	 * @throws BadRatingException
	 */

	public void setRating(String rating) throws BadRatingException {
		if (!(rating.equalsIgnoreCase("PG") || rating.equalsIgnoreCase("UNRATED")
				|| rating.equalsIgnoreCase("G") || rating.equalsIgnoreCase("R")
				|| rating.equalsIgnoreCase("PG-13") || rating.equalsIgnoreCase("NC-17"))) {

			throw new BadRatingException("invalid rating: " + rating);
		}

		this.rating = rating;
	}

	/**
	 * Gets the score of the movie.
	 *
	 * @return The score of the movie.
	 */

	public double getScore() {
		return score;
	}

	/**
	 * Sets the score of the movie.
	 *
	 * @param score The score of the movie.
	 * @throws BadScoreException
	 */

	public void setScore(double score) throws BadScoreException {

		if (score > 10 || score < 0) {
			throw new BadScoreException("invalid score: " + score);
		}

		this.score = score;
	}

	/**
	 * Gets the director of the movie.
	 *
	 * @return The director of the movie.
	 */

	public String getDirector() {
		return director;
	}

	/**
	 * Sets the director of the movie.
	 *
	 * @param director The director of the movie.
	 * @throws BadNameException
	 */

	public void setDirector(String director) throws BadNameException {
		this.director = director;
	}

	/**
	 * Gets the actor1 of the movie.
	 *
	 * @return The actor1 of the movie.
	 */

	public String getActor1() {
		return actor1;
	}

	/**
	 * Sets the actor1 of the movie.
	 *
	 * @param actor1 The actor1 of the movie.
	 * @throws BadNameException
	 */

	public void setActor1(String actor1) throws BadNameException {
		this.actor1 = actor1;
	}

	/**
	 * Gets the actor2 of the movie.
	 *
	 * @return The actor2 of the movie.
	 */

	public String getActor2() {
		return actor2;
	}

	/**
	 * Sets the actor2 of the movie.
	 *
	 * @param actor2 The actor2 of the movie.
	 * @throws BadNameException
	 */

	public void setActor2(String actor2) throws BadNameException {
		this.actor2 = actor2;
	}

	/**
	 * Gets the actor3 of the movie.
	 *
	 * @return The actor3 of the movie.
	 */

	public String getActor3() {
		return actor3;
	}

	/**
	 * Sets the actor3 of the movie.
	 *
	 * @param actor3 The actor3 of the movie.
	 * @throws BadNameException
	 */

	public void setActor3(String actor3) throws BadNameException {
		this.actor3 = actor3;
	}

	// equals method

	/**
	 * Compares this movie to the specified object. The result is true if and only if the argument is not null and
	 * is a Movie object that has the same properties as this object.
	 *
	 * @param obj the object to compare this Movie against
	 * @return true if the given object represents a Movie equivalent to this movie, false otherwise
	 */

	public boolean equals(Object obj) {

		if (this == obj) return true;
		if (!(obj instanceof Movie)) return false;
		Movie movie = (Movie) obj;
		return year == movie.year &&
				duration == movie.duration &&
				rating.equals(movie.rating) &&
				score == movie.score &&
				title.equals(movie.title) &&
				Objects.equals(genres, movie.genres) &&
				director.equals(movie.director) &&
				actor1.equals(movie.actor1) &&
				actor2.equals(movie.actor2) &&
				actor3.equals(movie.actor3);
	}

	// toString() method

	/**
	 * Returns a string representation of this Movie object, which includes all attributes of the movie.
	 *
	 * @return a string representation of this movie
	 */
	public String toString() {
		String genre = Capitalize(this.genres.toString());

		String formattedTitle = title.contains(",") ? "\"" + title + "\"" : title;
		String formattedDirector = director.contains(",") ? "\"" + director + "\"" : director;
		String formattedActor1 = actor1.contains(",") ? "\"" + actor1 + "\"" : actor1;
		String formattedActor2 = actor2.contains(",") ? "\"" + actor2 + "\"" : actor2;
		String formattedActor3 = actor3.contains(",") ? "\"" + actor3 + "\"" : actor3;

		return year + "," + formattedTitle + "," + duration + "," + genre + "," + rating + "," + score + "," +
				formattedDirector + "," + formattedActor1 + "," + formattedActor2 + "," + formattedActor3;

	}

	public static String Capitalize(String str){
		return str.substring(0,1).toUpperCase()+str.substring(1).toLowerCase();
	}
}
