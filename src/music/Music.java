package music;

public enum Music{
	THEME_MUSIC("theme_music.wav"), TENSE_MUSIC("tense_music.wav"), BOOM("boom.wav");
	
	private String fileName;
	
	private Music(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return this.fileName;
	}
}
