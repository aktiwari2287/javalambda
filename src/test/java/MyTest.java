import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;

import com.insightfullogic.java8.examples.chapter1.Album;
import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.examples.chapter1.SampleData;
import com.insightfullogic.java8.examples.chapter1.Track;

public class MyTest {
	public Set<String> findLongTracks(List<Album> albums) {
		Set<String> trackNames = new HashSet<>();
		for (Album album : albums) {
			for (Track track : album.getTrackList()) {
				if (track.getLength() > 60) {
					String name = track.getName();
					trackNames.add(name);
				}
			}
		}
		return trackNames;
	}

	public Set<String> findLongTracksRefactor1(List<Album> albums) {
		Set<String> trackNames = new HashSet<>();
		albums.forEach(album -> {
			album.getTrackList().forEach(track -> {
				if (track.getLength() > 60) {
					String name = track.getName();
					trackNames.add(name);
				}
			});
		});
		return trackNames;
	}

	public Set<String> findLongTracksRefactor2(List<Album> albums) {
		Set<String> trackNames = new HashSet<>();
		albums.forEach(album -> {
			trackNames.addAll(album.getTrackList().stream().filter(track -> track.getLength() > 60)
					.map(track -> track.getName()).collect(Collectors.toSet()));
		});
		return trackNames;
	}

	public Set<String> findLongTracksRefactor3(List<Album> albums) {
		return albums.stream().flatMap(album -> album.getTrackList().stream()).filter(track -> track.getLength() > 60)
				.map(track -> track.getName()).collect(Collectors.toSet());
	}

	@Test
	public void countingTheNumberOfAlbumsForEachArtist() {
		List<Artist> artists = SampleData.getThreeArtists();
		Map<Artist, Integer> map = new HashMap<Artist, Integer>();
		for (Artist artist : artists) {
			int count = 0;
			for (Album album : SampleData.getAllAlbums()) {
				if (album.getMusicianList().contains(artist)) {
					count++;
				}
			}
			map.put(artist, count);
		}

		System.out.println(map);
		
	}
	

	
	
	@Test
	public void countingTheNumberOfAlbumsForEachArtistRefactor1() {
		Map<Artist, List<Album>> albumsByArtist = SampleData.getAllAlbums().stream().collect(Collectors.groupingBy(album -> album.getMainMusician()));
		Map<Artist, Integer> numberOfAlbums = new HashMap<>();
		for (Entry<Artist, List<Album>> entry : albumsByArtist.entrySet()) {
			numberOfAlbums.put(entry.getKey(), entry.getValue().size());
		}
		System.out.println(numberOfAlbums);
	}

	@Test
	@Ignore
	public void test() {
		System.out.println(findLongTracks(SampleData.getAllAlbums()));
		System.out.println(findLongTracksRefactor1(SampleData.getAllAlbums()));
		System.out.println(findLongTracksRefactor2(SampleData.getAllAlbums()));
		System.out.println(findLongTracksRefactor3(SampleData.getAllAlbums()));
	}

}
