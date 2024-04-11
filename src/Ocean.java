import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ocean {
    private String name;
    private String url;

    public Ocean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getLowestPoint() {
        try {
            Document doc = Jsoup.connect(url).get();
            Element lowestPointLabelElement = doc.selectFirst("#geography > div:nth-child(12) > p > strong:contains(Lowest point:)");
            if (lowestPointLabelElement != null) {
                Element paragraphElement = lowestPointLabelElement.parent();
                if (paragraphElement != null) {
                    Element lowestPointElement = paragraphElement.selectFirst("strong:contains(Lowest point:)");
                    if (lowestPointElement != null) {
                        String lowestPointText = lowestPointElement.nextSibling().toString().trim();
                        return lowestPointText;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error fetching lowest point for " + name);
            e.printStackTrace();
        }
        return "Lowest point not found";
    }


    public static ArrayList<Ocean> getAllOceans(String baseUrl) {
        ArrayList<Ocean> oceans = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(baseUrl).get();
            for (int i = 1; i <= 5; i++) {
                String cssSelector = String.format("#main-content > section:nth-child(6) > section > div > div > div.col-sm-12.col-md-5.offset-md-7.order-last.pv120-90.teaser-context > div > div > div > p:nth-child(%d) > strong > a", i);
                doc.select(cssSelector).forEach(oceanLink -> {
                    String name = oceanLink.text();
                    String relativeUrl = oceanLink.attr("href");
                    String absoluteUrl = baseUrl + "/" + relativeUrl; 
                    Ocean ocean = new Ocean(name, absoluteUrl);
                    oceans.add(ocean);
                });
            }
        } catch (IOException e) {
            System.out.println("Error fetching ocean data.");
            e.printStackTrace();
        }
        return oceans;
    }

    public static void getLowestPointsForAllOceans(ArrayList<Ocean> oceans) {
        for (Ocean ocean : oceans) {
            System.out.println("Ocean: " + ocean.getName());
            System.out.println("URL: " + ocean.getUrl());
            System.out.println("Lowest Point: " + ocean.getLowestPoint());
            System.out.println();
        }
    }

    public String getOceanArea() {
        try {
            Document doc = Jsoup.connect(url).get();
            Element areaLabelElement = doc.selectFirst("#geography > div:nth-child(5) > p > strong:contains(total:)");
            if (areaLabelElement != null) {
                String areaText = areaLabelElement.parent().text();
                Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
                Matcher matcher = pattern.matcher(areaText);
                if (matcher.find()) {
                    String areaInfo = matcher.group();
                    return areaInfo;
                }
            }
        } catch (IOException e) {
            System.out.println("Error fetching ocean area for " + name);
            e.printStackTrace();
        }
        return "Ocean area not found";
    }
    
    class OceanUtils {
        public static String getSecondLargestOceanCoordinates(List<Ocean> oceans) {
            Collections.sort(oceans, (o1, o2) -> {
                Double area1 = Double.parseDouble(o1.getOceanArea());
                Double area2 = Double.parseDouble(o2.getOceanArea());
                return area2.compareTo(area1);
            });
    
            String secondLargestOceanUrl = oceans.get(1).getUrl();
    
            try {
                Document doc = Jsoup.connect(secondLargestOceanUrl).get();
                Element coordinatesElement = doc.selectFirst("#geography > div:nth-child(3)");
                if (coordinatesElement != null) {
                    return coordinatesElement.text();
                }
            } catch (IOException e) {
                System.out.println("Error fetching coordinates for the second largest ocean.");
                e.printStackTrace();
            }
            return "Coordinates not found";
        }
    }

    public static String getNthLargestOceanCoordinates(List<Ocean> oceans, int rank) {
        Collections.sort(oceans, (o1, o2) -> {
            Double area1 = Double.parseDouble(o1.getOceanArea());
            Double area2 = Double.parseDouble(o2.getOceanArea());
            return area2.compareTo(area1);
        });

        if (rank <= 0 || rank > 5) {
            return "Invalid rank. Please enter a rank between 1 and 5.";
        }

        String nthLargestOceanUrl = oceans.get(rank - 1).getUrl();

        try {
            Document doc = Jsoup.connect(nthLargestOceanUrl).get();
            Element coordinatesElement = doc.selectFirst("#geography > div:nth-child(3)");
            if (coordinatesElement != null) {
                return coordinatesElement.text();
            }
        } catch (IOException e) {
            System.out.println("Error fetching coordinates for the " + rank + " largest ocean.");
            e.printStackTrace();
        }
        return "Coordinates not found";
    }

    public static void findOceanVolume(ArrayList<Ocean> oceans) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the ocean: ");
        String oceanName = scanner.nextLine().trim();
        scanner.close();
    
        Ocean foundOcean = null;
    
        for (Ocean ocean : oceans) {
            if (ocean.getName().equalsIgnoreCase(oceanName)) {
                foundOcean = ocean;
                break;
            }
        }
    
        if (foundOcean != null) {
            try {
                Document doc = Jsoup.connect(foundOcean.getUrl()).get();
                Element volumeLabelElement = doc.selectFirst("#geography > div:nth-child(9) > p");
                if (volumeLabelElement != null) {
                    String volumeText = volumeLabelElement.text();
                    Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
                    Matcher matcher = pattern.matcher(volumeText);
                    if (matcher.find()) {
                        String volumeInfo = matcher.group();
                        System.out.println("The volume for " + oceanName + ": " + volumeInfo + " million cu km");
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error fetching ocean volume for " + oceanName);
                e.printStackTrace();
            }
        } else {
            System.out.println("Ocean '" + oceanName + "' not found.");
        }
    }
    
    public static void main(String[] args) {
    String baseUrl = "https://www.cia.gov/the-world-factbook";
    ArrayList<Ocean> oceans = Ocean.getAllOceans(baseUrl);

    /*for (Ocean ocean : oceans) {
        System.out.println("Ocean: " + ocean.getName());
        System.out.println("URL: " + ocean.getUrl());
        System.out.println("Lowest Point: " + ocean.getLowestPoint());
        System.out.println("Area: " + ocean.getOceanArea());
        System.out.println();
    }

    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the rank of the ocean you want coordinates for (1-5): ");
    int rank = scanner.nextInt();

    if (rank >= 1 && rank <= 5) {
        String coordinates = getNthLargestOceanCoordinates(oceans, rank);
        System.out.println("Geographic coordinates of the " + rank + " largest ocean: " + coordinates);
    } else {
        System.out.println("Invalid rank. Please enter a rank between 1 and 5.");
    }

    scanner.close(); */
    findOceanVolume(oceans);
}

}
