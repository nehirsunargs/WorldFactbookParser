import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Flags {
    public static String getFlagUrl(Country country) {
        String flagUrl = "";
        try {
            Document doc = Jsoup.connect(country.getUrl()).get();
            Elements flagElements = doc.select("#main-content > div.wfb-country-link-display.hide-print > section > div > div:nth-child(2) > div:nth-child(1) > section > div > div.card-gallery__text-container > div.card-gallery__section-two > a");
            if (!flagElements.isEmpty()) {
                flagUrl = "https://www.cia.gov" + flagElements.get(0).attr("href");
            }
        } catch (IOException e) {
            System.out.println("Error fetching flag URL for " + country.getName());
            e.printStackTrace();
        }
        return flagUrl;
    }
    
    public static boolean containsWordInFlagDescription(String flagUrl, String word) {
        try {
            Document doc = Jsoup.connect(flagUrl).get();
            Element descriptionElement = doc.selectFirst("#main-content > section.background-white > div > div:nth-child(1) > div > div > div > div > div.col-lg-4.col-md-12.col-sm-12.order-4.order-lg-3.ph0 > div");
            if (descriptionElement != null) {
                String descriptionText = descriptionElement.text().toLowerCase();
                return descriptionText.contains(word.toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Error fetching flag description from " + flagUrl);
            e.printStackTrace();
        }
        return false;
    }
}
