import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node; 
import java.io.IOException;
import java.util.List;

public class BorderCountries {
    public static String findBorderInfo(String countryUrl) {
        String borderInfo = "";
        try {
            Document doc = Jsoup.connect(countryUrl).get();
            Element strongElement = doc.selectFirst("strong:contains(border countries)");
            if (strongElement != null) {
                Node sibling = strongElement.nextSibling();
                while (sibling != null) {
                    if (sibling.nodeName().equals("#text")) {
                        borderInfo = sibling.toString();
                        break;
                    }
                    sibling = sibling.nextSibling();
                }
            }
        } catch (IOException e) {
            System.out.println("Error fetching border information for " + countryUrl);
            e.printStackTrace();
        }
        return borderInfo;
    }

    public static int countBorderCountries(String borderInfo) {
        return borderInfo.split(";").length;
    }

    public static Country getCountryWithLeastBorderCountries(String continentName) {
        String url = "https://www.cia.gov/the-world-factbook/countries/";
        MainPage mainPage = new MainPage(url);
        List<Country> countries = mainPage.getContinentInfo();
        Country countryWithLeastBorders = null;
        int minBorderCount = Integer.MAX_VALUE;
    
        for (Country country : countries) {
            if (country.getContinent().equalsIgnoreCase(continentName)) {
                String countryUrl = country.getUrl();
                String countryName = country.getName();
                String borderInfo = findBorderInfo(countryUrl);
                int borderCount = countBorderCountries(borderInfo);
    
                if (borderCount < minBorderCount) {
                    minBorderCount = borderCount;
                    countryWithLeastBorders = new Country(countryName, countryUrl, continentName);
                }
            }
        }
    
        return countryWithLeastBorders;
    }


    public static void main(String[] args) {
        String baseUrl = "https://www.cia.gov/the-world-factbook/countries/algeria";
        String result = findBorderInfo(baseUrl);
        int count = countBorderCountries(result);
        System.out.println(result);
        System.out.println(count);
    }
}
