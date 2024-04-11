import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.select.Elements;

public class Population {

    public static Country getCountryWithLargestCoastline(List<Country> countries, String continent) {
        Country countryWithLargestCoastline = null;
        double maxCoastline = Double.MIN_VALUE;

        for (Country country : countries) {
            if (country.getContinent().equals(continent)) {
                double coastline = country.getLargestCoastline();
                if (coastline > maxCoastline) {
                    maxCoastline = coastline;
                    countryWithLargestCoastline = country;
                }
            }
        }

        return countryWithLargestCoastline;
    }

    public static double getPercentagePopulationOver65(Country country) {
        double percentage = 0.0;
        try {
            Document doc = Jsoup.connect(country.getUrl()).get();
            Elements paragraphs = doc.select("#people-and-society > div > p");
    
            for (Element paragraph : paragraphs) {
                String paragraphText = paragraph.text();
                if (paragraphText.contains("65 years and over:")) {
                    String pattern = "(\\d+\\.\\d+)%\\s*\\(\\d{4} est\\.\\)";
                    Pattern r = Pattern.compile(pattern);
                    Matcher m = r.matcher(paragraphText);
                    if (m.find()) {
                        percentage = Double.parseDouble(m.group(1));
                        System.out.println("Percentage of population older than 65: " + percentage);
                        break; 
                    }
                }
            }
    
            if (percentage == 0.0) {
                System.out.println("Percentage of population older than 65 not found.");
            }
        } catch (IOException e) {
            System.out.println("Error fetching population data for " + country.getName());
            e.printStackTrace();
        }
        return percentage;
    }
    
    

    public static void main(String[] args) {
        String continentUrl = "https://www.cia.gov/the-world-factbook/south-america/";
        MainPage mainPage = new MainPage(continentUrl);
        List<Country> countries = mainPage.getContinentInfo();
        Country countryWithLargestCoastline = Population.getCountryWithLargestCoastline(countries, "South America");

        if (countryWithLargestCoastline != null) {
            System.out.println("Country with the largest coastline in South America: " + countryWithLargestCoastline.getName());
            System.out.println("Coastline: " + countryWithLargestCoastline.getLargestCoastline() + " km");

            double percentagePopulationOver65 = Population.getPercentagePopulationOver65(countryWithLargestCoastline);
            System.out.println("Percentage of population older than 65: " + percentagePopulationOver65 + "%");
        } else {
            System.out.println("No country found for the given continent.");
        }
    }

}
