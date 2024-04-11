import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Country {
    private String name;
    private String url;
    private String continent;
    private List<String> plugTypes;

    private static Map<String, String> countryUrlMap = new HashMap<>();

    public Country(String name, String url, String continent) {
        this.name = name;
        this.url = url;
        this.continent = continent;
        countryUrlMap.put(name, url);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public static String getUrl(String countryName) {
        return countryUrlMap.get(countryName);
    }

    public String getContinent() {
        return continent;
    }

    public List<String> getPlugTypes() {
            return plugTypes;
        }

    public double getLargestCoastline() {
        try {
            Document doc = Jsoup.connect(url).get();
            Element coastlineElement = doc.selectFirst("#geography > div:nth-child(8) > p");
            if (coastlineElement != null) {
                String coastlineText = coastlineElement.text();
                Pattern pattern = Pattern.compile("\\d{1,3}(,\\d{3})*(\\.\\d+)?");
                Matcher matcher = pattern.matcher(coastlineText);
                if (matcher.find()) {
                    String coastlineInfo = matcher.group();
                    return Double.parseDouble(coastlineInfo.replace(",", ""));
                }
            }
        } catch (IOException e) {
            System.out.println("Error fetching coastline for " + name);
            e.printStackTrace();
        }
        return -1; 
    }
} 
