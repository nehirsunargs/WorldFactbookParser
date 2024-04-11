import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPage {
    String url;

    public MainPage(String url) {
        this.url = url;
    }

    public List<Country> getContinentInfo() {
        List<Country> countryList = new ArrayList<>();
    
        // manual list of continents and their URLs
        String[] continents = {
                "Africa", "https://www.cia.gov/the-world-factbook/africa/",
                "Antarctica", "https://www.cia.gov/the-world-factbook/antarctica/",
                "Australia and Oceania", "https://www.cia.gov/the-world-factbook/australia-and-oceania/",
                "Central America and the Caribbean", "https://www.cia.gov/the-world-factbook/central-america-and-the-caribbean/",
                "Central Asia", "https://www.cia.gov/the-world-factbook/central-asia/",
                "East and Southeast Asia", "https://www.cia.gov/the-world-factbook/east-and-southeast-asia/",
                "Europe", "https://www.cia.gov/the-world-factbook/europe/",
                "Middle East", "https://www.cia.gov/the-world-factbook/middle-east/",
                "North America", "https://www.cia.gov/the-world-factbook/north-america/",
                "South America", "https://www.cia.gov/the-world-factbook/south-america/",
                "South Asia", "https://www.cia.gov/the-world-factbook/south-asia/"
        };
    
        for (int i = 0; i < continents.length; i += 2) {
            String continentName = continents[i];
            String continentUrl = continents[i + 1];
    
            try {
                Document doc = Jsoup.connect(continentUrl).get();
    
                Elements countryElements = doc.select("#main-content > section.background-white.three-col-links-wrapper > div > div > div > ul > li > h5 > a");
    
                for (Element countryElement : countryElements) {
                    String countryName = countryElement.text();
                    String countryUrl = countryElement.attr("abs:href"); 
                    countryList.add(new Country(countryName, countryUrl, continentName));
                }
            } catch (IOException e) {
                System.out.println("Error connecting to " + continentName + " page.");
                e.printStackTrace();
            }
        }
    
        return countryList;
    }
    

    public static class ContinentInfo {
        private String name;
        private String url;
        private List<String> countries;

        public ContinentInfo(String name, String url, List<String> countries) {
            this.name = name;
            this.url = url;
            this.countries = countries;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public List<String> getCountries() {
            return countries;
        }

        
    }
}
