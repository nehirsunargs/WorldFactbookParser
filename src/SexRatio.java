import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;

public class SexRatio {

    public static String findMostDisproportionateBorderCountry(String countryUrl) {
        String borderInfo = BorderCountries.findBorderInfo(countryUrl);
    
        String[] borderCountriesInfo = borderInfo.split(";");
        List<String> borderCountryNames = new ArrayList<>();
    
        for (String borderCountryInfo : borderCountriesInfo) {
            String countryName = borderCountryInfo.replaceAll("\\(.*?\\)", "") 
            .replaceAll("\\d+", "")       
            .replaceAll("km", "")        
            .replaceAll(",", "")          
            .replaceAll("\\s{2,}", " ")  
            .replaceAll("and China", "")  
            .replaceAll("North Korea", "Korea, North") 
            .trim();
            borderCountryNames.add(countryName);
        }
    
        String mostDisproportionateCountry = "";
        double maxDisproportion = Double.MIN_VALUE;
    
        String url = "https://www.cia.gov/the-world-factbook/countries/";
        MainPage mainPage = new MainPage(url);
        List<Country> countries = mainPage.getContinentInfo();
    
        for (String borderCountryName : borderCountryNames) {
            String borderCountryUrl = null;
            for (Country country : countries) {
                if (country.getName().equalsIgnoreCase(borderCountryName)) {
                    borderCountryUrl = country.getUrl();
                    break;
                }
            }
    
            if (borderCountryUrl == null) {
                System.out.println("URL not found for border country: " + borderCountryName);
                continue;
            }
    
            double sexRatio = getSexRatio(borderCountryUrl);
    
            if (sexRatio > maxDisproportion) {
                maxDisproportion = sexRatio;
                mostDisproportionateCountry = borderCountryName;
            }
        }
    
        return mostDisproportionateCountry;
    }    

    public static double getSexRatio(String countryUrl) {
        double sexRatio = 0.0;
        try {
            Document doc = Jsoup.connect(countryUrl).get();
            Element sexRatioElement1 = doc.selectFirst("#people-and-society > div:nth-child(18) > p");
            Element sexRatioElement2 = doc.selectFirst("#people-and-society > div:nth-child(17) > p");
            
            String sexRatioText = null;
            if (sexRatioElement1 != null && sexRatioElement1.text().contains("total population:")) {
                sexRatioText = sexRatioElement1.text();
            } else if (sexRatioElement2 != null && sexRatioElement2.text().contains("total population:")) {
                sexRatioText = sexRatioElement2.text();
            }
    
            if (sexRatioText != null) {
                sexRatio = parseSexRatio(sexRatioText);
            } else {
                System.out.println("Sex ratio information not found for " + countryUrl);
            }
        } catch (IOException e) {
            System.out.println("Error fetching sex ratio information for " + countryUrl);
            e.printStackTrace();
        }
        return sexRatio;
    }
    
    private static double parseSexRatio(String sexRatioText) {
        String[] parts = sexRatioText.split("\\s+");
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].matches("\\d+(\\.\\d+)?")) {
                double ratio = Double.parseDouble(parts[i]);
                if (i + 1 < parts.length && parts[i + 1].matches("male\\(s\\)/female.*")) {
                    return ratio;
                } else if (i + 2 < parts.length && parts[i + 2].matches("male\\(s\\)/female.*")) {
                    return ratio;
                } else if (i + 1 < parts.length && parts[i + 1].matches("female/male.*")) {
                    return 1 / ratio; 
                } else if (i + 2 < parts.length && parts[i + 2].matches("female/male.*")) {
                    return 1 / ratio; // invert the ratio for "female/male" format
                }
            }
        }
        System.out.println("Sex ratio format not recognized: " + sexRatioText);
        return 0.0;
    }
    

    //test function
    public static void main(String[] args) {
        String countryUrl = "https://www.cia.gov/the-world-factbook/countries/russia/";
        String mostDisproportionateBorderCountry = findMostDisproportionateBorderCountry(countryUrl);
        System.out.println("Most Disproportionate Border Country: " + mostDisproportionateBorderCountry);
    }
}

