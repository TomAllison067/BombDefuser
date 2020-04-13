package music;

/**
 * Simple enum to represent the different music choices
 * 
 * @author Tom
 *
 */
public enum Music {
	THEME_MUSIC("theme_music.wav"), TENSE_MUSIC("tense_music.wav"), BOOM("boom.wav");

	private String fileName;

	/**
	 * Gives each enum member the filename of an audio file
	 * 
	 * @param fileName
	 */
	private Music(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Returns the member's audio filename
	 * 
	 * @return the filename
	 */
	public String getFileName() {
		return this.fileName;
	}
}
