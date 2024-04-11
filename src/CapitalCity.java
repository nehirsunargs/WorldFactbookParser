import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CapitalCity {
    private String country;
    private String name;
    private int latitudeDegrees;
    private int longitudeDegrees;

    public CapitalCity(String name, int latitudeDegrees, int longitudeDegrees) {
        this.name = name;
        this.latitudeDegrees = latitudeDegrees;
        this.longitudeDegrees = longitudeDegrees;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public int getLatitudeDegrees() {
        return latitudeDegrees;
    }

    public int getLongitudeDegrees() {
        return longitudeDegrees;
    }

    public static CapitalCity extractFromCountryUrl(String countryUrl) {
        String country = "";
        String name = "";
        int latitudeDegrees = 0;
        int longitudeDegrees = 0;

        try {
            Document doc = Jsoup.connect(countryUrl).get();
            Element countryElement = doc.selectFirst("#content > h1");
            if (countryElement != null) {
                country = countryElement.text();
            }

            Element capitalElement = doc.selectFirst("#government > div:nth-child(4) > p > strong:nth-child(1):contains(name:)");
            if (capitalElement != null) {
                String capitalInfo = capitalElement.parent().text();
                int endIndex = capitalInfo.indexOf("geographic");
                if (endIndex != -1) {
                    name = capitalInfo.substring(capitalInfo.indexOf("name:") + 5, endIndex).trim();
                }
            }

            Element coordinatesElement = doc.selectFirst("#government > div:nth-child(4) > p > strong:nth-child(4):contains(geographic coordinates:)");
            if (coordinatesElement != null) {
                String coordinatesInfo = coordinatesElement.parent().text();
                int endIndexE = coordinatesInfo.indexOf("E");
                int endIndexW = coordinatesInfo.indexOf("W");
                int endIndex = Math.max(endIndexE, endIndexW);
                if (endIndex != -1) {
                    String[] coordinatesStr = coordinatesInfo.substring(coordinatesInfo.indexOf("geographic coordinates:") + 22, endIndex + 1).replaceAll(":", "").trim().split(", ");
                    if (coordinatesStr.length >= 2) {
                        String latitude = coordinatesStr[0].replaceAll("\\(.*\\)", "").trim(); // Remove direction indicators
                        String[] latitudeParts = latitude.split(" ");
                        latitudeDegrees = Integer.parseInt(latitudeParts[0]);
                        String longitude = coordinatesStr[1].replaceAll("\\(.*\\)", "").trim(); // Remove direction indicators
                        String[] longitudeParts = longitude.split(" ");
                        longitudeDegrees = Integer.parseInt(longitudeParts[0]);
                    }
                }
            }

            return new CapitalCity(name, latitudeDegrees, longitudeDegrees);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static final double EARTH_RADIUS = 6371;

    private static double toRadians(double degrees) {
        return degrees * Math.PI / 180.0;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public static void main(String[] args) {
        String url = "https://www.cia.gov/the-world-factbook/countries/";
        MainPage mainPage = new MainPage(url);
        List<Country> countries = mainPage.getContinentInfo();
        List<CapitalCity> capitals = new ArrayList<>();
    
        for (Country country : countries) {
            String countryUrl = country.getUrl();
            CapitalCity capital = CapitalCity.extractFromCountryUrl(countryUrl);
            if (capital != null) {
                capital.setCountry(country.getName());
                capitals.add(capital);
            }
        }

        System.out.println("Latitude and Longitude Coordinates:");
        for (CapitalCity city : capitals) {
            System.out.println("Country: " + city.getCountry());
            System.out.println("Capital: " + city.getName());
            System.out.println("Latitude: " + city.getLatitudeDegrees());
            System.out.println("Longitude: " + city.getLongitudeDegrees());
            System.out.println();
        }
    }
    
    


}