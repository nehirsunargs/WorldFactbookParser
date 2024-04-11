## Overview

The World Factbook Scraper is a Java application that extracts information from the CIA World Factbook website. It provides various functionalities such as retrieving country information, analyzing geographical data, and answering specific questions related to continents, countries, and oceans.

## Features

- Retrieve country information including names, URLs, continents, and plug types.
- Extract geographic data such as coastline length and lowest points of oceans.
- Analyze border countries for each country.
- Search for specific words in flag descriptions.
- Calculate sex ratio disproportionality for bordering countries.
- Find the volume percentage of a given ocean.

## Installation

After cloning the repository, import the project into your Java IDE (preferably vscode). Make sure to install the dependencies (jsoup). Run App.java which will generate a prompt to ask one of the 8 questions.

## Further Description

- The only hard-coded data in the project are the continents and their corresponding URLs, which are manually listed for simplicity. All other data, including flag URLs, ocean URLs, country information, etc., are dynamically retrieved from the CIA World Factbook website. This ensures that even if the structure of the website changes in the future, the code remains adaptable and functional.
- An issue encountered in the 6th question functionality is noted, where it throws a "Plug type not found" error for non-country entries such as "European Union". Although the functionality still provides accurate results, it could be improved to handle such edge cases more gracefully. Given more time, this aspect of the code could be refined to account for edge-cases.
- The `MainPage.java` file is introduced to handle the retrieval of country URLs from continents, which are then stored in the `Country.java` class. Additionally, almost each question or functionality has its own file, minimizing disruption to the main `App.java` file and enhancing code readability.

## Examples

- To find the lowest point in a given ocean:
  ```
  Enter the number of the question you want to ask: 1
  Enter the name of the ocean: Pacific Ocean
  ```

- To list all countries with flags containing a specific word:
  ```
  Enter the number of the question you want to ask: 2
  Enter the word you want to search in flag descriptions: sky
  ```

## Dependencies

- Jsoup: [https://jsoup.org/](https://jsoup.org/)

## Contributors

- [Nehir Sunar](https://github.com/nehirsunargs)

## Outputs to the programming questions:

1. What is the lowest point in the Atlantic Ocean?
Puerto Rico Trench -8,605 m
2. List all countries with flags containing stars.
Countries with flags containing 'stars':
Burundi
Cabo Verde
Comoros
Egypt
Equatorial Guinea
Sao Tome and Principe
Australia
Cook Islands
French Polynesia
Micronesia, Federated States of
New Zealand
Niue
Samoa
Solomon Islands
Tokelau
Tuvalu
Cayman Islands
Curacao
Dominica
El Salvador
Grenada
Honduras
Nicaragua
Saint Kitts and Nevis
Tajikistan
Turkmenistan
Uzbekistan
China
Hong Kong
Macau
Papua New Guinea
Philippines
Singapore
Bosnia and Herzegovina
European Union
Kosovo
Slovenia
Iraq
Syria
Yemen
United States
Brazil
Venezuela
3. Which country in South Asia has the least amount of border countries?
Country in South Asia with the least amount of border countries: British Indian Ocean Territory
4. What are the geographic coordinates of the second largest ocean (by area)?
Geographic coordinates of the 2 largest ocean: Geographic coordinates 0 00 N, 25 00 W
5. In the country in South America with the largest coastline, what percentage of the population is
65 and over?
Country with the largest coastline in South America: Brazil
Coastline: 7491.0 km
Percentage of population older than 65: 10.51%
6.Give a list of countries in Africa that have a different Plug Type to USA. In your output, state the plug type and group countries together with the same plug type.
Countries in africa with different Plug Types to the USA:
Plug type C: Cameroon, Angola, Sudan, Gabon, Mozambique, Morocco, Mali, Algeria, Cabo Verde, South Sudan, Zambia, Mauritius, Guinea-Bissau, Senegal, Comoros, Central African Republic, Ethiopia, Congo, Republic of the, Burundi, Eritrea, Guinea, Egypt, Chad, Somalia, Sao Tome and Principe, Madagascar, Equatorial Guinea, Libya, Congo, Democratic Republic of the, Tunisia, Togo, Cote d'Ivoire, Rwanda, Djibouti, Burkina Faso, Mauritania
Plug type D: Sudan, South Sudan, Tanzania, Ghana, Zambia, Senegal, Namibia, South Africa, Sierra Leone, Nigeria, Niger, Botswana, Zimbabwe
Plug type E: Cameroon, Gabon, Morocco, Mali, Senegal, Comoros, Central African Republic, Congo, Republic of the, Burundi, Chad, Madagascar, Equatorial Guinea, Congo, Democratic Republic of the, Tunisia, Cote d'Ivoire, Djibouti, Burkina Faso
Plug type F: Angola, Mozambique, Algeria, Cabo Verde, Senegal, Ethiopia, Guinea, Egypt, Chad, Sao Tome and Principe
Plug type G: Seychelles, Tanzania, Ghana, Zambia, Mauritius, Ethiopia, Sierra Leone, Malawi, Nigeria, Gambia, The, Niger, Kenya, Botswana, Uganda, Zimbabwe
Plug type J: Rwanda
Plug type K: Guinea
Plug type L: Eritrea, Libya
Plug type M: Mozambique, Lesotho, Eswatini, Namibia, South Africa
Plug type N: South Africa
7. Among all the countries that Russia borders, which country has the most disproportionate sex ratio of the total population?
Most Disproportionate Border Country of Russia: Azerbaijan
8. Find the total volume of Indian Ocean.
The volume for Indian Ocean: 264 million cu km

## Notes
The last file, CapitalCity is made for the first EC question. Even though the full code is not implemented, it correctly finds country capitals, their geolocations and calculates distance.