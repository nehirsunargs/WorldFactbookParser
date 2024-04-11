import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

        String[] continents = {
            "Africa", "Africa",
            "Antarctica", "Antarctica",
            "Australia and Oceania", "Australia and Oceania",
            "Central America and the Caribbean", "Central America and the Caribbean",
            "Central Asia", "Central Asia",
            "East and Southeast Asia", "East and Southeast Asia",
            "Europe", "Europe",
            "Middle East", "Middle East",
            "North America", "North America",
            "South America", "South America",
            "South Asia", "South Asia"
    };

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Choose one of the following questions:");
        System.out.println("1. What is the lowest point in a given ocean?");
        System.out.println("2. List all countries with flags containing a specific word.");
        System.out.println("3. Which country in a given continent has the least amount of border countries?");
        System.out.println("4. What are the geographic coordinates of the nth largest ocean (by area)?");
        System.out.println("5. In the country in a given continent with the largest coastline, what percentage of the population is 65 and over?");
        System.out.println("6. Give a list of countries in a continent that have a different Plug Type to USA.");
        System.out.println("7. Among all the countries that a country borders, which country has the most disproportionate sex ratio of the total population?");
        System.out.println("8. Given an ocean name, find its total volume.");
        System.out.print("Enter the number of the question you want to ask: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                findLowestPoint();
                break;
            case 2:
                Scanner termScanner = new Scanner(System.in);
                System.out.print("Enter the word you want to search in flag descriptions: ");
                String searchTerm = termScanner.nextLine();
                List<Country> countriesWithFlags = getCountriesWithSpecificWordInFlagDescription(searchTerm);
                termScanner.close();
                System.out.println("Countries with flags containing '" + searchTerm + "':");
                for (Country country : countriesWithFlags) {
                    System.out.println(country.getName());
                }
                break;
            case 3:
                System.out.println("Select a continent:");
                for (int i = 0; i < continents.length; i += 2) {
                    System.out.println((i / 2 + 1) + ". " + continents[i]);
                }
                System.out.print("Enter the number of the continent: ");
                int continentChoice = scanner.nextInt();
                if (continentChoice >= 1 && continentChoice <= continents.length / 2) {
                    String continent = continents[(continentChoice - 1) * 2];
                    Country countryWithLeastBorders = BorderCountries.getCountryWithLeastBorderCountries(continent);
                    if (countryWithLeastBorders != null) {
                        System.out.println("Country in " + continent + " with the least amount of border countries: " + countryWithLeastBorders.getName());
                    } else {
                        System.out.println("No country found in " + continent);
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
                break;
            case 4:
                String baseUrl = "https://www.cia.gov/the-world-factbook";
                ArrayList<Ocean> oceans = Ocean.getAllOceans(baseUrl);
            
                Scanner scanner2 = new Scanner(System.in);
                System.out.print("Enter the rank of the ocean you want coordinates for (1-5): ");
                int rank = scanner.nextInt();
            
                if (rank >= 1 && rank <= 5) {
                    String coordinates = Ocean.getNthLargestOceanCoordinates(oceans, rank);
                    System.out.println("Geographic coordinates of the " + rank + " largest ocean: " + coordinates);
                } else {
                    System.out.println("Invalid rank. Please enter a rank between 1 and 5.");
                }
            
                scanner2.close();
                break;
            case 5:
                System.out.print("Enter the name of the continent: ");
                scanner.nextLine(); 

                String continent = scanner.nextLine();
                String continentUrl = "https://www.cia.gov/the-world-factbook/" + continent.toLowerCase() + "/";
                MainPage mainPage = new MainPage(continentUrl);
                List<Country> countries = mainPage.getContinentInfo();
                Country countryWithLargestCoastline = Population.getCountryWithLargestCoastline(countries, continent);

                if (countryWithLargestCoastline != null) {
                    System.out.println("Country with the largest coastline in " + continent + ": " + countryWithLargestCoastline.getName());
                    System.out.println("Coastline: " + countryWithLargestCoastline.getLargestCoastline() + " km");

                    double percentagePopulationOver65 = Population.getPercentagePopulationOver65(countryWithLargestCoastline);
                    System.out.println("Percentage of population older than 65: " + percentagePopulationOver65 + "%");
                } else {
                    System.out.println("No country found for the given continent.");
                }
                break;
            case 6:
                Scanner continentScanner = new Scanner(System.in);
                System.out.print("Enter the name of the continent: ");
                String continentInput = continentScanner.nextLine().toLowerCase();
                continentScanner.close();
                PlugType plugTypeInstance = new PlugType();
                plugTypeInstance.findCountriesWithDifferentPlugTypesToUSA(continentInput);
                break;
            case 7:
                Scanner countryScanner = new Scanner(System.in);
                System.out.print("Enter the name of the country: ");
                String countryName = countryScanner.nextLine();
                countryScanner.close();
                String countryUrl = "https://www.cia.gov/the-world-factbook/countries/";
                MainPage mainPage3 = new MainPage(countryUrl);
                List<Country> countries3 = mainPage3.getContinentInfo();
                String targetCountryUrl = null;
                for (Country country : countries3) {
                    if (country.getName().equalsIgnoreCase(countryName)) {
                        targetCountryUrl = country.getUrl();
                        break;
                    }
                }
                if (targetCountryUrl != null) {
                    String mostDisproportionateBorderCountry = SexRatio.findMostDisproportionateBorderCountry(targetCountryUrl);
                    System.out.println("Most Disproportionate Border Country of " + countryName + ": " + mostDisproportionateBorderCountry);
                } else {
                    System.out.println("Country not found!");
                }
                break;
            case 8:
                String baseUrl2 = "https://www.cia.gov/the-world-factbook";
                ArrayList<Ocean> oceans2 = Ocean.getAllOceans(baseUrl2);
                Ocean.findOceanVolume(oceans2);
                break;
            default:
                System.out.println("Invalid choice!");
        }

        scanner.close();
    }

    private static void findLowestPoint() {
        String baseUrl = "https://www.cia.gov/the-world-factbook";
        ArrayList<Ocean> oceans = Ocean.getAllOceans(baseUrl);
    
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the ocean: ");
        String oceanName = scanner.nextLine();
        scanner.close();
        Ocean foundOcean = null;

        for (Ocean ocean : oceans) {
            if (ocean.getName().equalsIgnoreCase(oceanName)) {
                foundOcean = ocean;
                break;
            }
        }

        if (foundOcean != null) {
            System.out.println("Lowest Point in the " + oceanName + ": " + foundOcean.getLowestPoint());
        } else {
            System.out.println("Ocean '" + oceanName + "' not found.");
        }
    }
    
    private static List<Country> getCountriesWithSpecificWordInFlagDescription(String searchTerm) {
        List<Country> countriesWithFlags = new ArrayList<>();
        String url = "https://www.cia.gov/the-world-factbook/countries/";
        MainPage mainPage = new MainPage(url);
        List<Country> countries = mainPage.getContinentInfo();
        
        for (Country country : countries) {
            String flagUrl = Flags.getFlagUrl(country);
            if (!flagUrl.isEmpty() && Flags.containsWordInFlagDescription(flagUrl, searchTerm)) {
                countriesWithFlags.add(country);
            }
        }
        
        return countriesWithFlags;
    }
}
