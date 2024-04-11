import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PlugType {
    public List<String> getPlugTypes(String countryUrl) {
        List<String> plugTypes = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(countryUrl + "travel-facts").get();
            Element plugTypesElement = doc.selectFirst("#main-content > section.content-section-mobile.background-white > div > div > div.col-lg-6.col-md-12.col-sm-12.border-left-black.pt60.content-area-content.col-lg-9.pb30.pt0 > span:nth-child(11) > p:contains(plug types(s):)");
            if (plugTypesElement != null) {
                String plugInfo = plugTypesElement.text();
                String[] parts = plugInfo.split("plug types\\(s\\):")[1].trim().split(",");
                for (String part : parts) {
                    plugTypes.add(part.trim());
                }
            } else {
                System.out.println("Plug types information not found for the country.");
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return plugTypes;
    }

    public void findCountriesWithDifferentPlugTypesToUSA(String continent) {
        String url = "https://www.cia.gov/the-world-factbook/countries/";
        MainPage mainPage = new MainPage(url);
        List<Country> countries = mainPage.getContinentInfo();
        Map<String, List<String>> plugTypesByCountry = new HashMap<>();
        
        for (Country country : countries) {
            if (country.getContinent().equalsIgnoreCase(continent)) {
                String countryUrl = country.getUrl();
                List<String> plugTypes = getPlugTypes(countryUrl);
                //Excluding countries with plug type A or B as it is the same as USA's
                if (!plugTypes.contains("A") && !plugTypes.contains("B")) {
                    plugTypesByCountry.put(country.getName(), plugTypes);
                }
            }
        }
    
        Map<String, List<String>> countriesByPlugType = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : plugTypesByCountry.entrySet()) {
            String countryName = entry.getKey();
            List<String> plugTypes = entry.getValue();
            for (String plugType : plugTypes) {
                countriesByPlugType.putIfAbsent(plugType, new ArrayList<>());
                countriesByPlugType.get(plugType).add(countryName);
            }
        }
    
        System.out.println("Countries in " + continent + " with different Plug Types to the USA:"); 
        for (Map.Entry<String, List<String>> entry : countriesByPlugType.entrySet()) {
            String plugType = entry.getKey();
            List<String> countriesWithPlugType = entry.getValue();
            System.out.println("Plug type " + plugType + ": " + String.join(", ", countriesWithPlugType));
        }
    }
    

    //test function
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the continent: ");
        String continent = scanner.nextLine().toLowerCase();
        scanner.close();

        PlugType plugTypeInstance = new PlugType();
        plugTypeInstance.findCountriesWithDifferentPlugTypesToUSA(continent);
    }

}
