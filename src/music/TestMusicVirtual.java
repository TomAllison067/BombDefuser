package music;
public class TestMusicVirtual {
	public static void main(String[] args) {
		MusicContainer music = new MusicContainer();
		music.setMusic(Music.THEME_MUSIC);
		music.playMusic(10000);
	}
}
